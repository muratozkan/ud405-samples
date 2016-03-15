package com.farorigins.ud405.level7.addtheicicles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

class Player(private var viewport: Viewport) {

    companion object {
        val TAG = Player::class.java.name
    }

    private lateinit var position: Vector2

    init {
        init()
    }

    fun init() {
        position = Vector2(viewport.worldWidth / 2, Constants.PLAYER_HEAD_HEIGHT)
    }

    fun update(delta: Float) {
        // Use Gdx.input.isKeyPressed() to move the player in the appropriate direction when an arrow key is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= Constants.PLAYER_MOVEMENT_SPEED * delta
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += Constants.PLAYER_MOVEMENT_SPEED * delta
        }

        // Compute accelerometer input = raw input / (gravity * sensitivity)
        val accY = -Gdx.input.accelerometerY / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY)

        // Use the accelerometer input to move the player
        position.x += accY * -delta * Constants.PLAYER_MOVEMENT_SPEED

        ensureInBounds()
    }

    private fun ensureInBounds() {
        // Complete this function to ensure the player is within the viewport
        if (position.x - Constants.PLAYER_HEAD_RADIUS < 0) {
            position.x = Constants.PLAYER_HEAD_RADIUS
        }
        if (position.x + Constants.PLAYER_HEAD_RADIUS > viewport.worldWidth) {
            position.x = viewport.worldWidth - Constants.PLAYER_HEAD_RADIUS
        }
    }

    fun render(renderer: ShapeRenderer) {
        renderer.color = Constants.PLAYER_COLOR
        renderer.set(ShapeRenderer.ShapeType.Filled)
        renderer.circle(position.x, position.y, Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_HEAD_SEGMENTS)

        val torsoTop = Vector2(position.x, position.y - Constants.PLAYER_HEAD_RADIUS)
        val torsoBottom = Vector2(torsoTop.x, torsoTop.y - 2 * Constants.PLAYER_HEAD_RADIUS)

        renderer.rectLine(torsoTop, torsoBottom, Constants.PLAYER_LIMB_WIDTH)

        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x + Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH)

        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x - Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH)

        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x + Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH)

        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x - Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH)
    }
}
