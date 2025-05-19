package io.github.projectbullethell;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;

import io.github.projectbullethell.startscreenassets.startText;
import GenTexture.generateTexture;

import MainMethod.bulletHellMain;

public class startScreen implements Screen {

    private ShapeRenderer shapeRenderer;

    SpriteBatch spriteBatch;

    bulletHellMain game;

    float screenW;
    float screenH;

    FitViewport viewport;
    OrthographicCamera camera;

    float scrollY, scrollY1, scrollY2, scrollSkyTexture;
    float speed;
    float speed1;
    float speed2;
    float speedSky;
    float deltaTime;

    Texture backgroundTexture1;
    Texture backgroundTexture2;
    Texture backgroundTexture3;
    Texture starBackground;
    Texture alphaBackground;

    generateTexture generateTexture;
    Sprite text;
    Texture textTexture;
    startText startText;

    Color color;
    float spriteW;
    float spriteH;
    public startScreen(bulletHellMain game){
        this.game = game;
    }

    @Override
    public void show() {
        speed = 10f;
        speed1 = 20f;
        speed2 = 30f;
        speedSky = 5f;

        screenH = Gdx.graphics.getHeight();
        screenW = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenW, screenH);

        viewport = new FitViewport(screenW, screenH, camera);

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        getBG();

        color = new Color(Color.BLUE);
        startText = new startText(color.r, color.g, color.b);

        generateTexture = new generateTexture();
        textTexture = generateTexture.genTexture(28, 7, 10, startText.getPixels());

        text = new Sprite(textTexture);
        text.setOriginCenter();

        spriteW = text.getWidth();
        spriteH = text.getHeight();

        text.setPosition(screenW / 2f - text.getWidth() / 2f, screenH / 2f - text.getHeight() / 2f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(
            Gdx.graphics.getWidth() / 2f - spriteW,
            Gdx.graphics.getHeight() / 2f - spriteH,
            spriteW,
            spriteH
        );
        shapeRenderer.end();

        background();
        drawText();
        inputButton();
    }

    public void drawText(){
        spriteBatch.begin();

        text.draw(spriteBatch);

        spriteBatch.end();
    }

    public void background(){
        updateBGPos(10f);
        backgroundDraw();
    }

    public void getBG(){
        AssetManager assetManager = game.assetManager;

        assetManager.load("cache/background1.png", Texture.class);
        assetManager.load("cache/background2.png", Texture.class);
        assetManager.load("cache/background3.png", Texture.class);
        assetManager.load("cache/starBackground.png", Texture.class);
        assetManager.load("cache/alphaBG.png", Texture.class);
        assetManager.finishLoading();

        backgroundTexture1 = assetManager.get("cache/background1.png", Texture.class);
        backgroundTexture2 = assetManager.get("cache/background2.png", Texture.class);
        backgroundTexture3 = assetManager.get("cache/background3.png", Texture.class);
        starBackground = assetManager.get("cache/starBackground.png", Texture.class);
        alphaBackground = assetManager.get("cache/alphaBG.png", Texture.class);
    }

    private void backgroundDraw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

//        spriteBatch.draw(starBackground, bgAdjust.getInfiniteBGCordsR(), 0, screenW, screenH);
//        spriteBatch.draw(flippedStarBackground, bgAdjust.getInfiniteBGCordsL(), 0, screenW, screenH);

        spriteBatch.draw(starBackground, 0, - scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(starBackground, 0, screenH - scrollSkyTexture, screenW, screenH);


        spriteBatch.draw(alphaBackground,0,0, screenW, screenH);

        spriteBatch.draw(backgroundTexture1, 0, -scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, 0, screenH-scrollY, screenW, screenH);

        spriteBatch.draw(backgroundTexture2, 0, -scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, 0, screenH-scrollY1, screenW, screenH);

        spriteBatch.draw(backgroundTexture3, 0, -scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, 0, screenH-scrollY2, screenW, screenH);

        spriteBatch.end();
    }

    private void updateBGPos(float speedModifier) {
        deltaTime = Gdx.graphics.getDeltaTime();
        scrollY += speed * deltaTime;
        if(scrollY >= screenH){
            scrollY = 0;
        }

        scrollY1 += speed1 * deltaTime;
        if(scrollY1 >= screenH){
            scrollY1 = 0;
        }

        scrollY2 += speed2 * deltaTime;
        if(scrollY2 >= screenH){
            scrollY2 = 0;
        }

        scrollSkyTexture += speedSky * deltaTime;
        if(scrollSkyTexture >= screenH){
            scrollSkyTexture = 0;
        }
    }

    public void inputButton(){
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y

            float rectX = Gdx.graphics.getWidth() / 2f - 100;
            float rectY = Gdx.graphics.getHeight() / 2f - 25;
            float rectWidth = 200;
            float rectHeight = 50;

            if (touchX >= rectX && touchX <= rectX + rectWidth &&
                touchY >= rectY && touchY <= rectY + rectHeight) {
                game.setScreen(new gameScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        if (backgroundTexture1 != null) backgroundTexture1.dispose();
        if (backgroundTexture2 != null) backgroundTexture2.dispose();
        if (backgroundTexture3 != null) backgroundTexture3.dispose();
        if (starBackground != null) starBackground.dispose();
        if (alphaBackground != null) alphaBackground.dispose();
    }
}
