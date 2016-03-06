package com.farorigins.ud405.level6.arrowkeymovement

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport

class BouncingBall(viewport: Viewport) {

    companion object {
        val COLOR = Color.RED
        val DRAG = 1.0f

        val BASE_RADIUS = 20.0f
        val RADIUS_GROWTH_RATE = 2.5f
        val MIN_RADIUS_MULTIPLIER = 0.1f

        val ACCELERATION = 500.0f
        val MAX_SPEED = 1000.0f
    }

    private lateinit var position: Vector2
    private lateinit var velocity: Vector2

    private var radius = 0f
    private var radiusMultiplier = 1f

    init {
        init(viewport)
    }

    fun init(viewport: Viewport) {
        position = Vector2(viewport.worldWidth / 2, viewport.worldHeight / 2)
        velocity = Vector2()
        radiusMultiplier = 1f
        radius = BASE_RADIUS * radiusMultiplier
    }

    fun update(delta: Float, viewport: Viewport) {
        // Growing and shrinking
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            radiusMultiplier += delta * RADIUS_GROWTH_RATE
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            radiusMultiplier -= delta * RADIUS_GROWTH_RATE
            radiusMultiplier = Math.max(radiusMultiplier, MIN_RADIUS_MULTIPLIER)
        }
        radius = radiusMultiplier * BASE_RADIUS

        // Subtract delta * ACCELERATION from velocity.x if the left arrow key is pressed (Hint: Keys.LEFT)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x -= delta * ACCELERATION
        }

        // Handle Keys.RIGHT
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x += delta * ACCELERATION
        }

        // Handle Keys.UP
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y += delta * ACCELERATION
        }

        // Handle Keys.DOWN
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y -= delta * ACCELERATION
        }

        // Use velocity.clamp() to limit the total speed to MAX_SPEED
        velocity.clamp(0f, MAX_SPEED)

        // Drag is proportional to the current velocity
        velocity.x -= delta * DRAG * velocity.x
        velocity.y -= delta * DRAG * velocity.y

        position.x += delta * velocity.x
        position.y += delta * velocity.y

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
