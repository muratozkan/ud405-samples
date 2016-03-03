package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 *
 * In this demo we've got a game world with a huge star, a tiny planet, and an itty bitty satellite.
 * Using the default camera, we can just barely see the edge of the star, and the satellite is just
 * a single pixel. Let's use an orthographic camera and the resize callback to adjust our field of
 * view and zoom in on each of these objects.
 *
 * So what is a camera? A camera holds on to two matrices. One matrix encodes the camera's position
 * and orientation in the game world. For 2D games, this is usually just a single X/Y position, but
 * for, say, a 3D flight simulator, the camera can hold the plane's 3D position, pitch, roll, and
 * yaw.
 *
 * The other matrix encodes how the camera translates positions in the world into positions on the
 * screen. This includes how much perspective to include (fisheye vs telephoto), and how big of an
 * area on the screen the camera will draw to.
 */
class Closeup : ApplicationAdapter() {

    companion object {
        val TAG = Closeup::class.java.name

        val STAR_CENTER_X = -900f
        val STAR_CENTER_Y = 240f
        val STAR_RADIUS = 1000f
        val PLANET_CENTER_X = 640f
        val PLANET_CENTER_Y = 240f
        val PLANET_RADIUS = 10f
        val SATELLITE_POSITION_X = 620f
        val SATELLITE_POSITION_Y = 220f
        val SATELLITE_SIZE = 1f
        val SATELLITE_ROTATION = 45f

        val SEGMENTS = 64
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var camera: OrthographicCamera

    override fun create() {
        renderer = ShapeRenderer()
        camera = OrthographicCamera()
    }

    override fun dispose() {
        renderer.dispose()
    }

    /**
     * There are two new things we need to do when using a camera. The first is calling
     * camera.update(). Whenever we adjust the properties of a camera (like its position), it needs
     * to fold those changes into the matrices that define how it looks at the world. The easiest
     * way to make sure that happens is just to call update every frame. It's a fast operation, so
     * no worries.
     *
     * The second thing we need to do is to tell our ShapeRenderer that we want to use our camera.
     * We do this by setting the ShapeRenderer's projection matrix to the camera's combined matrix.
     * The combined matrix is the combination of the camera's view and projection matrices. Yeah,
     * there's a lot of matrices flying around. Basically ShapeRenderer doesn't have a notion of
     * position in some larger world, so all it's got is a projection matrix.
     */
    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Tell the camera to propagate any changes to it's matrices
        camera.update()
        // Tell our ShapeRenderer to use the camera's view of the world
        renderer.projectionMatrix = camera.combined

        renderer.begin(ShapeType.Filled)

        // Draw the star
        renderer.color = Color.YELLOW
        renderer.circle(STAR_CENTER_X, STAR_CENTER_Y, STAR_RADIUS, SEGMENTS)

        // Draw the planet
        renderer.color = Color.GREEN
        renderer.circle(PLANET_CENTER_X, PLANET_CENTER_Y, PLANET_RADIUS, SEGMENTS)

        // Draw the satellite
        renderer.color = Color.RED

        var halfSize = SATELLITE_SIZE / 2
        renderer.rect(SATELLITE_POSITION_X - halfSize, SATELLITE_POSITION_Y - halfSize,
                halfSize, halfSize,
                SATELLITE_SIZE, SATELLITE_SIZE, 1f, 1f, SATELLITE_ROTATION)

        renderer.end()
    }

    /**
     * The resize callback gets called after create, and any time the screen size changes, which
     * could be because a mobile device rotated, or because we're running the desktop app, and the
     * user changed the window size by dragging around the corner.
     *
     * When the screen size changes, we need to make sure our camera is updated. If the camera
     * thinks that that the screen is smaller than it really is, shapes will be way bigger than we
     * want them to be, and vice versa, if the camera thinks the screen is bigger than it really is,
     * the world will appear tiny.
     *
     * Here we've factored out the code needed to get the camera to focus on each of the solar
     * system objects in turn. Uncomment the code for the object you want to focus on.
     */
    override fun resize(width: Int, height: Int) {
        var aspectRatio = width.toFloat() / height
        camera.setToOrtho(false, width.toFloat(), height.toFloat())
        // trackStar(camera, aspectRatio)
        trackPlanet(camera, aspectRatio)
        // trackSatellite(camera, aspectRatio)
    }


    /**
     * To get the camera to zoom out, so we can see the whole star, first we need to set the
     * camera's viewport size using setToOrtho. The first argument is whether we want increasing Y
     * to mean moving down. We don't, so we'll set it to false. The next two arguments are the
     * viewport width and height. The height just needs to be double the radius of the star, but if
     * we set the viewport width to be equal to the viewport height, the star will be a stretched
     * out oval, instead of a circle.
     *
     * This is because the aspect ratio (width / height) of the camera doesn't match the aspect
     * ratio of the screen. We can fix this by making our viewport wider, by multiplying twice the
     * star radius by the aspect ratio.
     *
     * If we stop there, it looks like our camera viewport is now big enough to hold the star, but
     * we're not looking at the right spot. In fact, it looks like the is centered in exactly the
     * same location as before. Let's set the camera's position to the center of the star.
     *
     * That looks pretty good, but now we've lost the planet. Let's use translate to nudge the
     * camera to the right, so we can see the planet again.
     */
    private fun trackStar(camera: OrthographicCamera, aspectRatio: Float) {
        camera.viewportHeight = 2 * STAR_RADIUS
        // This will stretch the star, since the aspect ratio doesn't match
        //        camera.viewportWidth = 2 * STAR_RADIUS;
        camera.viewportWidth = aspectRatio * camera.viewportHeight
        camera.position.set(STAR_CENTER_X, STAR_CENTER_Y, 0f)
        camera.translate(STAR_RADIUS * (aspectRatio - 1), 0f)
    }

    /**
     * Let's move the camera to focus on the green planet. First, we need to set the viewport size,
     * as we did with the star. Now we need to move the camera to the planet.
     *
     * A different way to do that is to move the camera to the origin, then translate it to the
     * position of the planet.
     */
    private fun trackPlanet(camera: OrthographicCamera, aspectRatio: Float) {
        camera.viewportHeight = 2 * PLANET_RADIUS
        camera.viewportWidth = aspectRatio * camera.viewportHeight
        camera.position.set(0f, 0f, 0f)
        camera.translate(PLANET_CENTER_X, PLANET_CENTER_Y, 0f)
    }

    /**
     * Finally, let's zoom in on the satellite, and match its rotation. This could be very tricky,
     * given that rectangles are drawn by specifying their lower left corner, and also considering
     * that rectangles can specify the origin about which they will be rotated.
     *
     * Fortunately, we were smart when drawing the satellite, and made sure that it was _centered_
     * at SATELLITE_POSITION_X, SATELLITE_POSITION_Y, and also rotated about that point. That makes
     * our job super easy. We just move the camera to that position, and rotate the camera.
     */
    private fun trackSatellite(camera: OrthographicCamera, aspectRatio: Float) {
        camera.viewportHeight = 2 * SATELLITE_SIZE
        camera.viewportWidth = aspectRatio * camera.viewportHeight
        camera.position.set(SATELLITE_POSITION_X, SATELLITE_POSITION_Y, 0f)
        camera.rotate(SATELLITE_ROTATION)
    }
}
