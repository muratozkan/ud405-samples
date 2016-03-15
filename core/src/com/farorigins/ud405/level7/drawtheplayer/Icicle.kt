package com.farorigins.ud405.level7.drawtheplayer

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

// Add a constructor that sets the position
// Add a Vector2 position
class Icicle(private var position: Vector2) {

    // Add a render function that takes a ShapeRenderer
    fun render(renderer: ShapeRenderer) {

        // Set the ShapeRenderer's color
        renderer.color = Constants.ICICLE_COLOR

        // Set the ShapeType
        renderer.set(ShapeRenderer.ShapeType.Filled)

        // Draw the icicle using the size constants
        renderer.triangle(
                position.x, position.y,
                position.x - Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT,
                position.x + Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT)
    }
}
