package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import Enemy.enemy;
import Enemy.damage.loadAttacks;
import CameraClass.camera;
import CameraClass.bgAdjustment;
import Collision.collision;
import ShowBounds.showBounds;

public class gameScreen implements Screen {
    private final bulletHellMain game;
    private final Music music;
    showBounds showBounds;
    Texture backgroundTexture1;
    Texture backgroundTexture2;
    Texture backgroundTexture3;
    Texture starBackground;
    TextureRegion flippedStarBackground;
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
    Sprite shipGlow;
    Sprite enemySprite;
    Sprite enemyGlow;
    Random rand = new Random();

    int gridSize;
    joyStick js;
    Vector2 jsCords;
    Vector2 jsCords2;

    float flyElapsed;

    int time;
    int timeCount;
    Vector2 buttonCords;


    drawHUD drawHUD;
    bgAdjustment bgAdjust;
    player player1;
    enemy enemy1;
    int sps = 0;

    loadAttacks loadAttacks;

    collision collision;

    public gameScreen(bulletHellMain game, Music music) {
        this.game = game;
        this.music = music;
    }

        @Override
    public void show() {
        loadAttacks = new loadAttacks(game);

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

        screenW = Gdx.graphics.getBackBufferWidth();
        screenH = Gdx.graphics.getBackBufferHeight();
        finalRefreshRate = Gdx.graphics.getDisplayMode().refreshRate;

        font = new BitmapFont();

        spriteBatch = new SpriteBatch();

        player1 = new player(new Color(0f, 180/255f, 255/255f, 1f));
        shipSprite = player1.getSprite();
        shipGlow = player1.getGlow();

        enemy1 = new enemy(new Color(1f, 0f, 0f, 0f));
        enemySprite = enemy1.getSprite();
        enemyGlow = enemy1.getGlow();

        camera = new camera(shipSprite);

        viewport = new FitViewport(screenW, screenH, camera.getCamera());

//            dropSound = Gdx.audio.newSound(Gdx.files.internal("audioFiles/drop.mp3"));
//            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        touchPos = new Vector2();

        //paralaxx
        getBG();

        //HUD
        HUD();

        bgAdjust = new bgAdjustment(camera, 10f);

        flippedStarBackground = new TextureRegion(starBackground);
        flippedStarBackground.flip(true, false);

        //Collision Detection
        collision = new collision(this.enemy1, this.player1);

        setHitBox();
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
//        buttonCords = new Vector2(screenW -200f, 200f);
        jsCords = new Vector2(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.2f);
        jsCords2 = new Vector2(Gdx.graphics.getWidth()*0.9f, Gdx.graphics.getHeight()*0.2f);

        drawHUD = new drawHUD(true, enableInput, jsCords, jsCords2, player1, enemy1);
        drawHUD.setShipSpeed(shipSpeed);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        drawHUD.getHudViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        backgroundDraw();
        updateBGPos(0f);
        enemySpriteUpdate();
        playerSpriteUpdate();
        updateCamera();
        draw();
        headUpDisplay();
        detectCollision();
        killPlayer();
        killBoss();
        HitBox();
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

    public void setHitBox(){
        showBounds = new showBounds();
    }

    public void HitBox(){
        showBounds.showB(camera, player1.getBounds());
        for (Rectangle elem : enemy1.getRingBounds()) {
            showBounds.showB(camera, elem);
        }
    }

    public void killPlayer(){
        if(player1.getHealth() <= 0){
            game.setScreen(new deathScreen(game, music));
        }
    }

    public void killBoss(){
        if(enemy1.getHealth() <= 0){
            game.setScreen(new winScreen(game, music));
        }
    }

    private void playerSpriteUpdate(){
        shipSprite = player1.getSprite();
        player1.updateGlow();
        player1.updateShot(camera);
    }

    private void enemySpriteUpdate(){
        enemySprite = enemy1.getSprite();
        enemy1.initBatchIfNeeded();
        enemy1.circularAttack(camera, deltaTime);
    }

    private void headUpDisplay() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        }

        drawHUD.getHudViewport().apply();
        drawHUD.draw(touchPos);
        drawHUD.devConsole("topBorder: " + camera.getTop() + " bottomBorder: " + camera.getBottom() + " leftBorder: " + camera.getLeft() + " rightBorder: " + camera.getRight() ) ;
    }

    private void draw() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        font.getData().setScale(3f);

        spriteBatch.begin();

        shipGlow.draw(spriteBatch);
        shipSprite.draw(spriteBatch);

        enemyGlow.draw(spriteBatch);
        enemySprite.draw(spriteBatch);

        spriteBatch.end();
    }

    private void updateCamera(){
        camera.update( 1f, 1f);
    }

    private void backgroundDraw() {
        bgAdjust.updateCam();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

//        spriteBatch.draw(starBackground, bgAdjust.getInfiniteBGCordsR(), 0, screenW, screenH);
//        spriteBatch.draw(flippedStarBackground, bgAdjust.getInfiniteBGCordsL(), 0, screenW, screenH);

        spriteBatch.draw(starBackground, 0, - scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(starBackground, 0, screenH - scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(starBackground, 0, (screenH*2) - scrollSkyTexture, screenW, screenH);

        spriteBatch.draw(flippedStarBackground, screenW, - scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(flippedStarBackground, screenW, screenH - scrollSkyTexture, screenW, screenH);
        spriteBatch.draw(flippedStarBackground, screenW, (screenH*2) - scrollSkyTexture, screenW, screenH);

        spriteBatch.draw(alphaBackground,camera.getCameraPos().x-((float) screenW /2),camera.getCameraPos().y-((float) screenH /2), screenW, screenH);

        spriteBatch.draw(backgroundTexture1, 0, -scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, 0, screenH-scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, 0, (screenH*2)-scrollY, screenW, screenH);

        spriteBatch.draw(backgroundTexture1, screenW, -scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, screenW, screenH-scrollY, screenW, screenH);
        spriteBatch.draw(backgroundTexture1, screenW, (screenH*2)-scrollY, screenW, screenH);


        spriteBatch.draw(backgroundTexture2, 0, -scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, 0, screenH-scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, 0, (screenH*2)-scrollY1, screenW, screenH);

        spriteBatch.draw(backgroundTexture2, screenW, -scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, screenW, screenH-scrollY1, screenW, screenH);
        spriteBatch.draw(backgroundTexture2, screenW, (screenH*2)-scrollY1, screenW, screenH);


        spriteBatch.draw(backgroundTexture3, 0, -scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, 0, screenH-scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, 0, (screenH*2)-scrollY2, screenW, screenH);

        spriteBatch.draw(backgroundTexture3, screenW, -scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, screenW, screenH-scrollY2, screenW, screenH);
        spriteBatch.draw(backgroundTexture3, screenW, (screenH*2)-scrollY2, screenW, screenH);

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

    @Override
    public void hide() {
        // Optional: implement if needed
    }

    public void detectCollision(){
        collision.update();
    }


}
