package com.farorigins.ud405.level5.gamesandscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align

/**
 * 3 Check out this screen class third
 *
 * In this screen, we're doing almost the same thing as in DeltaScreen, except we're instead
 * displaying the current frames per second. Let's try running this game, and see how the
 * Game/Screen lifecycle works.
 *
 * When we start up the app, first the Game is created, then we called setScreen() with a
 * DeltaScreen, so DeltaScreen has its show and resize methods called. If we hit space to swap out
 * screens, hide is called on DeltaScreen, and show and resize are called on FPS screen.
 *
 * Finally, when we close the game, hide is called on the active screen, then the dispose method is
 * called on the Game, which cleans up the screens as well.
 */
class FPSScreen : Screen {

    companion object {
        val TAG = FPSScreen::class.java.name

        private val FONT_SCALE = 3.0f
    }

    private lateinit var font: BitmapFont
    private lateinit var batch: SpriteBatch

    override fun show() {
        Gdx.app.log(TAG, "show() called")
        batch = SpriteBatch()
        font = BitmapFont()
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        font.data.setScale(FONT_SCALE)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        font.draw(batch, "FPS = " + Gdx.graphics.framesPerSecond, Gdx.graphics.width / 4f, Gdx.graphics.height / 2f, 0f, Align.left, false)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log(TAG, "resize($width, $height) called")
    }

    override fun pause() {
        Gdx.app.log(TAG, "pause() called")
    }

    override fun resume() {
        Gdx.app.log(TAG, "resume() called")
    }

    override fun hide() {
        Gdx.app.log(TAG, "hide() called")
    }

    override fun dispose() {
        Gdx.app.log(TAG, "dispose() called")
        batch.dispose()
    }
}
