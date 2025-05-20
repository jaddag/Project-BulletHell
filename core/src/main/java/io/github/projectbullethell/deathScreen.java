package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;

import MainMethod.bulletHellMain;

public class deathScreen implements Screen {

    private final bulletHellMain game;
    private float timer = 0f;
    Music music;

    public deathScreen(bulletHellMain game, Music music) {
        this.game = game;
        this.music = music;

        this.music.stop();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        timer += delta;

        Gdx.gl.glClearColor(1, 0, 0, 1); // red background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (timer >= 3f) {
            game.setScreen(new startScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
