package com.farorigins.ud405.level8.adddifficultylevels

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

    val HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f
    val HUD_MARGIN = 20.0f

    // Create constants for difficulty labels ("Cold", "Colder", "Coldest")
    val EASY_LABEL = "Cold"
    val MEDIUM_LABEL = "Colder"
    val HARD_LABEL = "Coldest"

    // Create constants for the icicle spawn rates for the various difficulties
    val EASY_SPAWNS_PER_SECOND = 5
    val MEDIUM_SPAWNS_PER_SECOND = 15
    val HARD_SPAWNS_PER_SECOND = 25
}

// Create Difficulty enum holding the spawn rate and label for each difficulty
enum class Difficulty(val spawnRate: Float, val label: String) {
    EASY(5f, "Cold"),
    MEDIUM(15f, "Colder"),
    HARD(25f, "Coldest")
}
