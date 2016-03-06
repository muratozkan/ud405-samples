package com.farorigins.ud405.level6.inputtestbed

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport

/**
 *
 * The behavior of this ball should be familiar from the screensaver example. The two new things are
 * drag, and the periodic kicks the ball relieves to show off that drag.
 *
 * If we run this project, we see a little red ball that occasionally gets kicked in a random
 * direction, the slowly comes to a stop. It kinda looks like an air-hockey table. However, it's not
 * interactive yet. Let's fix that.
 */
class BouncingBall(viewport: Viewport) {

    companion object {
        val COLOR = Color.RED
        val DRAG = 1.0f

        // This constant defines how big the ball should be relative to the world size
        val RADIUS_FACTOR = 1.0f / 20

        val KICK_INTERVAL = 3.0f
        val KICK_VELOCITY = 500.0f
    }

    private lateinit var position: Vector2
    private lateinit var velocity: Vector2

    private var radius = 0f
    private var lastKick = 0L

    init {
        init(viewport)
    }

    fun init(viewport: Viewport) {
        position = Vector2(viewport.worldWidth / 2, viewport.worldHeight / 2)
        velocity = Vector2()
        randomKick()
    }

    private fun randomKick() {
        val angle = MathUtils.PI2 * MathUtils.random()
        velocity.x = KICK_VELOCITY * MathUtils.cos(angle)
        velocity.y = KICK_VELOCITY * MathUtils.sin(angle)
    }

    fun update(delta: Float, viewport: Viewport) {

        val secondsSinceLastKick = MathUtils.nanoToSec * (TimeUtils.nanoTime() - lastKick)

        if (secondsSinceLastKick > KICK_INTERVAL) {
            lastKick = TimeUtils.nanoTime()
            randomKick()
        }

        // Drag is proportional to the current velocity
        velocity.x -= delta * DRAG * velocity.x
        velocity.y -= delta * DRAG * velocity.y

        position.x += delta * velocity.x
        position.y += delta * velocity.y
        radius = RADIUS_FACTOR * Math.min(viewport.worldWidth, viewport.worldHeight)

        collideWithWalls(radius, viewport.worldWidth, viewport.worldHeight)
    }

    fun collideWithWalls(radius: Float, viewportWidth: Float, viewportHeight: Float) {
        if (position.x - radius < 0) {
            position.x = radius
            velocity.x = -velocity.x
        }
        if (position.x + radius > viewportWidth) {
            position.x = viewportWidth - radius
            velocity.x = -velocity.x
        }
        if (position.y - radius < 0) {
            position.y = radius
            velocity.y = -velocity.y
        }
        if (position.y + radius > viewportHeight) {
            position.y = viewportHeight - radius
            velocity.y = -velocity.y
        }
    }

    fun render(renderer: ShapeRenderer) {
        // This takes advantage of AutoShapeType
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.color = COLOR
        renderer.circle(position.x, position.y, radius)
    }
}
