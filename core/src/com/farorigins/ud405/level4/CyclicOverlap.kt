package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 *
 * In this project we have three overlapping rectangles. They'd look nice and symmetrical if the
 * left end of the red one was on top of the blue one, but, constrained by the painter's algorithm
 * as we are, that would seem to be impossible. Can you figure out a way to make it happen?
 */
class CyclicOverlap : ApplicationAdapter() {

    companion object {
        val WORLD_SIZE = 10f
    }

    private lateinit var renderer: ShapeRenderer
    private lateinit var viewport: FitViewport

    override fun create() {
        renderer = ShapeRenderer()
        viewport = FitViewport(WORLD_SIZE, WORLD_SIZE)
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        viewport.apply()
        renderer.projectionMatrix = viewport.camera.combined

        renderer.begin(ShapeType.Filled)
        renderer.color = Color.RED
        renderer.rect(2f, 3.5f, 3f, 1.5f, 6f, 1f, 1f, 1f, 0f)
        renderer.color = Color.GREEN
        renderer.rect(2f, 3.5f, 3f, 1.5f, 6f, 1f, 1f, 1f, 120f)
        renderer.color = Color.BLUE
        renderer.rect(2f, 3.5f, 3f, 1.5f, 6f, 1f, 1f, 1f, 240f)

        // Make it look like the left end of RED is on top of BLUE
        renderer.color = Color.RED
        renderer.rect(2f, 3.5f, 3f, 1.5f, 3f, 1f, 1f, 1f, 0f)

        renderer.end()
    }
}
