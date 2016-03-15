package com.farorigins.ud405.level7.drawtheplayer

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

// Add a viewport
class Player(private var viewport: Viewport) {

    companion object {
        val TAG = Player::class.java.name
    }

    // Add a position (add constants to Constants.java first)
    private lateinit var position: Vector2


    // Add constructor that accepts and sets the viewport, then calls init()
    init {
        init()
    }

    // Add init() function that moves the character to the bottom center of the screen
    fun init() {
        position = Vector2(viewport.worldWidth / 2, Constants.PLAYER_HEAD_HEIGHT)
    }

    // Create a render function that accepts a ShapeRenderer and does the actual drawing
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
