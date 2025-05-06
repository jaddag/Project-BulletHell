package io.github.projectbullethell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import MainMethod.BulletHellMain;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class loadingScreen extends ApplicationAdapter implements Screen{
    private SpriteBatch batch;
    private Texture image;
    private float alpha;
    private BitmapFont font;

    private float elapsed = 0f;
    private BulletHellMain game;


    @Override
    public void create() {

        alpha = 0f;
        font = new BitmapFont();
        batch = new SpriteBatch();
        image = new Texture("Icon/BulletHellIconStartScreen.png");
    }

    @Override
    public void render() {
//        ScreenUtils.clear(0f, 0f, 0f, 0f);
//        batch.begin();
//        float Percent = (Gdx.graphics.getWidth() * 0.10f);
//        float xWidth = (Gdx.graphics.getWidth() - Percent) / 2f;
//        float yWidth = (Gdx.graphics.getHeight() - Percent) / 2f;
//        if (alpha < 1f) alpha += 0.010f;
//        batch.setColor(1,1,1, Math.min(alpha, 1f));
//        batch.draw(image, xWidth, yWidth, Percent, Percent);
//        font.draw(batch ,"" + Gdx.graphics.getFramesPerSecond(), 600, 500);
//        font.getData().setScale(10f);
//
//        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += delta;

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        batch.begin();

        float Percent = (Gdx.graphics.getWidth() * 0.10f);
        float xWidth = (Gdx.graphics.getWidth() - Percent) / 2f;
        float yWidth = (Gdx.graphics.getHeight() - Percent) / 2f;

        if (alpha < 1f) alpha += 0.010f;
        batch.setColor(1, 1, 1, Math.min(alpha, 1f));
        batch.draw(image, xWidth, yWidth, Percent, Percent);
        font.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), 600, 500);
        font.getData().setScale(10f);
        batch.end();

        // render your loading visuals here

        if (elapsed > 3f) { // after 3 seconds
//            game.setScreen(new startScreen(game));
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
