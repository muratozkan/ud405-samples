package com.farorigins.ud405.level8.addthehud

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

    // Add counter for number of deaths
    // Set number of deaths to zero
    var deaths = 0; private set

    init {
        init()
    }

    fun init() {
        position = Vector2(viewport.worldWidth / 2, Constants.PLAYER_HEAD_HEIGHT)
    }

    fun update(delta: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= Constants.PLAYER_MOVEMENT_SPEED * delta
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += Constants.PLAYER_MOVEMENT_SPEED * delta
        }

        val accY = -Gdx.input.accelerometerY / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY)

        position.x += accY * -delta * Constants.PLAYER_MOVEMENT_SPEED

        ensureInBounds()
    }

    private fun ensureInBounds() {
        if (position.x - Constants.PLAYER_HEAD_RADIUS < 0) {
            position.x = Constants.PLAYER_HEAD_RADIUS
        }
        if (position.x + Constants.PLAYER_HEAD_RADIUS > viewport.worldWidth) {
            position.x = viewport.worldWidth - Constants.PLAYER_HEAD_RADIUS
        }
    }

    fun hitByIcicle(icicles: Icicles): Boolean {
        val isHit = icicles.icicles.any { ic -> position.dst(ic.position) < Constants.PLAYER_HEAD_RADIUS }

        // If the player was hit, increment death counter
        if (isHit) {
            deaths += 1
        }

        return isHit
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
