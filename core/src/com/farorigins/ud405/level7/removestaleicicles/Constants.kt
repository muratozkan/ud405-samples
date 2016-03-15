package com.farorigins.ud405.level7.removestaleicicles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

object Constants {
    val WORLD_SIZE = 10.0f
    val BACKGROUND_COLOR = Color.BLUE

    val PLAYER_HEAD_RADIUS = 0.5f
    val PLAYER_HEAD_HEIGHT = 4f * PLAYER_HEAD_RADIUS
    val PLAYER_LIMB_WIDTH = 0.1f
    val PLAYER_HEAD_SEGMENTS = 20
    val PLAYER_COLOR = Color.BLACK
    val PLAYER_MOVEMENT_SPEED = 10f

    val ACCELEROMETER_SENSITIVITY = 0.5f
    val GRAVITATIONAL_ACCELERATION = 9.8f

    val ICICLES_HEIGHT = 1.0f
    val ICICLES_WIDTH = 0.5f
    val ICICLE_COLOR = Color.WHITE
    val ICICLE_ACCELERATION = Vector2(0f, -GRAVITATIONAL_ACCELERATION / 2)
    val ICICLE_SPAWNS_PER_SECOND = 10f
}
