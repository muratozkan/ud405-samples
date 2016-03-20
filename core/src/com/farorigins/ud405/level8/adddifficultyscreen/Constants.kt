package com.farorigins.ud405.level8.adddifficultyscreen

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

    // Add constants for the color of each difficulty select circle
    val EASY_COLOR = Color(0.2f, 0.2f, 1f, 1f)
    val MEDIUM_COLOR = Color(0.5f, 0.5f, 1f, 1f)
    val HARD_COLOR = Color(0.7f, 0.7f, 1f, 1f)

    // Add constant for the size of the difficulty world
    val DIFFICULTY_WORLD_SIZE = 480.0f

    // Add constant for the radius of the difficulty select "buttons"
    val DIFFICULTY_BUBBLE_RADIUS = DIFFICULTY_WORLD_SIZE / 9

    // Add constant for the scale of the difficulty labels (1.5 works well)
    val DIFFICULTY_LABEL_SCALE = 1.5f

    // Add Vector2 constants for the centers of the difficulty select buttons
    val EASY_CENTER = Vector2(DIFFICULTY_WORLD_SIZE / 4, DIFFICULTY_WORLD_SIZE / 2)
    val MEDIUM_CENTER = Vector2(DIFFICULTY_WORLD_SIZE / 2, DIFFICULTY_WORLD_SIZE / 2)
    val HARD_CENTER = Vector2(DIFFICULTY_WORLD_SIZE * 3 / 4, DIFFICULTY_WORLD_SIZE / 2)

}

enum class Difficulty(val spawnRate: Float, val label: String) {
    EASY(5f, "Cold"),
    MEDIUM(15f, "Colder"),
    HARD(25f, "Coldest")
}
