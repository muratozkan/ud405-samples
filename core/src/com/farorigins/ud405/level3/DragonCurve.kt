package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import java.util.*

/*
* The Dragon Curve is a fractal made by a single line. It is formed of a series of turns, which can be constructed
* in the following way:
*
* 0: L
* 1: L + L + R
* 2: LLR + L + LRR
* 3: LLRLLRR + L + LLRRLRR
*
* The nth dragon curve is the n-1th dragon curve plus L, plus the n-1th dragon curve reversed and reflected.
*
* In this project we have split up the tasks of generating and drawing the dragon curve into separate classes.
**/
class DragonCurve : ApplicationAdapter() {

    private lateinit var dragonCurve: FloatArray
    private lateinit var shapeRenderer: ShapeRenderer

    override fun create() {
        dragonCurve = CurveGenerator.generateDragonCurve(Gdx.graphics.width, Gdx.graphics.height, RECURSIONS);
        shapeRenderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.polyline(dragonCurve)
        shapeRenderer.end()
    }

    companion object {
        // Any more than 10 and we'll need to break up the polyline into multiple lines
        private val RECURSIONS = 10
    }

    object CurveGenerator {
        enum class Direction {
            LEFT,
            RIGHT;

            fun inverse(): Direction = when (this) {
                LEFT -> RIGHT
                RIGHT -> LEFT
            }
        }

        fun turn(heading: Vector2, turn: Direction): Vector2 {
            val newHeading = Vector2()
            when (turn) {
                Direction.LEFT -> {
                    newHeading.x = -heading.y
                    newHeading.y = heading.x
                }
                Direction.RIGHT -> {
                    newHeading.x = heading.y
                    newHeading.y = -heading.x
                }
            }
            return newHeading
        }

        fun dragonTurns(recursions: Int): List<Direction> {
            val turns = ArrayList<Direction>()
            turns.add(Direction.LEFT)

            for (i in 0..recursions - 1) {
                // Create a reversed copy of turns
                var reversed = turns.map { d -> d.inverse() }.toList().reversed()

                //  Add a left turn to turns
                turns.add(Direction.LEFT)

                // Add reflected version of reversed to turns
                turns.addAll(reversed)
            }
            return turns
        }

        fun generateDragonCurve(width: Int, height: Int, recursions: Int): FloatArray {
            val turns = dragonTurns(recursions)
            val head = Vector2((width / 2).toFloat(), (height / 2).toFloat())

            var heading = Vector2(5f, 0f)

            val curve = FloatArray((turns.size + 1) * 2)

            curve[0] = head.x
            curve[1] = head.y

            // Convert the list of turns into the actual path
            var i = 2
            for (d in turns) {
                heading = turn(heading, d)
                head.add(heading)
                curve[i] = head.x
                curve[i + 1] = head.y
                i += 2
            }

            return curve
        }
    }
}