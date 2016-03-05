package com.farorigins.ud405.level5.fallingobjects

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.*

class Avalanche {

    companion object {
        const val SPAWNS_PER_SECOND = 10f
    }

    var boulders = Array<Boulder>()

    fun update(delta: Float, viewport: Viewport) {
        val random = Random()
        if (random.nextFloat() < delta * SPAWNS_PER_SECOND) {
            boulders.add(Boulder(viewport))
        }

        val fallingBoulders = Array<Boulder>()
        for (b in boulders) {
            b.update(delta)
            if (!b.isBelowScreen()) {
                fallingBoulders.add(b)
            }
        }

        boulders = fallingBoulders
    }

    fun render(renderer: ShapeRenderer) {
        for (boulder in boulders) {
            boulder.render(renderer)
        }
    }
}
