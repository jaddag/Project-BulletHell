package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import ArrayListDraw.earthArrayList;
import Background.bgGenerator;
import Background.planetGen;

public class startScreen implements ApplicationListener{

        Texture backgroundTexture1;
        Texture backgroundTexture2;
        Texture backgroundTexture3;
        Texture planet1;
        float speed;
        float speed1;
        float speed2;
        float scrollY;
        float scrollY1;
        float scrollY2;

        boolean enableInput;
        boolean anim;

        Texture shipTexture;
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

        Sprite shipSprite;
        Random rand = new Random();

        int gridSize;

        @Override
        public void create() {
            anim = true;
            enableInput = false;

            scrollY = 0;

            deltaTime = Gdx.graphics.getDeltaTime();

            speed = 300f;
            speed1 = 190;
            speed2 = 100;

            screenW = Gdx.graphics.getWidth();
            screenH = Gdx.graphics.getHeight();

            font = new BitmapFont(); // default font
            spriteBatch = new SpriteBatch();
            viewport = new FitViewport(screenW, screenH);

            worldWidth = viewport.getWorldWidth();
            worldHeight = viewport.getWorldHeight();

            finalRefreshRate = Gdx.graphics.getDisplayMode().refreshRate;


//            backgroundTexture = new Texture("Background/starBackground1.png");
            shipTexture = new Texture("Player/ship.png");
//            dropTexture = new Texture("drop.png");

//            dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
//            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));


            shipSprite = new Sprite(shipTexture);
            shipSprite.setCenter(1600 , 300);
            shipSprite.setSize(worldWidth/10, worldWidth/10);

            touchPos = new Vector2();

            planetGen plGen = new planetGen();

            bgGenerator bg = new bgGenerator();

            //paralaxx
            try {
                backgroundTexture3 = bg.starGen(100, 22, 0.2f, 33, false, 5, 3);
                backgroundTexture2 = bg.starGen(10, 20, 0.4f, 33, false, 7, 4);
                backgroundTexture1 = bg.starGen(rand.nextInt() * 1000, 17, 0.6f, 63, true, 9, 5);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            gridSize = 25;

            planet1 = plGen.Earth(gridSize);

        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        @Override
        public void render() {
            if(anim == true) {
                flyAnim();
            }
            backgroundDraw();
            updateBGPos();
            input();
//		    logic();
            draw();

        }


    @Override
        public void pause() {
            // TODO Auto-generated method stub

        }

        @Override
        public void resume() {
            // TODO Auto-generated method stub

        }

        @Override
        public void dispose() {
            backgroundTexture1.dispose();
            shipTexture.dispose();
            dropTexture.dispose();
            spriteBatch.dispose();

        }

        private void input() {
            float worldWidth = viewport.getWorldWidth();



//            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//
//                shipSprite.translateX(speed * deltaTime);
//
//
//
//            } else if (Gdx.input.isKeyPressed(Input.Keys.A)){
//
//                if(shipSprite.getX()!= 0 && shipSprite.getX()!= worldWidth) shipSprite.translateX(-speed * deltaTime);
//
//            }

            if(shipSprite.getY() >= 400){
                enableInput = true;
            }

            if (Gdx.input.isTouched() && enableInput) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos);
                shipSprite.setCenterX(touchPos.x);
                shipSprite.setCenterY(touchPos.y);
            }
        }



        private void draw() {
            float worldWidth = viewport.getWorldWidth();
            float worldHeight = viewport.getWorldHeight();

            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            font.getData().setScale(3f);
            spriteBatch.begin();

            font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + Gdx.graphics.getWidth() + " x " + Gdx.graphics.getHeight(), 20, worldHeight-30);
            font.draw(spriteBatch, "PosX: " + shipSprite.getX() + " PoxY: " + shipSprite.getY() , 20, worldHeight-90);
            font.draw(spriteBatch, "scrollX: " + scrollY + " scrollX1:" + scrollY1, 20, worldHeight-150);
            shipSprite.draw(spriteBatch);

            spriteBatch.draw(planet1, 1200-gridSize, -540);

            spriteBatch.end();
        }


    private void backgroundDraw() {

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture1, 0, -scrollY, worldWidth, worldHeight);
        spriteBatch.draw(backgroundTexture1, 0, worldHeight-scrollY, worldWidth, worldHeight);


        spriteBatch.draw(backgroundTexture2, 0, -scrollY1, worldWidth, worldHeight);
        spriteBatch.draw(backgroundTexture2, 0, worldHeight-scrollY1, worldWidth, worldHeight);

        spriteBatch.draw(backgroundTexture3, 0, -scrollY2, worldWidth, worldHeight);
        spriteBatch.draw(backgroundTexture3, 0, worldHeight-scrollY2, worldWidth, worldHeight);

        spriteBatch.end();

    }

    private void updateBGPos() {
        deltaTime = Gdx.graphics.getDeltaTime();
        scrollY += speed * deltaTime;
        if(scrollY >= worldHeight){
            scrollY = 0;
        }

        scrollY1 += speed1 * deltaTime;
        if(scrollY1 >= worldHeight){
            scrollY1 = 0;
        }

        scrollY2 += speed2 * deltaTime;
        if(scrollY2 >= worldHeight){
            scrollY2 = 0;
        }

    }

    private void flyAnim() {

        shipSprite.translateY(speed*deltaTime);

        if(shipSprite.getY() >= 400){
            anim = false;
        }

    }

}
