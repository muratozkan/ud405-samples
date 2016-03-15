package com.farorigins.ud405.level7.thefirsticicle

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

// Add a Vector2 position
// Add a constructor that sets the position
class Icicle(private var position: Vector2) {

    companion object {
        val TAG = Icicle::class.java.name
    }

    // Add a render function that takes a ShapeRenderer
    fun render(renderer: ShapeRenderer) {
        // Set the ShapeRenderer's color
        renderer.color = Constants.ICICLE_COLOR

        // Set the ShapeType
        renderer.set(ShapeRenderer.ShapeType.Filled)

        // Draw the icicle using the size constants
        val halfWidth = Constants.ICICLE_WIDTH / 2
        val upperBound = position.y + Constants.ICICLE_HEIGHT
        renderer.triangle(position.x, position.y,
                position.x - halfWidth, upperBound,
                position.x + halfWidth, upperBound)
    }
}