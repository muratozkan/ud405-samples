package com.farorigins.ud405.level5.screensaver

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

import java.util.*

/**
 * This class represents a ball, bouncing around the screen. It maintains a position and velocity, and it needs to knows how to update its position, based on how much time has passed.
 *
 * and has basic physics for colliding with the "walls" (the edges of the screen).
 */
class BouncingBall(viewport: Viewport) {

    companion object {
        val COLOR = Color.RED
        const val RADIUS_FACTOR = 1.0f / 20
        const val KICK_VELOCITY = 500.0f
    }

    private lateinit var position: Vector2
    private lateinit var velocity: Vector2

    private var radius = 0f

    init {
        init(viewport)
    }

    fun update(delta: Float, viewport: Viewport) {
        // Update the ball's position using its velocity
        position.x += delta * velocity.x
        position.y += delta * velocity.y

        collideWithWalls(radius, viewport.worldWidth, viewport.worldHeight)
    }

    fun init(viewport: Viewport) {
        position = Vector2(viewport.worldWidth / 2, viewport.worldHeight / 2)
        velocity = Vector2()
        radius = RADIUS_FACTOR * Math.min(viewport.worldWidth, viewport.worldHeight)
        randomKick()
    }

    private fun randomKick() {
        val random = Random()
        val angle = MathUtils.PI2 * random.nextFloat()
        velocity.x = KICK_VELOCITY * MathUtils.cos(angle)
        velocity.y = KICK_VELOCITY * MathUtils.sin(angle)
    }

    private fun collideWithWalls(radius: Float, viewportWidth: Float, viewportHeight: Float) {
        if (position.x - radius < 0) {
            position.x = radius
            velocity.x = -velocity.x
        }
        if (position.x + radius > viewportWidth) {
            position.x = viewportWidth - radius
            velocity.x = -velocity.x
        }

        // Make the ball bounce off the bottom of the screen
        if (position.y - radius < 0) {
            position.y = radius
            velocity.y = -velocity.y
        }
        // Make the ball bounce off the top of the screen
        if (position.y + radius > viewportHeight) {
            position.y = viewportHeight - radius
            velocity.y = -velocity.y
        }
    }

    fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.color = COLOR
        renderer.circle(position.x, position.y, radius)
    }
}

