package com.farorigins.ud405.level5.screensaver

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

class ScreenSaver : Game() {
    override fun create() {
        setScreen(BallScreen())
    }
}

class BallScreen : Screen, InputAdapter() {

    companion object {
        const val WORLD_SIZE = 480.0f
        // When a single ball is working try a bunch of balls.
        // See how many balls you can add before your computer starts slowing down.
        const val BALL_COUNT = 1
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: Viewport

    private val balls = Array<BouncingBall>()

    override fun show() {
        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        viewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE)

        for (i in 1..BALL_COUNT) {
            balls.add(BouncingBall(viewport))
        }
        Gdx.input.inputProcessor = this
    }

    private fun initBalls() {
        for (b in balls) {
            b.init(viewport)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        initBalls()
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()
        renderer.projectionMatrix = viewport.camera.combined

        renderer.begin(ShapeRenderer.ShapeType.Filled)
        for (b in balls) {
            b.update(delta, viewport)
            b.render(renderer)
        }
        renderer.end()
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.SPACE) {
            initBalls()
        }
        return false
    }

    override fun pause() {
        // do nothing
    }

    override fun hide() {
        // do nothing
    }

    override fun resume() {
        // do nothing
    }
}