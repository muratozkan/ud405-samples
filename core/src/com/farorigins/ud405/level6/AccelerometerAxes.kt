package com.farorigins.ud405.level6

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport

class AccelerometerAxesGame : Game() {

    override fun create() {
        setScreen(AccelerometerAxesScreen())
    }
}

class AccelerometerAxesScreen : ScreenAdapter() {

    companion object {
        val TAG = AccelerometerAxesScreen::class.java.name

        private val WORLD_SIZE = 100.0f
        private val TEXT_SCALE = 5.0f
        private val AXIS_COLOR = Color.RED
        private val AXIS_WIDTH = 1.0f
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var axisViewport: FitViewport

    private lateinit var batch: SpriteBatch
    private lateinit var textViewport: ScreenViewport
    private lateinit var font: BitmapFont

    private var maxAcceleration = 0f
    private var minAcceleration = 0f

    override fun show() {
        axisViewport = FitViewport(WORLD_SIZE, WORLD_SIZE)
        textViewport = ScreenViewport()

        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        batch = SpriteBatch()

        font = BitmapFont()
        font.data.setScale(TEXT_SCALE)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        maxAcceleration = 0f
        minAcceleration = Float.MAX_VALUE
    }

    override fun resize(width: Int, height: Int) {
        axisViewport.update(width, height, true)
        textViewport.update(width, height, true)
    }

    override fun dispose() {
        renderer.dispose()
        batch.dispose()
    }

    override fun render(delta: Float) {
        textViewport.apply()
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // TODO: First we get the raw accelerometer readings
        val xAxis = Gdx.input.accelerometerX
        val yAxis = Gdx.input.accelerometerY
        val zAxis = Gdx.input.accelerometerZ

        // TODO: We can calculate the total acceleration using the Pythagorean theorem
        val totalAcceleration = Math.sqrt(xAxis * xAxis + yAxis * yAxis + zAxis * zAxis + 0.0).toFloat()

        maxAcceleration = Math.max(maxAcceleration, totalAcceleration)
        minAcceleration = Math.min(minAcceleration, totalAcceleration)

        batch.projectionMatrix = textViewport.camera.combined
        batch.begin()

        val message = String.format("Accelerometer reads:\nx = %.2f\ny = %.2f\nz = %.2f\ntotal = %.2f\nmax = %.2f\nmin = %.2f", xAxis, yAxis, zAxis, totalAcceleration, maxAcceleration, minAcceleration)
        font.draw(batch, message, 40f, textViewport.worldHeight - 40)
        batch.end()

        axisViewport.apply()
        renderer.projectionMatrix = axisViewport.camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = AXIS_COLOR

        // TODO: Now we draw the accelerometer readings
        // Horizontal bar, note we have to use the negative yAxis reading, since we're in landscape mode
        renderer.rectLine(
                WORLD_SIZE / 2,
                WORLD_SIZE / 2,
                WORLD_SIZE * (.5f - .25f * yAxis / 9.8f),
                WORLD_SIZE / 2,
                AXIS_WIDTH)

        // Vertical bar, note we have to use the positive xAxis reading, since we're in landscape mode
        renderer.rectLine(
                WORLD_SIZE / 2,
                WORLD_SIZE / 2,
                WORLD_SIZE / 2,
                WORLD_SIZE * (.5f + .25f * xAxis / 9.8f),
                AXIS_WIDTH)

        renderer.end()
    }
}
