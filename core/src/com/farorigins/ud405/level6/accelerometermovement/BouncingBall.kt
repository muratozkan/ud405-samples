package com.farorigins.ud405.level6.accelerometermovement

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

class BouncingBall(private val viewport: Viewport) : InputAdapter() {

    companion object {
        val COLOR = Color.RED
        val DRAG = 0.5f

        val RADIUS_FACTOR = 1.0f / 20
        val RADIUS_GROWTH_RATE = 1.5f
        val MIN_RADIUS_MULTIPLIER = 0.1f

        val ACCELERATION = 500.0f
        val MAX_SPEED = 4000.0f

        val ACCELEROMETER_SENSITIVITY = 0.5f
        val ACCELERATION_OF_GRAVITY = 9.8f

        val KICK_VELOCITY = 500.0f
    }

    private lateinit var position: Vector2
    private lateinit var velocity: Vector2

    private lateinit var flickStart: Vector2
    private var flicking = false

    // Declare a Vector2 to hold the ball's target position
    private lateinit var targetPosition: Vector2
    // Declare a boolean to hold whether the ball is following something (and set it to false)
    private var following = false

    private var baseRadius = 0f
    private var radiusMultiplier = 1f

    init {
        init(viewport)
    }

    fun init(viewport: Viewport) {
        position = Vector2(viewport.worldWidth / 2, viewport.worldHeight / 2)
        velocity = Vector2()
        radiusMultiplier = 1f
        baseRadius = RADIUS_FACTOR * Math.min(viewport.worldWidth, viewport.worldHeight)
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

        if (following) {
            velocity.x = 2 * (targetPosition.x - position.x)
            velocity.y = 2 * (targetPosition.y - position.y)
        }

        // Movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x -= delta * ACCELERATION
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x += delta * ACCELERATION
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y += delta * ACCELERATION
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y -= delta * ACCELERATION
        }

        // Accelerometer Movement

        // Get the accelerometer reading for the x axis (remember that the reading will be relative to the
        // portrait device orientation)
        val xAxis = -Gdx.input.accelerometerY
        // Get the accelerometer reading for the y axis
        val yAxis = Gdx.input.accelerometerX

        // Use ACCELERATION, ACCELEROMETER_SENSITIVITY, and ACCELERATION_OF_GRAVITY to determine the acceleration
        // to apply to the ball
        // Note we want to negate the value, since we want to fall away from the direction of the accelerometer
        // readings
        val accelerationX = -ACCELERATION * xAxis / (ACCELEROMETER_SENSITIVITY * ACCELERATION_OF_GRAVITY)
        val accelerationY = -ACCELERATION * yAxis / (ACCELEROMETER_SENSITIVITY * ACCELERATION_OF_GRAVITY)

        // Apply that acceleration to the ball
        velocity.x += delta * accelerationX
        velocity.y += delta * accelerationY

        velocity.clamp(0f, MAX_SPEED)

        velocity.x -= delta * DRAG * velocity.x
        velocity.y -= delta * DRAG * velocity.y

        position.x += delta * velocity.x
        position.y += delta * velocity.y

        collideWithWalls(baseRadius, viewport.worldWidth, viewport.worldHeight)
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
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.color = COLOR
        renderer.circle(position.x, position.y, baseRadius * radiusMultiplier)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.SPACE) {
            randomKick()
        }
        if (keycode == Input.Keys.R) {
            init(viewport)
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldClick = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
        if (worldClick.dst(position) < baseRadius * radiusMultiplier) {
            Gdx.app.log("Ball", "Click in the ball, starting flick.")
            flicking = true
            flickStart = worldClick
        } else {
            Gdx.app.log("Ball", "Click around ball, starting follow.")
            targetPosition = worldClick
            following = true
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        targetPosition = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (flicking) {
            flicking = false
            val flickEnd = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
            velocity.x += 3 * (flickEnd.x - flickStart.x)
            velocity.y += 3 * (flickEnd.y - flickStart.y)
            Gdx.app.log("Ball", "End flick")
        }
        following = false

        return true
    }

    private fun randomKick() {
        val angle = MathUtils.PI2 * MathUtils.random()
        velocity.x = KICK_VELOCITY * MathUtils.cos(angle)
        velocity.y = KICK_VELOCITY * MathUtils.sin(angle)
    }
}
