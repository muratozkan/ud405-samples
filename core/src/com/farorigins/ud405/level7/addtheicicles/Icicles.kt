package com.farorigins.ud405.level7.addtheicicles

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.badlogic.gdx.utils.Array

// Add an array of icicles and a viewport
class Icicles(private var viewport: Viewport) {

    companion object {
        val TAG = Icicles::class.java.name
    }

    init {
        init()
    }

    private lateinit var icicles: Array<Icicle>

    fun init() {
        // Initialize the array of icicles
        icicles = Array<Icicle>(false, 100)
    }

    fun update(delta: Float) {
        // Replace hard-coded spawn rate with a constant
        if (MathUtils.random() < delta * Constants.ICICLE_SPAWNS_PER_SECOND) {
            // Add a new icicle at the top of the viewport at a random x position
            val icicle = Icicle(Vector2(MathUtils.random(viewport.worldWidth), viewport.worldHeight))
            icicles.add(icicle)
        }

        // Update each icicle
        icicles.forEach { ic -> ic.update(delta) }
    }

    fun render(renderer: ShapeRenderer) {
        // Set ShapeRenderer Color
        // Render each icicle
        renderer.set(ShapeRenderer.ShapeType.Filled)
        icicles.forEach { ic -> ic.render(renderer) }
    }
}
