package Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Glow.generateGlowTextures;
import GenTexture.generateTexture;

import Enemy.damage.attack;

public class enemy {
    generateGlowTextures glow;
    float screenW;
    float screenH;
    Texture enemyShipTexture;
    Sprite enemyShipSprite;
    float sizeX;
    float sizeY;
    generateTexture genT;
    enemyArrayList pal;
    Rectangle bounds;

    attack attack;
    public enemy(Color glowColour){
        this.attack = new attack();

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        sizeX = (float)Gdx.graphics.getWidth()/5;
        sizeY = (float)Gdx.graphics.getHeight()/5;

        genT = new generateTexture();
        pal = new enemyArrayList();
        enemyShipTexture = genT.genTexture(40, 10, pal.getPixels());

        enemyShipSprite = new Sprite(enemyShipTexture);

        enemyShipSprite.setSize(sizeX, sizeX);
        enemyShipSprite.setOriginCenter();

        bounds = new Rectangle(enemyShipSprite.getX(), enemyShipSprite.getY(), enemyShipSprite.getWidth(), enemyShipSprite.getHeight());

        enemyShipSprite.setCenter((screenW+1000), screenH);

        glow = new generateGlowTextures();
        glow.glow(glowColour, 10, sizeX, enemyShipSprite);
        glow.updatePos();


    }

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
        return enemyShipSprite;
    }

    public void rings(){
        attack.ringAttack(10);
    }

//    public updateEnemyPosition(Camera camera){
//
//    }
}

