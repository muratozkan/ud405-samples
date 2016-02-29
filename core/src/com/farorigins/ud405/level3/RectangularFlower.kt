package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


/**
 * In this exercise, we're going to draw a flower using only rectangles! We've already started off
 * with a green rectline for the stem. First you'll draw a couple leaves using rotated rectangles.
 *
 * Then you'll draw the head of the flower by drawing a a bunch of rotated squares!
 */
class RectangularFlower : ApplicationAdapter() {

    internal lateinit var shapeRenderer: ShapeRenderer

    override fun create () {
        shapeRenderer = ShapeRenderer()
    }

    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }

    override fun render () {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.GREEN
        shapeRenderer.rectLine(100f, 0f, 100f, 300f, 20f)

        // Draw two leaves on the stem
        shapeRenderer.rect(100f, 100f, 0f, 0f, 40f, 40f, 1f, 1f, 135f)
        shapeRenderer.rect(100f, 150f, 0f, 0f, 30f, 30f, 1f, 1f, 315f)

        // Set the active color to yellow
        shapeRenderer.color = Color.YELLOW
        // Use a loop to draw 20 of these petals in a circle
        val petalAngle = 18f;
        (0 .. 20).forEach { i ->
            shapeRenderer.rect(100f, 300f, 0f, 0f, 40f, 40f, 1f, 1f, petalAngle * i)
        }

        shapeRenderer.end()
    }
}

