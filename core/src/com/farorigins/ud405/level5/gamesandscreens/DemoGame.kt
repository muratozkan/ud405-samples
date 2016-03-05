package com.farorigins.ud405.level5.gamesandscreens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx

/**
 * 1. Check out the Game class first
 *
 * Instead of an ApplicationAdapter we're using a Game, which extends ApplicationListener.
 *
 * If we check out Game, we see it's got a screen member variable, and it seems that, for every
 * callback that ApplicationListener implements, it checks to see if it's got a Screen, and if so,
 * it delegates that callback to the Screen.
 */
class DemoGame : Game() {

    companion object {
        val TAG = DemoGame::class.java.name
    }

    private lateinit var deltaScreen: DeltaScreen
    private lateinit var textScreen: FPSScreen

    /**
     * So let's see this in action. Here we've declared two screens. One of which displays the
     * number of frames displayed per second, and the other displays the number of seconds between
     * the current frame and the last one.
     *
     * To set the active screen, we just need to call setScreen() on our Game.
     *
     * Let's check out the DeltaScreen.
     */
    override fun create() {
        Gdx.app.log(TAG, "create() called")
        deltaScreen = DeltaScreen()
        textScreen = FPSScreen()
        setScreen(deltaScreen)

        // We'll talk about this soon. This lets us hit spacebar to swap screens
        Gdx.input.inputProcessor = ScreenSwitcher(this, deltaScreen, textScreen)
    }

    override fun dispose() {
        Gdx.app.log(TAG, "dispose() called")
        super.dispose()
    }
}