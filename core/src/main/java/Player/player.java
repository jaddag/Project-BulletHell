package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import GenTexture.generateTexture;
import Glow.glow;

public class player implements Disposable {

    glow glow;
    float screenW;
    float screenH;
    Texture shipTextureL;
    TextureRegion shipTextureR;
    Sprite shipSprite;
    float sizeX;
    float sizeY;
    generateTexture genT;
    playerArrayList pal;
    Rectangle bounds;

    public player(Color glowColour){
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        sizeX = (float)Gdx.graphics.getWidth()/10;
        sizeY = (float)Gdx.graphics.getHeight()/10;

        genT = new generateTexture();
        pal = new playerArrayList();
        pal.load();
        shipTextureL = genT.genTexture(30, 10, pal.getArrayList());
        shipTextureR = new TextureRegion(shipTextureL);
        shipTextureR.flip(true, false);
//        shipTexture = new Texture("Player/ship.png");

        shipSprite = new Sprite(shipTextureR);

        shipSprite.setSize(sizeX, sizeX);
        shipSprite.setOriginCenter();

        bounds = new Rectangle(shipSprite.getX(), shipSprite.getY(), shipSprite.getWidth(), shipSprite.getHeight());

        shipSprite.setCenter((screenW), screenH);

        glow = new glow(glowColour, sizeX*0.7f, shipSprite);
    }

//    public void update(float delta) {
//        bounds.setPosition(shipSprite.getX(), shipSprite.getY());
//    }

    public void updateGlow(){
        glow.updatePos();
    }

    public Sprite getGlow(){
        return glow.getSprite();
    }
    public static Rectangle getBounds() {
        return null;
    }

    public Sprite getSprite(){
        return shipSprite;
    }

    @Override
    public void dispose() {
        if (shipTextureL != null) {
            shipTextureL.dispose();
        }
        shipTextureR = null;
    }

    public void lookRight(){
        shipSprite.setRegion(shipTextureR);
    }

    public void lookLeft(){
        shipSprite.setRegion(shipTextureL);
    }

    public void shoot(Sprite target){

        target.getY();
        target.getX();

    }

    public float getSizeX(){
        return sizeX;
    }

    public float getSizeY(){
        return sizeY;
    }

}
