package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import MainMethod.bulletHellMain;

public class winScreen implements Screen {

    private final bulletHellMain game;
    private float timer = 0f;
    Music music;

    BitmapFont font;

    SpriteBatch textBatch;

    GlyphLayout layout;

    int screenW;
    int screenH;

    private OrthographicCamera camera;
    private Viewport viewport;

    public winScreen(bulletHellMain game, Music music) {
        screenW = Gdx.graphics.getBackBufferWidth();
        screenH = Gdx.graphics.getBackBufferHeight();


        this.game = game;
        this.music = music;

        this.music.stop();

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenW, screenH, camera);
        viewport.apply();
        camera.position.set(screenW / 2f, screenH / 2f, 0);
        camera.update();

        textBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        font.getData().setScale(20f);
        layout = new GlyphLayout();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timer += delta;
        textBatch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (timer >= 3f) {
            game.setScreen(new startScreen(game));
        }

        showText();
    }

    public void showText(){
        layout.setText(font, "YOU WIN");
        float x = (screenW - layout.width) / 2f;
        float y = (screenH + layout.height) / 2f;

        textBatch.begin();
        font.draw(textBatch, layout, x, y);
        textBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
