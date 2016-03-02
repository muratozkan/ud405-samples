package com.farorigins.ud405.level4

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class OrthographicProjection : ApplicationAdapter() {

    private lateinit var renderer: ShapeRenderer
    private lateinit var demoCamera: DemoCamera

    override fun create() {
        renderer = ShapeRenderer()
        demoCamera = DemoCamera()
        // Tell LibGDX that demoCamera knows what to do with keypresses
        Gdx.input.inputProcessor = demoCamera;
    }

    override fun resize(width: Int, height: Int) {
        demoCamera.resize(width.toFloat(), height.toFloat());
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        demoCamera.update();
        demoCamera.setCamera(renderer);
        renderTestScene(renderer);
        demoCamera.render(renderer);
    }

    /**
     * This method renders a few shapes for us to try our camera on. Note that we're using a Bezier
     * curve, which is a way to draw smooth curves. For more information on Bezier curves, check
     * out: https://en.wikipedia.org/wiki/B%C3%A9zier_curve
     *
     * Also note that a line is a line is a line. No matter how much we zoom in, a line is always
     * just one pixel wide.
     */
    private fun renderTestScene(renderer: ShapeRenderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.GREEN
        renderer.circle(100f, 100f, 90f)
        renderer.color = Color.RED
        renderer.rect(200f, 10f, 200f, 200f)
        renderer.color = Color.YELLOW
        renderer.triangle(10f, 200f, 200f, 200f, 100f, 400f)
        renderer.end()

        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.CYAN

        // Here's another shape ShapeRenderer
        renderer.curve(
                210f, 210f,
                400f, 210f,
                210f, 400f,
                400f, 300f,
                20)
        renderer.end()
    }
}