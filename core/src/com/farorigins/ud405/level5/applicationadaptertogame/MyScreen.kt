package com.farorigins.ud405.level5.applicationadaptertogame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * Start Here!
 *
 * First run what we've got so far.
 *
 * Declare that MyScreen implements Screen (com.badlogic.gdx.Screen), and hit Ctrl-i to insert all required methods.
 *
 * Move all member variables from MyGame to MyScreen
 *
 * Move everything from MyGame.create() to MyScreen.show()
 *
 * Move everything from MyGame.dispose() to MyScreen.hide()
 *
 * Move everything from MyGame.resize() to MyScreen.resize()
 *
 * Move everything from MyGame.render() to MyScreen.render()
 *
 * MyScreen is now ready. Next we'll set up MyGame to make use of MyScreen
 */
class MyScreen : Screen {

    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var viewport: Viewport

    override fun show() {
        batch = SpriteBatch()
        font = BitmapFont()
        font.data.setScale(2f)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        viewport = ScreenViewport()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        batch.dispose()
        font.dispose()
    }

    override fun render(delta: Float) {
        viewport.apply()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.projectionMatrix = viewport.camera.combined
        batch.begin()

        font.draw(batch, "Hello from " + MyScreen::class.java.simpleName,
                viewport.worldWidth / 2,
                viewport.worldHeight / 2,
                0f,
                Align.center,
                false)

        batch.end()
    }

    override fun pause() {
        // do nothing
    }

    override fun resume() {
        // do nothing
    }

    override fun dispose() {
        // do nothing
    }
}
