package com.farorigins.ud405.level5

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

class FPSCounterGame : Game() {

    override fun create() {
        setScreen(FPSCounterScreen())
    }
}

class FPSCounterScreen : Screen {

    companion object {
        private val FONT_SCALE = 3.0f
    }

    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var viewport: Viewport

    override fun show() {
        batch = SpriteBatch()
        font = BitmapFont()
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        viewport = ScreenViewport()
        font.data.setScale(FONT_SCALE)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()

        batch.projectionMatrix = viewport.camera.combined

        val fps = 1f / delta

        batch.begin()
        font.draw(batch, "FPS = $fps", Gdx.graphics.width / 4f, Gdx.graphics.height / 2f)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        batch.dispose()
        font.dispose()
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
