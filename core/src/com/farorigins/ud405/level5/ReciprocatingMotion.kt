package com.farorigins.ud405.level5

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * In this exercise we'll make a circle move back and forth smoothly. We'll pick a period and and
 * amplitude, the set the circle x position to the center of the screen plus the amplitude times the
 * sin of 2Pi the elapsed time divided by the period.
 */
class ReciprocatingMotion : ApplicationAdapter() {

    companion object {
        private val WORLD_SIZE = 480f
        private val CIRCLE_RADIUS = WORLD_SIZE / 20
        private val MOVEMENT_DISTANCE = WORLD_SIZE / 4

        // Define a constant that fixes how long a cycle of the animation should take in seconds
        private val PERIOD = 5f
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: Viewport

    // Create a long to hold onto ApplicationAdapter creation time
    private var initialTime = 0L

    override fun create() {
        renderer = ShapeRenderer()
        viewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE)

        // Save current value of TimeUtils.nanoTime()
        initialTime = TimeUtils.nanoTime()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun render() {
        viewport.apply()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = viewport.camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)

        // Since we're using an extend viewport, the world might be bigger than we expect
        val worldCenterX = viewport.worldWidth / 2
        val worldCenterY = viewport.worldHeight / 2

        // Figure out how long it's been since the animation started using TimeUtils.nanoTime()
        val runningFor = TimeUtils.timeSinceNanos(initialTime)

        // Use MathUtils.nanoToSec to figure out how many seconds the animation has been running
        val runningForSec = MathUtils.nanoToSec * runningFor

        // Figure out how many cycles have elapsed since the animation started running
        val cycleCount = MathUtils.floor(runningForSec / PERIOD)

        // Figure out where in the cycle we are
        val cyclePos = runningForSec / PERIOD - cycleCount

        // Use MathUtils.sin() to set the x position of the circle
        val x = worldCenterX + MOVEMENT_DISTANCE * MathUtils.sin(MathUtils.PI2 * cyclePos)
        val y = worldCenterY
        renderer.circle(x, y, CIRCLE_RADIUS)
        renderer.end()

    }
}
