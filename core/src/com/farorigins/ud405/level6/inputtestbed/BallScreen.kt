package com.farorigins.ud405.level6.inputtestbed

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport

/**
 *
 * This demo uses a ScreenAdapter to separate the particular bouncing ball content from the
 * ApplicationListener. A ScreenAdapter is a convenience implementation of Screen which provides
 * blank implementations of all the required methods. That way we only need to override what we
 * need.
 *
 * The BallScreen is responsible for setting up the drawing environment for the BouncingBall object,
 * which contains all the logic related to how the ball moves.
 *
 * One new concept is AutoShapeType on ShapeRenderer. When we're delegating drawing to other
 * objects, it's nice to allow those objects to set the ShapeType. With AutoShapeType enabled,
 * ShapeRenderer will automatically end its current batch and start a new one when asked to switch
 * shape types.
 *
 * After reviewing this file, check out the BouncingBall class
 */
class BallScreen : ScreenAdapter() {

    companion object {
        val WORLD_SIZE = 480f
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: ExtendViewport
    private lateinit var ball: BouncingBall

    override fun show() {
        renderer = ShapeRenderer()
        // We use AutoShapeType so we can allow other objects to use our renderer safely
        renderer.setAutoShapeType(true)
        // We use an extend viewport to make the shortest axis of the world equal to WORLD_SIZE
        viewport = ExtendViewport(WORLD_SIZE, WORLD_SIZE)
        ball = BouncingBall(viewport)
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
