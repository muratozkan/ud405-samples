package com.farorigins.ud405.level4


import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils


/**
 * In this exercise, you'll create an OrthographicCamera, and use it to zoom in on a moving circle
 */
class OrthographicCameraExercise : ApplicationAdapter() {

    companion object {
        val BALL_RADIUS = 20f
        val PERIOD = 2000f
        val X_AMPLITUDE = 40f
        val Y_AMPLITUDE = 20f
        val X_CENTER = 100f
        val Y_CENTER = 100f
    }

    private lateinit var renderer: ShapeRenderer
    private var timeCreated = 0L

    // Declare an OrthographicCamera
    private lateinit var camera: OrthographicCamera

    override fun create() {
        renderer = ShapeRenderer()
        timeCreated = TimeUtils.millis()

        // Initialize the camera
        camera = OrthographicCamera()
        // Set the camera's position to the center of the circle's movement (X_CENTER, Y_CENTER)
        camera.position.set(X_CENTER, Y_CENTER, 0f)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        // Calculate the aspect ratio (width / height)
        val aspectRatio = width.toFloat() / height

        // Set the camera's viewport height taking into account the ball's movement and radius
        camera.viewportHeight = 2f * (BALL_RADIUS + Y_AMPLITUDE)

        // Set the camera's viewport width to maintain the aspect ratio
        camera.viewportWidth = aspectRatio * camera.viewportHeight
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Call update() on the camera
        camera.update()

        // Set the SceneRenderer's projection matrix equal to the camera's combined matrix
        renderer.projectionMatrix = camera.combined

        renderer.begin(ShapeType.Filled)
        val interval = TimeUtils.timeSinceMillis(timeCreated)
        val x = X_CENTER + X_AMPLITUDE * MathUtils.sin(MathUtils.PI2 * interval / PERIOD)
        val y = Y_CENTER + Y_AMPLITUDE * MathUtils.sin(2 * MathUtils.PI2 * interval / PERIOD)
        renderer.circle(x, y, BALL_RADIUS)
        renderer.end()
    }
}
