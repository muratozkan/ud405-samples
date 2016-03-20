package com.farorigins.ud405.level8.adddifficultyscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport

import com.farorigins.ud405.level8.IciclesGame

class DifficultyScreen(val game: IciclesGame) : InputAdapter(), Screen {

    companion object {
        val TAG = DifficultyScreen::class.java.name
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var batch: SpriteBatch
    private lateinit var viewport: FitViewport

    private lateinit var font: BitmapFont

    override fun show() {
        renderer = ShapeRenderer()
        batch = SpriteBatch()

        // Initialize a FitViewport with the difficulty world size constant
        viewport = FitViewport(Constants.DIFFICULTY_WORLD_SIZE, Constants.DIFFICULTY_WORLD_SIZE)
        Gdx.input.inputProcessor = this

        font = BitmapFont()
        // Set the font scale using the constant we defined
        font.data.scale(Constants.DIFFICULTY_LABEL_SCALE)

        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }

    override fun render(delta: Float) {
        // Apply the viewport
        viewport.apply()

        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Set the ShapeRenderer's projection matrix
        renderer.projectionMatrix = viewport.camera.combined

        // Use ShapeRenderer to draw the buttons
        renderer.begin(ShapeRenderer.ShapeType.Filled)

        renderer.color = Constants.EASY_COLOR
        renderer.circle(Constants.EASY_CENTER.x, Constants.EASY_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS)

        renderer.color = Constants.MEDIUM_COLOR
        renderer.circle(Constants.MEDIUM_CENTER.x, Constants.MEDIUM_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS)

        renderer.color = Constants.HARD_COLOR
        renderer.circle(Constants.HARD_CENTER.x, Constants.HARD_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS)

        renderer.end()

        // Set the SpriteBatch's projection matrix
        batch.projectionMatrix = viewport.camera.combined

        // Use SpriteBatch to draw the labels on the buttons
        // HINT: Use GlyphLayout to get vertical centering
        batch.begin()

        val easyLayout = GlyphLayout(font, Difficulty.EASY.label)
        font.draw(batch, Difficulty.EASY.label, Constants.EASY_CENTER.x, Constants.EASY_CENTER.y + easyLayout.height / 2, 0f, Align.center, false)

        val mediumLayout = GlyphLayout(font, Difficulty.MEDIUM.label)
        font.draw(batch, Difficulty.MEDIUM.label, Constants.MEDIUM_CENTER.x, Constants.MEDIUM_CENTER.y + mediumLayout.height / 2, 0f, Align.center, false)

        val hardLayout = GlyphLayout(font, Difficulty.HARD.label)
        font.draw(batch, Difficulty.HARD.label, Constants.HARD_CENTER.x, Constants.HARD_CENTER.y + hardLayout.height / 2, 0f, Align.center, false)

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        // Update the viewport
        viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        batch.dispose()
        font.dispose()
        renderer.dispose()
    }

    override fun dispose() {

    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //  Unproject the touch from the screen to the world
        val worldTouch = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))

        // Check if the touch was inside a button, and launch the icicles screen with the appropriate difficulty
        if (worldTouch.dst(Constants.EASY_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Difficulty.EASY)
        }

        if (worldTouch.dst(Constants.MEDIUM_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Difficulty.MEDIUM)
        }

        if (worldTouch.dst(Constants.HARD_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Difficulty.HARD)
        }

        return true
    }
}
