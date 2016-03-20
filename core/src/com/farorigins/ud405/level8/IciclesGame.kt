package com.farorigins.ud405.level8

import com.badlogic.gdx.Game
import com.farorigins.ud405.level8.adddifficultyscreen.Difficulty
import com.farorigins.ud405.level8.adddifficultyscreen.DifficultyScreen
import com.farorigins.ud405.level8.adddifficultyscreen.IciclesScreen

class IciclesGame : Game() {

    // Uncomment for collisiondetection, addthehud, adddifficultylevels
    //    override fun create() {
    //        setScreen(IciclesScreen(Difficulty.EASY))
    //    }

    override fun create() {
        showDifficultyScreen()
    }

    fun showDifficultyScreen() {
        // Show the difficulty screen
        setScreen(DifficultyScreen(this))
    }

    fun showIciclesScreen(difficulty: Difficulty) {
        // Show the Icicles screen with the appropriate difficulty
        setScreen(IciclesScreen(difficulty, this))
    }
}
