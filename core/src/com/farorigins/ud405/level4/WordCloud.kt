package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


/**
 * In this exercise, we'll create a word cloud. We've created all the infrastructure like the
 * SpriteBatch and BitmapFont. Now all you need to do is actually draw the random collection of
 * words we've generated.
 *
 * You can find the Word class at the bottom of this file. It contains 5 fields:
 *
 * x, y - Normalized position (meaning in the range 0-1), you'll want to multiply by
 * Gdx.graphics.getWidth() and Gdx.graphics.getHeight() as appropriate.
 * scale - The size of the text.
 * color - The color of the word.
 * letters - The actual letters in the world.
 *
 * Jump to the TODOs below to see what you'll need to do.
 */
class WordCloud : ApplicationAdapter() {

    companion object {
        private var MIN_SCALE = 0.5f
        private var MAX_SCALE = 5.0f

        private var WORD_COUNT = 20
    }

    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont

    private lateinit var words: Array<Word>

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        font.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        words = generateWords(WORD_COUNT)
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()

        for (word in words) {

            // Set the font's scale using font.getData().setScale()
            font.data.setScale(word.scale)

            // Set the font's tint using font.setColor()
            font.color = word.color

            // Actually draw the word using font.draw()
            font.draw(batch, word.letters, word.x * Gdx.graphics.width, word.y * Gdx.graphics.height)
        }
        batch.end();
    }

    private fun generateWords(count: Int): Array<Word> {
        val newWords = Array<Word>(count)
        for (i in 0 .. count) {
            newWords.add(Word.randomWord(MIN_SCALE, MAX_SCALE))
        }
        return newWords;
    }
}

internal class Word(val x: Float, val y: Float, val scale: Float, val color: Color, val letters: String) {

    companion object {
        val words = Array.with("render-farm", "refrigerator", "tiger-team", "weathered", "camera", "tattoo",
                "boat", "soul-delay", "nodal point", "motion augmented", "reality neon", "nano-construct", "garage",
                "bicycle", "rebar tanto", "modem", "concrete RAF", "industrial grade media", "realism", "drone",
                "post-franchise shoes", "render-farm-ware", "DIY San Francisco", "rain lights", "numinous tank-traps",
                "pen drone", "cyber-cardboard", "denim monofilament", "order-flow", "smart-hotdog")

        fun randomWord(minScale: Float, maxScale: Float): Word {
            val x = MathUtils.random(-.25f, .75f)
            val y = MathUtils.random()
            val scale = minScale + (maxScale - minScale) * MathUtils.random()
            val color = Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f)
            val letters = words[MathUtils.random(words.size - 1)]
            return Word(x, y, scale, color, letters)
        }
    }
}
