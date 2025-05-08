package io.github.projectbullethell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import Background.bgGenerator;
import Background.spaceBGGen;
import HUD.JoyStick;
import Player.player;

public class startScreen implements ApplicationListener{

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

        Rectangle shipRectangle;
        Rectangle planetRectangle;
        Sound dropSound;

        Sprite shipSprite;
        Random rand = new Random();

        int gridSize;
        JoyStick js;
        Vector2 jsCords;

        @Override
        public void create() {
            anim = true;
            enableInput = false;

            scrollY = 0;

            deltaTime = Gdx.graphics.getDeltaTime();

            shipSpeed = 20f;

            speed = 40;
            speed1 = 30;
            speed2 = 20f;
            speedSky = 10f;

            screenW = Gdx.graphics.getWidth();
            screenH = Gdx.graphics.getHeight();

            font = new BitmapFont(); // default font
            spriteBatch = new SpriteBatch();
            viewport = new FitViewport(screenW, screenH);

            finalRefreshRate = Gdx.graphics.getDisplayMode().refreshRate;


            player player = new player();
            shipSprite = player.getSprite();

            shipSprite.setCenter((screenW), screenH);


            dropSound = Gdx.audio.newSound(Gdx.files.internal("audioFiles/drop.mp3"));
//            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));


            touchPos = new Vector2();

            spaceBGGen plGen = new spaceBGGen();

            bgGenerator bg = new bgGenerator();

            //paralaxx
            try {
                backgroundTexture3 = bg.starGen(100, 22, 0.2f, 33, false, 5, 3, false);
                backgroundTexture2 = bg.starGen(10, 20, 0.4f, 33, false, 7, 4, false);
                backgroundTexture1 = bg.starGen(rand.nextInt() * 1000, 17, 0.6f, 63, true, 9, 5, false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            gridSize = 10;

            shipRectangle = new Rectangle();
            planetRectangle = new Rectangle();

            planet1 = plGen.Nebula(gridSize);
//            planet2 = plGen.Jupiter(gridSize);
//            planet3 = plGen.Saturn(gridSize);
//            planet4 = plGen.Sun(gridSize);

            starBackground = bg.starBackground(2, 2, 4, screenW, screenH*2, 0.005f, 0.005f, 0.5f, 0.1f);
            alphaBackground = bg.coloredBackground(screenW, screenH, new Color(0f,0f,0f,0.5f));


            jsCords = new Vector2(screenW*0.1f, screenH*0.2f);
            js = new JoyStick(150, jsCords);

        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height, true);
        }

        @Override
        public void render() {
            if(anim) {
//                flyAnim(2f, 400f);
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

            if(enableInput){
                if (Gdx.input.isTouched()) {
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                    viewport.unproject(touchPos);
                    js.moveJoyStick(shipSprite, shipSpeed, touchPos);
                }else{
                    js.reset();
                }
            }

        }



        private void draw() {
            int worldWidth = screenW;
            int worldHeight = screenH;

            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            font.getData().setScale(3f);
            spriteBatch.begin();

            font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH-30);
            font.draw(spriteBatch, "PosX: " + (shipSprite.getX()+(shipSprite.getWidth()/2)) + " PoxY: " + (shipSprite.getY()+(shipSprite.getHeight()/2)) , 20, screenH-90);
            font.draw(spriteBatch, "scrollX: " + scrollY + " scrollX1:" + scrollY1, 20, screenH-150);
            font.draw(spriteBatch, "isPressed?: " + js.getInput() +" firstTouch: " + js.getFirstTouch(), 20, screenH-210);

            shipSprite.draw(spriteBatch);


            spriteBatch.end();


            js.draw();



        }


    private void backgroundDraw() {

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        spriteBatch.draw(starBackground, screenW / 2f - starBackground.getWidth() / 2f, (screenH - starBackground.getHeight() / 2f) - scrollSkyTexture);

        spriteBatch.draw(alphaBackground, screenW / 2f - starBackground.getWidth() / 2f, screenH - starBackground.getHeight() / 2f);

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

    private void createCollison(){

    }

    public float circOut(float t) {
        return (float)Math.sqrt(1 - (t - 1) * (t - 1));
    }

}
