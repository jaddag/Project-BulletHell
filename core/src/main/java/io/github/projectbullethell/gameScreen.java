package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import HUD.joyStick;
import HUD.drawHUD;
import MainMethod.bulletHellMain;
import Player.player;
import camera.camera;

public class gameScreen implements Screen {
        private final bulletHellMain game;

        Texture backgroundTexture1;
        Texture backgroundTexture2;
        Texture backgroundTexture3;
        Texture planet1;
        Texture starBackground;
        Texture alphaBackground;

        float shipSpeed;
        float speed;
        float speed1;
        float speed2;
        float speedSky;
        float scrollY;
        float scrollY1;
        float scrollY2;
        float scrollSkyTexture;


        boolean enableInput;
        boolean anim;

        Texture dropTexture;
        BitmapFont font;

        Vector2 touchPos;

        float worldWidth;
        float worldHeight;
        int screenW;
        int screenH;
        int finalRefreshRate;
        float deltaTime;

        SpriteBatch spriteBatch;
        FitViewport viewport;
        camera camera;

        Rectangle shipRectangle;
        Rectangle planetRectangle;
        Sound dropSound;

        Sprite shipSprite;
        Random rand = new Random();

        int gridSize;
        joyStick js;
        Vector2 jsCords;

        float flyElapsed;

        int time;
        int timeCount;
        Vector2 buttonCords;
        Vector2 button2Cords;

        drawHUD drawHUD;

    public gameScreen(bulletHellMain game) {
        this.game = game;
    }

        @Override
    public void show() {
        anim = true;
        enableInput = true;

        gridSize = 10;

        scrollY = 0;

        flyElapsed = 0f;
        time = 0;
        timeCount = 0;

        deltaTime = Gdx.graphics.getDeltaTime();

        shipSpeed = 20f;
        speed = 40;
        speed1 = 30;
        speed2 = 20f;
        speedSky = 10f;

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();
        finalRefreshRate = Gdx.graphics.getDisplayMode().refreshRate;

        font = new BitmapFont();

        spriteBatch = new SpriteBatch();

        player player = new player();
        shipSprite = player.getSprite();
        shipSprite.setCenter((screenW/2), screenH/2);

        camera = new camera(shipSprite);

        viewport = new FitViewport(screenW, screenH, camera.getCamera());

//            dropSound = Gdx.audio.newSound(Gdx.files.internal("audioFiles/drop.mp3"));
//            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        touchPos = new Vector2();

        //paralaxx
        getBG();

        //HUD
        HUD();
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

    public void HUD(){
        buttonCords = new Vector2(screenW -200f, 200f);
        button2Cords = new Vector2(0f,0f);
        jsCords = new Vector2(screenW*0.1f, screenH*0.2f);

        drawHUD = new drawHUD(true, enableInput, jsCords, buttonCords);
        drawHUD.setShipSpeed(shipSpeed);
        drawHUD.setShipSprite(shipSprite);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        drawHUD.getHudViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
//            if (anim) {
//                if (animStart) {
//                    flyStartY = shipSprite.getY();
//                    flyElapsed = 0f;
//                    animStart = false;
//                }
//                flyAnim(2f, 400f, 0.02f);
//            }
//
//            timeCount++;
//            if(timeCount == 60){
//                time++;
//                timeCount =0;
//            }

        backgroundDraw();
        updateBGPos();
        headUpDisplay();
        updateCamera();
        draw();
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        backgroundTexture1.dispose();
        if (dropTexture != null) dropTexture.dispose();
        spriteBatch.dispose();
        drawHUD.getSpriteBatch().dispose();

    }

    private void headUpDisplay() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        }

        drawHUD.getHudViewport().apply();
        drawHUD.draw(touchPos);

        drawHUD.devConsole("X: " + camera.getCameraPos().x + "Y: " + camera.getCameraPos().y);

    }

    private void draw() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        font.getData().setScale(3f);
        spriteBatch.begin();

//            font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH - 30);
//            font.draw(spriteBatch, "PosX: " + (shipSprite.getX() + (shipSprite.getWidth() / 2)) + " PoxY: " + (shipSprite.getY() + (shipSprite.getHeight() / 2)), 20, screenH - 90);
//            font.draw(spriteBatch, "dev Options:" , 20, screenH - 210);

        shipSprite.draw(spriteBatch);

        spriteBatch.end();
    }

    private void updateCamera(){
        camera.update( 1f, 1f);
    }

    private void backgroundDraw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        spriteBatch.draw(starBackground, 0, -scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(starBackground, 0, screenH-scrollSkyTexture, screenW, screenH);

        spriteBatch.draw(alphaBackground,camera.getCameraPos().x-((float) screenW /2),camera.getCameraPos().y-((float) screenH /2), screenW, screenH);

        spriteBatch.draw(backgroundTexture1, 0, -scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, 0, screenH-scrollY, screenW, screenH);


        spriteBatch.draw(backgroundTexture2, 0, -scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, 0, screenH-scrollY1, screenW, screenH);

        spriteBatch.draw(backgroundTexture3, 0, -scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, 0, screenH-scrollY2, screenW, screenH);

//        spriteBatch.draw(planet4, 300-gridSize, -100);
//        spriteBatch.draw(planet2, 400-gridSize, -200);
//        spriteBatch.draw(planet3, 1700-gridSize, -100);

        spriteBatch.end();
    }

    private void updateBGPos() {
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

    private void flyAnim(float time, float FlyingYPos) {
        if(shipSprite.getY() >= 400){
            anim = false;
        }

        float t = Math.min(Gdx.graphics.getDeltaTime() / time, 1f);

        float posY = shipSprite.getY() + (FlyingYPos - shipSprite.getY()) * circOut(t);

        shipSprite.setY(posY);
    }

    public void logic(){
        shipSprite.setX(MathUtils.clamp(shipSprite.getX(), 0, worldWidth));

        if (shipRectangle.overlaps(planetRectangle)){
            dropSound.play();
        }
    }

    public float circOut(float t) {
        return (float)Math.sqrt(1 - (t - 1) * (t - 1));
    }

    @Override
    public void hide() {
        // Optional: implement if needed
    }
}
