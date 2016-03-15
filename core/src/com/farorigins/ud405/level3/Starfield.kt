package com.farorigins.ud405.level3

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

import java.util.Random

/**
 * In this exercise we'll draw a star field of white points on a black background. The number of
 * points will be defined by a density parameter that states what proportion of the pixels should be
 * white.
 *
 * Run what you've got before making any changes
 *
 * One thing to note is we're using two new LibGDX classes, Array, and Vector2. We're using a custom
 * Array type so LibGDX can control the memory, and avoid unfortunate garbage collection events.
 * Vector2 is a super simple class for holding a 2D position. You can find more information in the
 * LibGDX Javadocs, or just by right clicking on the class name, and selecting Go To > Declaration.
 *
 * One new utility class we'll be using in this exercise is com.badlogic.gdx.math.Vector2. You can
 * find more information in the LibGDX Javadocs.
 *
 * Remember you can set up a Desktop run configuration using the dropdown in the toolbar, or you can
 * open the terminal at the bottom of the screen and run
 *
 * $ ./gradlew desktop:run
 */
class Starfield : ApplicationAdapter() {

    companion object {
        private val STAR_DENSITY = 0.01f
    }

    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var stars: Array<Vector2>

    override fun create() {
        //  Initialize a shapeRenderer
        shapeRenderer = ShapeRenderer()
        // Call initStars
        initStars(STAR_DENSITY)
    }

    fun initStars(density: Float) {
        // Figure out how many stars to draw. You'll need the screen dimensions, which you can get
        // using Gdx.graphics.getWidth() and Gdx.graphics.getHeight().
        val starCount = Math.round(Gdx.graphics.width * Gdx.graphics.height * density)

        // Create a new array of Vector2's to hold the star positions
        stars = Array<Vector2>(starCount)
        // Use java.util.Random to fill the array of star positions
        val random = Random()
        for (i in 0..starCount - 1) {
            val star = Vector2(random.nextInt(Gdx.graphics.width).toFloat(), random.nextInt(Gdx.graphics.height).toFloat())
            stars.add(star)
        }
    }

    override fun render() {
        // Make the night sky black
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Begin a shapeRenderer batch using ShapeType.Point
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point)
        // Loop through the star positions and use shapeRenderer to draw points
        for (v in stars) {
            shapeRenderer.point(v.x, v.y, 0f)
        }
        //  End the shapeRenderer batch
        shapeRenderer.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }
}