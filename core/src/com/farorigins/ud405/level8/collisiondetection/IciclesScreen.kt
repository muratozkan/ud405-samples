package com.farorigins.ud405.level8.collisiondetection

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

    private lateinit var icicles: Icicles
    private lateinit var player: Player

    override fun show() {
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)

        renderer = ShapeRenderer()
        renderer.setAutoShapeType(true)

        player = Player(viewport)
        icicles = Icicles(viewport)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        player.init()
        icicles.init()
    }

    override fun dispose() {

    }

    override fun render(delta: Float) {
        icicles.update(delta)
        player.update(delta)
        // Check if the player was hit by an icicle. If so, reset the icicles.
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
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        renderer.dispose()
    }
}
