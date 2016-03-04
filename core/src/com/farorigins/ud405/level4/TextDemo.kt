package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Let's do the minimal text example! Note that we use a SpriteBatch to draw, instead of a
 * ShapeRenderer.
 *
 * One more concept we haven't yet covered is filtering. That's how we determine what to do when we
 * draw a bitmap at a larger size than it was intended. Our options are Linear, and Nearest. Nearest
 * is what you want for pixel art, where you want things to maintain their sharp individual pixels.
 * For everything else, you want the Linear filter, as that will smooth between adjacent pixels.
 */
class TextDemo : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        // Create the default font
        font = BitmapFont()
        // Scale it up
        font.data.setScale(3f);
        // Set the filter
        font.region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    /**
     * Remember to dispose of SpriteBatches and fonts!
     */
    override fun dispose() {
        batch.dispose()
        font.dispose()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // We begin batches just like with a ShapeRenderer, though there's no mode
        batch.begin()
        font.draw(batch, "Text", 100f, 100f)
        // Remember to end the batch
        batch.end()
    }
}
