package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * In this exercise you'll set up a ShapeRenderer, and use it to draw a stick figure. At minimum,
 * you'll need a circle for the head, and five lines for the torso, arms, and legs.
 *
 * Remember to set up a ShapeRenderer you'll need to declare it, initialize it, and dispose of it.
 * Then to actually use the ShapeRenderer you'll need to start a batch of the appropriate type, draw
 * your shapes, and end the batch.
 *
 * We don't have step-by-step TODOs for this one, since the aim is for you to remember the steps for
 * setting up a ShapeRenderer and be able to set one up on your own. Of course, the solution is
 * available if you run into anything confusing.
 */
class StickFigure : ApplicationAdapter() {

    private lateinit var renderer: ShapeRenderer

    override fun create() {
        renderer = ShapeRenderer()
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.begin(ShapeRenderer.ShapeType.Filled)

        // Head
        renderer.circle(100f, 100f, 10f)
        renderer.end()

        renderer.begin(ShapeRenderer.ShapeType.Line)

        // Torso
        renderer.line(100f, 50f, 100f, 100f)

        // Legs
        renderer.line(85f, 35f, 100f, 50f)
        renderer.line(115f, 35f, 100f, 50f)

        // Arms
        renderer.line(85f, 70f, 100f, 85f)
        renderer.line(115f, 70f, 100f, 85f)
        renderer.end()
    }
}