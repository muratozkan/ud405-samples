package com.farorigins.ud405.level6.flick

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport

class BallScreen : ScreenAdapter() {

    companion object {
        val WORLD_SIZE = 480f
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: ExtendViewport
    private lateinit var ball: BouncingBall

    override fun show() {
        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)
        viewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE)
        ball = BouncingBall(viewport)
        Gdx.input.inputProcessor = ball
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        ball.init(viewport)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun render(delta: Float) {
        viewport.apply()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = viewport.camera.combined
        ball.update(delta, viewport)

        renderer.begin(ShapeRenderer.ShapeType.Filled)
        ball.render(renderer)
        renderer.end()
    }
}
