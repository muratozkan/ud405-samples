package com.farorigins.ud405.level5.fallingobjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.*

class Boulder(val viewport: Viewport) {

    companion object {
        const val RADIUS_RATIO = 0.01f
        val COLOR = Color.RED

        // Declare a constant holding the acceleration due to gravity. -20 works well
        const val GRAVITY = -20f
        const val WIND_ACCELERATION = 5f
    }

    private var position: Vector2
    private var velocity: Vector2
    private var radius = 0f

    private val random = Random()

    init {
        position = Vector2()
        // Set the initial velocity to zero in both directions
        velocity = Vector2.Zero.cpy()

        radius = viewport.worldWidth * RADIUS_RATIO
        position.y = viewport.worldHeight + radius

        position.x = random.nextFloat() * (viewport.worldWidth - 2 * radius) + radius
    }

    fun update(delta: Float) {
        // Apply gravitational acceleration to the vertical velocity
        velocity.y += delta * GRAVITY
        // Challenge - Add wind blowing from the side
        val windAcc = if (random.nextInt(10) > 6) 1 else -1
        velocity.x += delta * WIND_ACCELERATION * windAcc

        position.x += delta * velocity.x
        position.y += delta * velocity.y
    }

    fun isBelowScreen(): Boolean {
        return position.y < -radius
    }

    fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.color = COLOR
        renderer.circle(position.x, position.y, radius)
    }
}

