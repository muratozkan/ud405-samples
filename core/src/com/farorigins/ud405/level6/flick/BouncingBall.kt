package com.farorigins.ud405.level6.flick

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
        val DRAG = 1.0f

        val RADIUS_FACTOR = 1.0f / 20
        val RADIUS_GROWTH_RATE = 1.5f
        val MIN_RADIUS_MULTIPLIER = 0.1f

        val KICK_VELOCITY = 500.0f
        val FLICK_MULTIPLIER = 5.0f

        val ACCELERATION = 500.0f
        val MAX_SPEED = 4000.0f
    }

    private lateinit var position: Vector2
    private lateinit var velocity: Vector2

    private lateinit var flickStart: Vector2
    private var flicking = false

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
            randomKick();
        }
        if (keycode == Input.Keys.R) {
            init(viewport)
        }
        return true;
    }

    /**
     * Check out what happens when a touch starts
     *
     * When a touch starts, we first need to translate the point that the user touched from screen
     * coordinates to world coordinates. Since the viewport handles the projection from world
     * coordinates to screen coordinates, it also has an unproject() method that does the opposite.
     *
     * Next we use the Vector2.dst() method to see if the distance between the touch and the
     * position of the ball is smaller than the ball's radius. If the touch is inside the radius,
     * then we start a flick, and save the world coordinates of the touch.
     */
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val worldClick = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
        if (worldClick.dst(position) < baseRadius * radiusMultiplier) {
            flicking = true
            flickStart = worldClick
        }
        return true
    }

    /**
     * Check out what happens when a touch ends
     *
     * If we were in the process of flicking the ball, we calculate the vector between the start of
     * the flick and the end of the flick. Remember that the incoming position of the touch is in
     * screen coordinates, so we need to use the viewport to unproject that position into world
     * coordinates.
     *
     * Then we add that flick vector to the velocity of the ball, times some multiplier. Give it a
     * try!
     */
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (flicking) {
            flicking = false
            val flickEnd = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
            val flickVector = Vector2(flickEnd.x - flickStart.x, flickEnd.y - flickStart.y)
            velocity.mulAdd(flickVector, FLICK_MULTIPLIER)
        }

        return true
    }

    private fun randomKick() {
        val angle = MathUtils.PI2 * MathUtils.random()
        velocity.x = KICK_VELOCITY * MathUtils.cos(angle)
        velocity.y = KICK_VELOCITY * MathUtils.sin(angle)
    }

}
