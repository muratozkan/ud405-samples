package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * In this demo, we'll use ShapeRenderer to draw some lines! We'll use most of the line drawing
 * methods offered by ShapeRenderer, but remember to check out the Javadocs for the full story. If
 * you're lazy, you can just Google "LibGDX ShapeRenderer", and you'll find what you're looking
 * for!
 */
class DrawingLines : ApplicationAdapter() {

    private lateinit var shapeRenderer: ShapeRenderer

    override fun create() {
        // Remember we want to create our ShapeRenderer outside of our render callback
        shapeRenderer = ShapeRenderer()
    }

    override fun dispose() {
        // Also remember to clean up
        shapeRenderer.dispose()
    }

    override fun render() {
        // As always, first we clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // Then we start our shapeRenderer batch, this time with ShapeType.Line
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        // A Simple white line
        shapeRenderer.color = Color.WHITE
        shapeRenderer.line(0f, 0f, 100f, 100f)
        // We can set different colors using two methods. We can use constants like so.
        shapeRenderer.color = Color.MAGENTA
        shapeRenderer.line(10f, 0f, 110f, 100f)
        // We can also set a color using RGBA values
        shapeRenderer.setColor(0f, 1f, 0f, 1f)
        shapeRenderer.line(20f, 0f, 120f, 100f)
        // We can also do fancy things like gradients
        shapeRenderer.line(30f, 0f, 130f, 100f, Color.BLUE, Color.RED)
        // The last interesting thing we can do is draw a bunch of connected line segments using polyline
        // First we set up the list of vertices, where the even positions are x coordinates, and the odd positions are the y coordinates
        val vertices = floatArrayOf(100f, 200f, 300f, 300f, 200f, 300f, 300f, 200f)
        shapeRenderer.polyline(vertices)
        // Finally, as always, we end the batch
        shapeRenderer.end()
    }
}
