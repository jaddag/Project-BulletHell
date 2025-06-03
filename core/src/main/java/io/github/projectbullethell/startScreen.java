package io.github.projectbullethell;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
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

import com.badlogic.gdx.audio.Music;

import io.github.projectbullethell.startscreenassets.creditsText;
import io.github.projectbullethell.startscreenassets.settingsText;
import io.github.projectbullethell.startscreenassets.startText;
import GenTexture.generateTexture;

import Player.playerArrayList;

import MainMethod.bulletHellMain;

public class startScreen implements Screen {

    // Delay timer for credits input
    private float creditsDelayTime = 0f;

    private Music music;

    private ShapeRenderer shapeRenderer;

    SpriteBatch spriteBatch;

    bulletHellMain game;

    float screenW;
    float screenH;

    FitViewport viewport;
    OrthographicCamera camera;

    playerArrayList pal;

    boolean anim;
    boolean animStart;
    int timeCount;

    private boolean flyOut = false;
    private float resetTimer = 0f;

    Sprite shipSprite;

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

    generateTexture bg;
    startScreen startScreen;
    Sprite textSettings;
    Texture textTexture;
    startText startText;
    settingsText settingsText;
    creditsText creditText;

    Color color;
    float spriteW;
    float spriteH;

    Texture startTexture;
    Texture settingsTexture;
    Texture creditsTexture;

    float textX;
    float textStartY;

    float textSettingsX;
    float textSettingsY;

    float textCreditsX;
    float textCreditsY;

    boolean menu;
    boolean settings;
    boolean credits;

    float red;
    float green;
    float blue;

    private float animationTime = 0f;

    public startScreen(bulletHellMain game){
        this.game = game;
    }

    @Override
    public void show() {
        speed = 10f;
        speed1 = 20f;
        speed2 = 30f;
        speedSky = 5f;

        screenH = Gdx.graphics.getBackBufferHeight();
        screenW = Gdx.graphics.getBackBufferWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenW, screenH);

        viewport = new FitViewport(screenW, screenH, camera);

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        getBG();

        bg = new generateTexture();

        Color color = Color.valueOf("00FFFF");

        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;

        startText = new startText(red, green, blue);
        settingsText = new settingsText(red, green, blue);
        creditText = new creditsText(red, green, blue);

        startTexture = bg.genTexture(26, 7, 10, startText.getPixels());
        settingsTexture = bg.genTexture(39, 7, 10, settingsText.getPixels());
        creditsTexture = bg.genTexture(33, 7, 10, creditText.getPixels());

        textSettingsY = (screenH - settingsTexture.getHeight()) / 2f;
        textStartY = textSettingsY + 100;
        textCreditsY = textSettingsY - 100;

        textX = (screenW - startTexture.getWidth()) / 2f;
        textSettingsX = (screenW - settingsTexture.getWidth()) / 2f;
        textCreditsX = (screenW - creditsTexture.getWidth()) / 2f;

        startMusic();

        menu = true;
        settings = false;
        credits = false;

        pal = new playerArrayList();
        pal.load();
        shipSprite = new Sprite(bg.genTexture(27, 10, pal.getArrayList()));
        shipSprite.setOriginCenter();

        shipSprite.setY(-shipSprite.getHeight());
        shipSprite.setX(screenW/7f);

        System.out.println("loaded");

        anim = true;
        animStart = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the credits input delay timer
        creditsDelayTime += Gdx.graphics.getDeltaTime();

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

        animShip();

        if(menu){
            drawText();
            inputButtonMenu();
        }

        if (settings){
//            showSettings();
//            inputButtonSettings();
        }

        if(credits){
//            showCredits();
            inputCredits();
//            renderAnim();
        }
    }



    //fix this
    public void animShip(){
//        anim = true;
//        animStart = true;
        if (anim) {
            if (animStart) {
                animStart = false;
            }
            flyAnim(8f, 400f);
        }

        spriteBatch.begin();
        shipSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void inputCredits(){
        if(Gdx.input.isTouched() && creditsDelayTime >= 1f){
            System.out.println("is pressed");
            credits = true;
            menu = true;
            creditsDelayTime = 0f;
        }
    }

    public void drawText() {
        spriteBatch.begin();

        spriteBatch.draw(startTexture, textX, textStartY);
        spriteBatch.draw(settingsTexture, textSettingsX, textSettingsY);
        spriteBatch.draw(creditsTexture, textCreditsX, textCreditsY);

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

    public void inputButtonMenu(){
        if (Gdx.input.justTouched()) {
            viewport.apply();
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

            float touchX = touchPos.x;
            float touchY = touchPos.y;

            // Start Button Area
            float startX = textX;
            float startY = textStartY;
            float startW = startTexture.getWidth();
            float startH = startTexture.getHeight();

            // Settings Button Area
            float settingsX = textSettingsX;
            float settingsY = textSettingsY;
            float settingsW = settingsTexture.getWidth();
            float settingsH = settingsTexture.getHeight();

            // Credits Button Area
            float creditsX = textCreditsX;
            float creditsY = textCreditsY;
            float creditsW = creditsTexture.getWidth();
            float creditsH = creditsTexture.getHeight();

            if (touchX >= startX && touchX <= startX + startW &&
                touchY >= startY && touchY <= startY + startH) {
                game.setScreen(new gameScreen(game, music));
            } else if (touchX >= settingsX && touchX <= settingsX + settingsW &&
                       touchY >= settingsY && touchY <= settingsY + settingsH) {
                toggleMusic();
            } else if (touchX >= creditsX && touchX <= creditsX + creditsW &&
                       touchY >= creditsY && touchY <= creditsY + creditsH) {

                menu = false;
                credits = true;
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

    public void toggleMusic(){

        if(music.isPlaying()){
            music.pause();
        } else {
            music.play();
        }
    }

    public void startMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("audioFiles/LoveIs.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);

        music.play();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        if (backgroundTexture1 != null) backgroundTexture1.dispose();
        if (backgroundTexture2 != null) backgroundTexture2.dispose();
        if (backgroundTexture3 != null) backgroundTexture3.dispose();
        if (starBackground != null) starBackground.dispose();
        if (alphaBackground != null) alphaBackground.dispose();
        if (startTexture != null) startTexture.dispose();
        if (settingsTexture != null) settingsTexture.dispose();
        if (creditsTexture != null) creditsTexture.dispose();
        if (music != null) music.dispose();
    }

    private void flyAnim(float time, float FlyingYPos) {
        float destinationY = screenH / 2f - shipSprite.getHeight() / 2f;

        animationTime += Gdx.graphics.getDeltaTime();
        float t = Math.min(animationTime / time, 1f);

        float currentY = shipSprite.getY();

        if (anim) {
            float factor;

            if (currentY <= destinationY) {
                factor = circOut(t);
                float newY = currentY + (destinationY - currentY) * factor;
                shipSprite.setY(newY);
            } else {
                factor = circIn(t);
                float newY = currentY + (screenH - currentY + shipSprite.getHeight()) * factor;
                shipSprite.setY(newY);

                if (newY > screenH + shipSprite.getHeight()) {
                    shipSprite.setY(-shipSprite.getHeight());
                    animationTime = 0f;
                    anim = true;
                    animStart = true;
                }
            }
        }
    }

    public float circOut(float t) {
        return (float)Math.sqrt(1 - (t - 1) * (t - 1));
    }

    public float circIn(float t) {
        return 1 - (float)Math.sqrt(1 - t * t);
    }
}
