package com.farorigins.ud405.level7.addtheicicles

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

// Add Vector2 for velocity
// Initialize velocity
class Icicle(private var position: Vector2) {

    private val velocity = Vector2.Zero.cpy()

    fun update(delta: Float) {
        // Update velocity using icicle acceleration constant
        velocity.mulAdd(Constants.ICICLE_ACCELERATION, delta)

        // Update position using velocity
        position.mulAdd(velocity, delta)
    }

    fun render(renderer: ShapeRenderer) {
        renderer.color = Constants.ICICLE_COLOR
        renderer.triangle(
                position.x, position.y,
                position.x - Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT,
                position.x + Constants.ICICLES_WIDTH / 2, position.y + Constants.ICICLES_HEIGHT)
    }
}
