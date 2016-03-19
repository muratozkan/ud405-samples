package com.farorigins.ud405.level8.adddifficultyscreen

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.badlogic.gdx.utils.DelayedRemovalArray

// Accept a difficulty in the constructor
// Set difficulty
// Add a Difficulty
class Icicles(private var viewport: Viewport, val difficulty: Difficulty) {

    companion object {
        val TAG = Icicles::class.java.name
    }

    init {
        init()
    }

    var iciclesDodged = 0; private set

    lateinit var icicles: DelayedRemovalArray<Icicle>
        private set

    fun init() {
        icicles = DelayedRemovalArray<Icicle>(false, 100)
        iciclesDodged = 0
    }

    fun update(delta: Float) {
        // Use the difficulty's spawn rate
        if (MathUtils.random() < delta * difficulty.spawnRate) {
            val icicle = Icicle(Vector2(MathUtils.random(viewport.worldWidth), viewport.worldHeight))
            icicles.add(icicle)
        }

        icicles.forEach { ic -> ic.update(delta) }

        icicles.begin()
        icicles.forEachIndexed { i, icicle ->
            if (icicle.position.y < -Constants.ICICLES_HEIGHT) {
                iciclesDodged++
                icicles.removeIndex(i)
            }
        }
        icicles.end()
    }

    fun render(renderer: ShapeRenderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled)
        icicles.forEach { ic -> ic.render(renderer) }
    }
}
