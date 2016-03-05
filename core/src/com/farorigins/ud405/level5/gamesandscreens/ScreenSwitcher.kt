package com.farorigins.ud405.level5.gamesandscreens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen

/**
 * We'll talk about input handling in the next level!
 */
class ScreenSwitcher(val game: Game, val screen1: Screen, val screen2: Screen) : InputAdapter() {

    private var currentScreen = 1

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.SPACE) {
            if (currentScreen == 1) {
                currentScreen = 2
                game.screen = screen2
            } else {
                currentScreen = 1
                game.screen = screen1
            }
        }
        return true
    }
}
