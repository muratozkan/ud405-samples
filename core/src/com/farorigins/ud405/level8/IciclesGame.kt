package com.farorigins.ud405.level8

import com.badlogic.gdx.Game
import com.farorigins.ud405.level8.adddifficultyscreen.Difficulty
import com.farorigins.ud405.level8.adddifficultyscreen.IciclesScreen

class IciclesGame : Game() {
    // call setScreen() with a new IciclesScreen()
    override fun create() {
        setScreen(IciclesScreen(Difficulty.EASY))
    }
}
