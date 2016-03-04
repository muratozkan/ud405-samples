package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * The goal of this exercise is just to draw a similey face. Feel free to get as artistic as you
 * want, but we've provided a bunch of constants you may find useful.
 *
 * The tricky part is drawing the mouth. Since we can't draw thick lines, making a thick line for
 * the mouth is hard. The trick is to draw two arcs. One black one, then a slightly smaller yellow
 * one. The portion of the black arc that isn't covered by the yellow arc becomes the mouth.
 */
class SmileyFace : ApplicationAdapter() {

    companion object {
        val FACE_CENTER_X = 20.0f
        val FACE_CENTER_Y = 20.0f
        val WORLD_WIDTH = 10.0f
        val WORLD_HEIGHT = 10.0f
        val FACE_RADIUS = 0.8f * WORLD_WIDTH / 2
        val EYE_OFFSET = 0.5f * FACE_RADIUS
        val EYE_RADIUS = 0.2f * FACE_RADIUS
        val MOUTH_OUTER_RADIUS = 0.8f * FACE_RADIUS
        val MOUTH_INNER_RADIUS = 0.6f * FACE_RADIUS
        val MOUTH_START_ANGLE = 180.0f
        val MOUTH_DEGREES = 180.0f

        val FACE_SEGMENTS = 40
        val EYE_SEGMENTS = 20
        val MOUTH_SEGMENTS = 20
    }

    // Declare a ShapeRenderer and an ExtendViewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: Viewport

    override fun create() {
        // Initialize the ShapeRenderer and ExtendViewport
        renderer = ShapeRenderer()
        viewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT)
    }

    override fun dispose() {
        // Dispose of the ShapeRenderer
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        // Update the viewport
        viewport.update(width, height, true)

        // Move the viewport's camera to the center of the face
        viewport.camera.position.set(FACE_CENTER_X, FACE_CENTER_Y, 0f)
    }

    /**
     * We'll often want to break up our drawing into separate functions, or different objects
     * entirely. This is easy to do, all we need to do is pass in our ShapeRenderer.
     */
    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Apply the viewport
        viewport.apply()

        // Set the ShapeRender's projection matrix
        renderer.projectionMatrix = viewport.camera.combined

        // Start a Filled batch
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        drawSmileyFace(renderer)
        renderer.end()
    }

    private fun drawSmileyFace(renderer: ShapeRenderer) {
        // Set the color to yellow, and draw the face
        renderer.color = Color.YELLOW
        renderer.circle(FACE_CENTER_X, FACE_CENTER_Y, FACE_RADIUS, FACE_SEGMENTS)

        // Set the color to black and draw the eyes
        renderer.color = Color.BLACK
        renderer.circle(FACE_CENTER_X - EYE_OFFSET, FACE_CENTER_Y + EYE_OFFSET, EYE_RADIUS, EYE_SEGMENTS)
        renderer.circle(FACE_CENTER_X + EYE_OFFSET, FACE_CENTER_Y + EYE_OFFSET, EYE_RADIUS, EYE_SEGMENTS)

        // Draw a black arc for the mouth (Hint: MOUTH_OUTER_RADIUS)
        renderer.arc(FACE_CENTER_X, FACE_CENTER_Y, MOUTH_OUTER_RADIUS, MOUTH_START_ANGLE, MOUTH_DEGREES, MOUTH_SEGMENTS)

        // Draw a yellow arc to make the mouth actually look like a mouth (Hint: MOUTH_INNER_RADIUS)
        renderer.color = Color.YELLOW
        renderer.arc(FACE_CENTER_X, FACE_CENTER_Y, MOUTH_INNER_RADIUS, MOUTH_START_ANGLE, MOUTH_DEGREES, MOUTH_SEGMENTS)
    }
}
