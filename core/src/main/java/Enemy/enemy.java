package Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Glow.glow;
import GenTexture.generateTexture;

public class enemy {
    Glow.glow glow;
    float screenW;
    float screenH;
    Texture enemyShipTexture;
    Sprite enemyShipSprite;
    float sizeX;
    float sizeY;
    generateTexture genT;
    enemyArrayList pal;
    Rectangle bounds;

    public enemy(Color glowColour){
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        sizeX = (float)Gdx.graphics.getWidth()/6;
        sizeY = (float)Gdx.graphics.getHeight()/6;

        genT = new generateTexture();
        pal = new enemyArrayList();
        enemyShipTexture = genT.genTexture(40, 10, pal.getPixels());

        enemyShipSprite = new Sprite(enemyShipTexture);

        enemyShipSprite.setSize(sizeX, sizeX);
        enemyShipSprite.setOriginCenter();

        bounds = new Rectangle(enemyShipSprite.getX(), enemyShipSprite.getY(), enemyShipSprite.getWidth(), enemyShipSprite.getHeight());

        enemyShipSprite.setCenter((screenW+1000), screenH);

        glow = new glow(glowColour, sizeX, enemyShipSprite);
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
}

