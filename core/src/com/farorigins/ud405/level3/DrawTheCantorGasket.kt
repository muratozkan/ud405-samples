package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

/**
 *
 * The Cantor gasket is a fractal where we start with a white square. We divide that square up
 * into a 3x3 grid of smaller squares, then remove the middle square. Finally, we repeat the process
 * on each of the remaining 8 squares.
 */
class DrawTheCantorGasket : ApplicationAdapter() {

    internal lateinit var shapeRenderer: ShapeRenderer

    // Set a constant for how many recursions to draw. 5 is a good place to start
    val recursions = 5

    override fun create() {
        shapeRenderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Finds a good place to draw our fractal
        // Rectangle has members x,y for the lower left corner, and width and height
        val bounds = findLargestSquare()

        // Begin a filled shapeRenderer batch
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        // Draw a white square matching the bounds
        shapeRenderer.color = Color.WHITE
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height)
        // Set the working color to black, and call punchCantorGasket with the bounds
        shapeRenderer.color = Color.BLACK
        punchCantorGasket(bounds.x, bounds.y, bounds.width, recursions)

        // End the batch
        shapeRenderer.end()
    }

    internal fun punchCantorGasket(x: Float, y: Float, size: Float, recursions: Int) {
        // Base case, if recursions = 0, return
        if (recursions == 0)
            return

        val subSize = size / 3f
        // Draw a black square in the middle square
        shapeRenderer.rect(x + subSize, y + subSize, subSize, subSize)

        // Call punchCantorGasket on all 8 other squares
        (0..2).map { i ->
            (0..2).map { j ->
                if ((i != 1) or (i != j))
                    punchCantorGasket(x + (subSize * i), y + (subSize * j), subSize, recursions - 1)
            }
        }
    }

    internal fun findLargestSquare(): Rectangle {
        val largestSquare = Rectangle()
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()

        if (screenWidth > screenHeight) {
            largestSquare.x = (screenWidth - screenHeight) / 2f
            largestSquare.y = 0f
            largestSquare.width = screenHeight
            largestSquare.height = screenHeight
        } else {
            largestSquare.x = 0f
            largestSquare.y = (screenHeight - screenWidth) / 2f
            largestSquare.width = screenWidth
            largestSquare.height = screenWidth
        }
        return largestSquare
    }
}
