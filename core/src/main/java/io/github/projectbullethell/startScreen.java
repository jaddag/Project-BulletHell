package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class startScreen implements ApplicationListener{

        Texture backgroundTexture;
        Texture shipTexture;
        Texture dropTexture;
        Sound dropSound;
        Music music;

        BitmapFont font;

        Vector2 touchPos;
        float speed = 300f;

        float worldWidth;
        float worldHeight;
        int screenW;
        int screenH;
        int finalRefreshRate;

        SpriteBatch spriteBatch;
        FitViewport viewport;

        Sprite shipSprite;

        @Override
        public void create() {
            screenW = Gdx.graphics.getWidth();
            screenH = Gdx.graphics.getHeight();

            font = new BitmapFont(); // default font
            spriteBatch = new SpriteBatch();
            viewport = new FitViewport(screenW, screenH);

            worldWidth = viewport.getWorldWidth();
            worldHeight = viewport.getWorldHeight();

            finalRefreshRate = Gdx.graphics.getDisplayMode().refreshRate;


            backgroundTexture = new Texture("Background/starBackground1.png");
            shipTexture = new Texture("Player/ship.png");
//            dropTexture = new Texture("drop.png");

//            dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
//            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));


            shipSprite = new Sprite(shipTexture);
            shipSprite.setSize(worldWidth/10, worldWidth/10);

            touchPos = new Vector2();
        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        @Override
        public void render() {
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
            backgroundTexture.dispose();
            shipTexture.dispose();
            dropTexture.dispose();
            spriteBatch.dispose();

        }

        private void input() {
            float worldWidth = viewport.getWorldWidth();

            float speed = this.speed;
            float deltaTime = Gdx.graphics.getDeltaTime();


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

            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos);
                shipSprite.setCenterX(touchPos.x);
                shipSprite.setCenterY(touchPos.y);
            }
        }



        private void draw() {
            float worldWidth = viewport.getWorldWidth();
            float worldHeight = viewport.getWorldHeight();

            ScreenUtils.clear(Color.BLACK);
            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            spriteBatch.begin();

            font.getData().setScale(1f);

            spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
            font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), worldWidth-100, worldHeight-10); // x=100, y=100
            shipSprite.draw(spriteBatch);

            spriteBatch.end();
        }


    }
