package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class CirclesAndArcs : ApplicationAdapter() {

    private lateinit var renderer: ShapeRenderer

    override fun create() {
        renderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.WHITE

        // The most basic circle you can draw, with the segment count set for you
        renderer.circle(100f, 100f, 90f)
        renderer.color = Color.YELLOW

        // We can also draw partial circle, or arc
        renderer.arc(300f, 100f, 90f, 45f, 270f)

        // What happens when we set the segments count too low
        renderer.circle(500f, 100f, 90f, 10)
        renderer.end()

        // Circles can be drawn in either Filled or Line mode!
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.circle(100f, 300f, 90f)

        // Let's draw target rings
        for (i in 8 downTo 1) {
            renderer.circle(100f, 300f, i * 10f)
        }

        // We can also draw the outline of an arc
        renderer.arc(300f, 300f, 90f, 0f, 90f)

        // Let's draw some a funky snail shell
        val arcs = 20
        for (i in 1..arcs) {
            renderer.arc(300f, 300f, (1 - 1.0f * i / arcs) * 90, 360.0f * i / arcs, 90f)
        }
        renderer.end()
    }
}
