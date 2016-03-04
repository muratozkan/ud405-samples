package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * Your challenge, should you choose to accept it, is to draw a Sierpinski Triangle. I offer no hints
 * beyond the fact that ShapeRenderer has a very convenient triangle() function, and that using a
 * FitViewport can simplify matters considerably. Good luck!
 */
class SierpinskiTriangle : ApplicationAdapter() {

    companion object {
        val SIZE = 10f
        val RECURSIONS = 7
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: Viewport

    override fun create() {
        renderer = ShapeRenderer()
        viewport = FitViewport(SIZE, SIZE)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()
        renderer.projectionMatrix = viewport.camera.combined

        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE

        drawSierpinski(0f, 0f, SIZE, RECURSIONS)
        // inscribeSierpinskiTriangle(SIZE, RECURSIONS)

        renderer.end()
    }

    private fun drawSierpinski(x: Float, y: Float, size: Float, recursion: Int) {
        if (recursion < 0)
            return

        val sizeHalf = size / 2

        renderer.triangle(x, y, x + sizeHalf, y + size, x + size, y)

        drawSierpinski(x + sizeHalf / 2, y + sizeHalf, sizeHalf, recursion - 1)
        drawSierpinski(x, y, sizeHalf, recursion - 1)
        drawSierpinski(x + sizeHalf, y, sizeHalf, recursion - 1)
    }

    // Course author's solution
    private fun inscribeSierpinskiTriangle(size: Float, recursions: Int) {
        val corner1 = Vector2(0f, 0f)
        val corner2 = Vector2(size, 0f)
        val corner3 = Vector2(size / 2, size * MathUtils.sin(MathUtils.PI / 3f))
        drawSierpinskiTriangle(corner1, corner2, corner3, recursions)
    }

    private fun drawSierpinskiTriangle(corner1: Vector2, corner2: Vector2, corner3: Vector2, recursions: Int) {
        val midpoint12 = Vector2((corner1.x + corner2.x) / 2, (corner1.y + corner2.y) / 2)
        val midpoint23 = Vector2((corner2.x + corner3.x) / 2, (corner2.y + corner3.y) / 2)
        val midpoint31 = Vector2((corner3.x + corner1.x) / 2, (corner3.y + corner1.y) / 2)

        if (recursions == 1) {
            renderer.triangle(corner1.x, corner1.y, midpoint12.x, midpoint12.y, midpoint31.x, midpoint31.y)
            renderer.triangle(corner2.x, corner2.y, midpoint23.x, midpoint23.y, midpoint12.x, midpoint12.y)
            renderer.triangle(corner3.x, corner3.y, midpoint31.x, midpoint31.y, midpoint23.x, midpoint23.y)
        } else {
            drawSierpinskiTriangle(corner1, midpoint12, midpoint31, recursions - 1)
            drawSierpinskiTriangle(corner2, midpoint23, midpoint12, recursions - 1)
            drawSierpinskiTriangle(corner3, midpoint31, midpoint23, recursions - 1)
        }
    }
}
