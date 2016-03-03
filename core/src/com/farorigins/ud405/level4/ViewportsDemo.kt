package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * In this demo we'll explore the effect of using a Viewport to manage a camera.
 *
 * We start with a world containing a 16x9 checkerboard, with an apron of neon green.
 */
class ViewportsDemo : ApplicationAdapter() {

    companion object {
        val TAG = ViewportsDemo::class.java.name

        val WORLD_WIDTH = 16
        val WORLD_HEIGHT = 9
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport

    private lateinit var renderer: ShapeRenderer

    /**
     * Uncomment the following viewports one at a time, and check out the effect when you resize the desktop window.
     */
    override fun create() {
        camera = OrthographicCamera()

        // Makes the size of the world match the size of the screen
        viewport = ScreenViewport(camera)

        // Make the world fill the screen, regardless of aspect ratio
        viewport = StretchViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat(), camera)

        // Make the world fill the screen, maintaining aspect ratio, but bits of the world may be cut off
        // viewport = FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)

        // Fit the world inside the screen, adding black bars to pad out the extra space, maintaining aspect ratio
        // viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)

        // Make the short axis of the world larger to fill the screen, maintaining aspect ratio
        // viewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)

        viewport.setScreenBounds(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        renderer = ShapeRenderer()
    }

    override fun dispose() {
        renderer.dispose()
    }

    /**
     * When the screen is resized, we need to inform the viewport. Note that when using an
     * ExtendViewport, the world size might change as well.
     */
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        Gdx.app.log(TAG, "Viewport world dimensions: (" + viewport.worldHeight + ", " + viewport.worldWidth + ")")
    }

    /**
     * When using a viewport, instead of calling camera.update(), we just call viewport.apply()
     */
    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        viewport.apply()
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeType.Filled)
        renderer.color = Color.GREEN
        renderer.rect(-10f, -10f, WORLD_WIDTH + 20f, WORLD_HEIGHT + 20f)
        renderWorld()
        renderer.end()
    }

    private fun renderWorld() {
        for (yStart in 0..WORLD_HEIGHT) {
            for (xStart in 0..WORLD_WIDTH) {
                renderer.color = if ((yStart + xStart) % 2 == 0) {
                    Color.WHITE
                } else {
                    Color.BLACK
                }
                renderer.rect(xStart.toFloat(), yStart.toFloat(), 1f, 1f)
            }
        }
    }
}
