package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * In this exercise, you'll use a fit viewport to make sure the entire game world is onscreen and
 * at the right aspect ratio.
 *
 * One new concept is the fact that a viewport will happily create its own camera if you don't
 * provide it with one. To get the camera (to set a ShapeRenderer's projection matrix, for
 * instance), you can just call getCamera() on the viewport.
 *
 * When you're done, change up the WORLD_WIDTH and WORLD_HEIGHT, and see how the world still fits on
 * screen
 */
class ViewportsExercise : ApplicationAdapter() {

    companion object {
        val WORLD_WIDTH = 100.0f
        val WORLD_HEIGHT = 100.0f
        val RECURSIONS = 3
    }

    private lateinit var renderer: ShapeRenderer
    // Declare a FitViewport
    private lateinit var viewport: Viewport

    override fun create() {
        renderer = ShapeRenderer()
        // Initialize the viewport with the world width and height
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        // update the viewport and center the camera by passing true as the third argument
        viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Apply the viewport
        viewport.apply()

        // Set the projection matrix of the ShapeRenderer to the combined matrix of the viewport's camera
        renderer.projectionMatrix = viewport.camera.combined

        renderer.begin(ShapeType.Filled)
        renderer.color = Color.WHITE
        renderer.rect(0f, 0f, WORLD_WIDTH, WORLD_HEIGHT)
        renderer.color = Color.BLACK
        punchCantorGasket(0f, 0f, WORLD_WIDTH, WORLD_HEIGHT, RECURSIONS)
        renderer.color = Color.WHITE
        renderer.circle(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, Math.min(WORLD_WIDTH, WORLD_HEIGHT) / 6.5f, 20)
        renderer.end()
    }

    private fun punchCantorGasket(x: Float, y: Float, width: Float, height: Float, recursions: Int) {
        if (recursions == 0) {
            return
        }

        val newWidth = width / 3
        val newHeight = height / 3

        renderer.rect(x + newWidth, y + newHeight, newWidth, newHeight)

        punchCantorGasket(x, y, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x + newWidth, y, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x + 2 * newWidth, y, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x, y + newHeight, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x + 2 * newWidth, y + newHeight, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x, y + 2 * newHeight, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x + newWidth, y + 2 * newHeight, newWidth, newHeight, recursions - 1)
        punchCantorGasket(x + 2 * newWidth, y + 2 * newHeight, newWidth, newHeight, recursions - 1)
    }
}
