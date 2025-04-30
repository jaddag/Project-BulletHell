package io.github.projectbullethell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class loadingScreen extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    private BitmapFont font;

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        image = new Texture("BulletHellIconStartScreen.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        batch.begin();
        float Percent = (Gdx.graphics.getWidth() * 0.10f);
        float xWidth = (Gdx.graphics.getWidth() - Percent) / 2f;
        float yWidth = (Gdx.graphics.getHeight() - Percent) / 2f;
        batch.draw(image, xWidth, yWidth, Percent, Percent);
//        font.draw(batch ,"Width: " + Gdx.graphics.getWidth() + " Hight: " + Gdx.graphics.getHeight() , 600, 500);
//        font.getData().setScale(10f);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
