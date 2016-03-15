package com.farorigins.ud405.level7.arrowkeycontrols

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

class IciclesScreen : Screen {

    companion object {
        val TAG = IciclesScreen::class.java.name
    }

    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private lateinit var icicle: Icicle
    private lateinit var player: Player

    override fun show() {
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)

        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        player = Player(viewport)
        icicle = Icicle(Vector2(Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2))
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        player.init()
    }

    override fun dispose() {

    }

    override fun render(delta: Float) {
        // Call update() on player
        player.update(delta)

        viewport.apply()
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1f)
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.projectionMatrix = viewport.camera.combined
        renderer.begin()

        icicle.render(renderer)
        player.render(renderer)

        renderer.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        renderer.dispose()
    }
}
