package com.farorigins.ud405.level8.addthehud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

class IciclesScreen : Screen {

    companion object {
        val TAG = IciclesScreen::class.java.name
    }

    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private lateinit var icicles: Icicles
    private lateinit var player: Player

    // Add ScreenViewport for HUD
    private lateinit var hudViewport: Viewport

    // Add SpriteBatch
    private lateinit var batch: SpriteBatch

    // Add BitmapFont
    private lateinit var font: BitmapFont

    // Add int to hold the top score
    private var topScore = 0

    override fun show() {
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)

        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        // Initialize the HUD viewport
        hudViewport = ScreenViewport()

        // Initialize the SpriteBatch
        batch = SpriteBatch()

        // Initialize the BitmapFont
        font = BitmapFont()

        // Give the font a linear TextureFilter
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        player = Player(viewport)
        icicles = Icicles(viewport)

        // Set top score to zero
        topScore = 0
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)

        // Update HUD viewport
        hudViewport.update(width, height, true)

        // Set font scale to min(width, height) / reference screen size
        font.data.setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE)

        player.init()
        icicles.init()
    }

    override fun dispose() {

    }

    override fun render(delta: Float) {
        icicles.update(delta)
        player.update(delta)

        if (player.hitByIcicle(icicles)) {
            icicles.init()
        }

        viewport.apply()
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = viewport.camera.combined
        renderer.begin()

        icicles.render(renderer)
        player.render(renderer)

        renderer.end()

        // Set the top score to max(topScore, iciclesDodges)
        topScore = Math.max(topScore, icicles.iciclesDodged)

        // Apply the HUD viewport
        hudViewport.apply()

        // Set the SpriteBatch's projection matrix
        batch.projectionMatrix = hudViewport.camera.combined

        // Begin the SpriteBatch
        batch.begin()

        // Draw the number of player deaths in the top left
        font.draw(batch, "Deaths: " + player.deaths,
                Constants.HUD_MARGIN, hudViewport.worldHeight - Constants.HUD_MARGIN)

        // Draw the score and top score in the top right
        font.draw(batch, "Score: " + icicles.iciclesDodged + "\nTop Score: " + topScore,
                hudViewport.worldWidth - Constants.HUD_MARGIN, hudViewport.worldHeight - Constants.HUD_MARGIN,
                0f, Align.right, false)

        // End the SpriteBatch
        batch.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        renderer.dispose()
        // Dispose of the SpriteBatch
        batch.dispose()
    }
}
