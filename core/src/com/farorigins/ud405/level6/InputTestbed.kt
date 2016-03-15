package com.farorigins.ud405.level6

import com.badlogic.gdx.Game
import com.farorigins.ud405.level6.pollingdemo.BallScreen

// Set the correct BallScreen here for different demos
class InputTestbed : Game() {
    override fun create() {
        setScreen(BallScreen())
    }
}
