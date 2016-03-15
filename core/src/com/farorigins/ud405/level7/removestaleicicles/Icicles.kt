package com.farorigins.ud405.level7.removestaleicicles

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray

class Icicles(private var viewport: Viewport) {

    companion object {
        val TAG = Icicles::class.java.name
    }

    init {
        init()
    }

    // Use a DelayedRemovalArray to hold our icicles
    private lateinit var icicles: DelayedRemovalArray<Icicle>

    fun init() {
        // Initialize the DelayedRemovalArray
        icicles = DelayedRemovalArray<Icicle>(false, 100)
    }

    fun update(delta: Float) {
        if (MathUtils.random() < delta * Constants.ICICLE_SPAWNS_PER_SECOND) {
            val icicle = Icicle(Vector2(MathUtils.random(viewport.worldWidth), viewport.worldHeight))
            icicles.add(icicle)
        }

        icicles.forEach { ic -> ic.update(delta) }

        // begin a removal session
        icicles.begin()

        // Remove any icicle completely off the bottom of the screen
        icicles.forEachIndexed { i, icicle ->
            if (icicle.position.y < -Constants.ICICLES_HEIGHT)
                icicles.removeIndex(i)
        }

        // End removal session
        icicles.end()
    }

    fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        icicles.forEach { ic -> ic.render(renderer) }
    }
}
