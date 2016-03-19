package com.farorigins.ud405.level8.adddifficultyscreen

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

// Accept a Difficulty in the constructor
// Set Difficulty
// Add Difficulty
class IciclesScreen(val difficulty: Difficulty) : Screen {

    companion object {
        val TAG = IciclesScreen::class.java.name
    }

    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private lateinit var icicles: Icicles
    private lateinit var player: Player

    private lateinit var hudViewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont

    private var topScore = 0


    override fun show() {
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)

        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        hudViewport = ScreenViewport()
        batch = SpriteBatch()

        font = BitmapFont()
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        player = Player(viewport)

        // Initialize icicles with the difficulty
        icicles = Icicles(viewport, difficulty)

        topScore = 0
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)

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

        topScore = Math.max(topScore, icicles.iciclesDodged)

        hudViewport.apply()
        batch.projectionMatrix = hudViewport.camera.combined

        batch.begin()
        // Show Difficulty level in the top left
        font.draw(batch, "Deaths: ${player.deaths}\nDifficulty: ${difficulty.label}",
                Constants.HUD_MARGIN, hudViewport.worldHeight - Constants.HUD_MARGIN)
        font.draw(batch, "Score: ${icicles.iciclesDodged}\nTop Score: $topScore",
                hudViewport.worldWidth - Constants.HUD_MARGIN, hudViewport.worldHeight - Constants.HUD_MARGIN,
                0f, Align.right, false)
        batch.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        renderer.dispose()
        batch.dispose()
    }
}
