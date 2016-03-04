package com.farorigins.ud405.level4


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

/**
 *
 * This class provides two cameras, a camera that draws pixel for pixel with the display, and a
 * closeup camera that can be moved around the screen and zoomed. When in overview mode, the field
 * of view of the closeup camera is drawn on the scene.
 *
 * Controls:
 *
 * Space: Switch between overview and closeup camera's
 * Arrow keys: Move the closeup camera
 * W/S: Grow and shrink the closeup camera's viewport height
 * D/A: Grow and shrink the closeup camera's viewport width
 * Z/X: Zoom in and out, respecting aspect ratio
 * R: Reset
 * F: Restore the proper aspect ratio
 */
class DemoCamera : InputAdapter() {

    val overviewCamera = OrthographicCamera()
    val closeupCamera = OrthographicCamera()

    var inCloseupMode = true

    init {
        closeupCamera.setToOrtho(false, Gdx.graphics.width * INITIAL_ZOOM, Gdx.graphics.height * INITIAL_ZOOM)
        overviewCamera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }

    fun resize(width: Float, height: Float) {
        overviewCamera.setToOrtho(false, width, height)
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            inCloseupMode = !inCloseupMode
        }
        // Reset
        if (keycode == Keys.R) {
            closeupCamera.setToOrtho(false, Gdx.graphics.width * INITIAL_ZOOM, Gdx.graphics.height * INITIAL_ZOOM)
        }
        if (keycode == Keys.F) {
            fixAspectRatio()
        }
        return super.keyUp(keycode)
    }

    fun update() {
        val delta = Gdx.graphics.deltaTime

        // Movement
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            closeupCamera.translate(-MOVE_RATE * delta, 0f)
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            closeupCamera.translate(MOVE_RATE * delta, 0f)
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            closeupCamera.translate(0f, -MOVE_RATE * delta)
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            closeupCamera.translate(0f, MOVE_RATE * delta)
        }

        // Rotation
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            closeupCamera.rotate(-ROTATION_RATE * delta)
        }
        if (Gdx.input.isKeyPressed(Keys.E)) {
            closeupCamera.rotate(ROTATION_RATE * delta)
        }

        // Viewport size (ignoring aspect ratio)
        if (Gdx.input.isKeyPressed(Keys.W)) {
            closeupCamera.viewportHeight += SCALE_RATE * delta
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            closeupCamera.viewportHeight -= SCALE_RATE * delta
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            closeupCamera.viewportWidth -= SCALE_RATE * delta
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            closeupCamera.viewportWidth += SCALE_RATE * delta
        }

        // Zoom
        if (Gdx.input.isKeyPressed(Keys.Z)) {
            proportionalZoom(-delta)
        }

        if (Gdx.input.isKeyPressed(Keys.X)) {
            proportionalZoom(delta)
        }
        closeupCamera.update()
    }

    private fun proportionalZoom(delta: Float) {
        val aspectRatio = overviewCamera.viewportWidth / overviewCamera.viewportHeight
        closeupCamera.viewportWidth += SCALE_RATE * delta
        closeupCamera.viewportHeight += SCALE_RATE / aspectRatio * delta
    }

    private fun fixAspectRatio() {
        val aspectRatio = overviewCamera.viewportWidth / overviewCamera.viewportHeight
        closeupCamera.viewportHeight = closeupCamera.viewportWidth / aspectRatio
        closeupCamera.update()
    }

    /**
     * Set's the ShapeRenderer's projection matrix depending on the mode of the demo camera.
     */
    fun setCamera(renderer: ShapeRenderer) {
        if (inCloseupMode) {
            closeupCamera.update()
            renderer.projectionMatrix = closeupCamera.combined
        } else {
            overviewCamera.update()
            renderer.projectionMatrix = overviewCamera.combined
        }
    }

    /**
     * Renders a blue rectangle showing the field of view of the closeup camera
     */
    fun render(renderer: ShapeRenderer) {
        if (!inCloseupMode) {
            // Figure out the location of the camera corners in the world
            val bottomLeft = myUnproject(closeupCamera, 0f, closeupCamera.viewportHeight)
            val bottomRight = myUnproject(closeupCamera, closeupCamera.viewportWidth, closeupCamera.viewportHeight)
            val topRight = myUnproject(closeupCamera, closeupCamera.viewportWidth, 0f)
            val topLeft = myUnproject(closeupCamera, 0f, 0f)

            // Draw a rectangle showing the closeup camera's field of view
            renderer.begin(ShapeType.Line)
            renderer.color = Color.BLUE
            val poly = floatArrayOf(
                    bottomLeft.x, bottomLeft.y,
                    bottomRight.x, bottomRight.y,
                    topRight.x, topRight.y,
                    topLeft.x, topLeft.y
            )

            renderer.set(ShapeType.Line)
            renderer.polygon(poly)
            renderer.end()
        }
    }

    /**
     * Helper function to deal with the fact that unproject expects coordinates with positive y
     * pointing down.
     */
    private fun myUnproject(camera: OrthographicCamera, x: Float, y: Float): Vector2 {
        val raw = camera.unproject(Vector3(x, y + overviewCamera.viewportHeight - camera.viewportHeight, 0f), 0f, 0f, camera.viewportWidth, camera.viewportHeight)
        return Vector2(raw.x, raw.y)
    }

    companion object {
        val SCALE_RATE = 100f
        val MOVE_RATE = 100f
        val ROTATION_RATE = 45f
        val INITIAL_ZOOM = 0.5f
    }
}
