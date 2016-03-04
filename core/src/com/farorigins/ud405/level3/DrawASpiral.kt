package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
 * In this exercise we have a project that draws a number of concentric rectangles as specified in
 * the COILS constant. The space between the rectangles is given by xStep and yStep.
 *
 * The rectangles are drawn using four lines between five points. Your task is to adjust the first
 * and last point such that each rectangle turns into a coil that meets up with the neighboring
 * coils inside and outside of it.
 */
class DrawASpiral : ApplicationAdapter() {

    companion object {
        private val COILS = 20
    }

    private lateinit var renderer: ShapeRenderer

    override fun create() {
        renderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.begin(ShapeRenderer.ShapeType.Line)

        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        val xStep = screenWidth / 2 / COILS
        val yStep = screenHeight / 2 / COILS

        for (i in 0..COILS - 1) {

            val xOffset = xStep * i
            val yOffset = yStep * i

            // Make this coil reach back to the outer coil
            val point1 = Vector2(xOffset - xStep, yOffset)
            val point2 = Vector2(screenWidth - xOffset, yOffset)
            val point3 = Vector2(screenWidth - xOffset, screenHeight - yOffset)
            val point4 = Vector2(xOffset, screenHeight - yOffset)
            // Make coil stop before connecting back to itself
            val point5 = Vector2(xOffset, yOffset + yStep)

            renderer.line(point1, point2)
            renderer.line(point2, point3)
            renderer.line(point3, point4)
            renderer.line(point4, point5)
        }

        renderer.end()
    }
}
