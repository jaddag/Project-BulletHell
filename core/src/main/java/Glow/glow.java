package Glow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import GenTexture.generateTexture;

public class glow {
    generateTexture generateTexture;
    Texture glow;
    Sprite target;
    Sprite glowSprite;
    public glowArrayList gal;
    private float red;
    private float green;
    private float blue;
    public glow(Color color, float SizeX, Sprite target){
        this.target = target;

        this.red   = color.r;
        this.green = color.g;
        this.blue  = color.b;

        gal = new glowArrayList(red,green,blue);
        generateTexture = new generateTexture();
        glow = generateTexture.genTexture(15, 10, gal.getArrayList());

        glowSprite = new Sprite(glow);
        glowSprite.setSize(SizeX, SizeX);
        glowSprite.setOriginCenter();
    }

    public void updatePos(){
        glowSprite.setCenter(
            target.getX() + target.getWidth() * 0.5f,
            target.getY() + target.getHeight() * 0.5f
        );
    }

    public Texture getTexture(){
        return glow;
    }

    public Sprite getSprite(){
        return glowSprite;
    }
}
