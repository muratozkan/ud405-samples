package com.farorigins.ud405.level7.thefirsticicle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

class IciclesScreen : Screen {

    companion object {
        val TAG = IciclesScreen::class.java.name
    }

    // Add an ExtendViewport
    private lateinit var viewport: Viewport

    // Add a ShapeRenderer
    private lateinit var renderer: ShapeRenderer

    // Add an Icicle
    private lateinit var icicle: Icicle

    override fun show() {
        // Initialize the viewport using the world size constant
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)

        // Initialize the ShapeRenderer
        renderer = ShapeRenderer()
        // Set autoShapeType(true) on the ShapeRenderer
        renderer.setAutoShapeType(true)

        // Create a new Icicle in the middle of the world
        icicle = Icicle(Vector2(Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2))
    }

    override fun resize(width: Int, height: Int) {
        // Ensure that the viewport updates correctly
        viewport.update(width, height, true)
    }

    override fun dispose() {

    }

    override fun render(delta: Float) {
        //  Apply the viewport
        viewport.apply()

        // Clear the screen to the background color
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set the ShapeRenderer's projection matrix
        renderer.projectionMatrix = viewport.camera.combined

        // Draw the Icicle
        renderer.begin()
        icicle.render(renderer)
        renderer.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    // Dispose of the ShapeRenderer
    override fun hide() {
        renderer.dispose()
    }
}
