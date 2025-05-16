package Glow;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import GenTexture.generateTexture;

public class generateGlowTextures {
    generateTexture generateTexture;
    Pixmap GridArray;
    Pixmap GridArrayREACT;
    Pixmap RingArray;
    Texture glowArea;
    Sprite target;
    Sprite glowSprite;
    public glow gsal;
    public glowRingArrayList gral;
    public glowGridArrayListREACT ggalr;
    public glowGridArrayList ggal;
    private float red;
    private float green;
    private float blue;

    public void glow(Color color, int size, float SizeX, Sprite target) {
        this.target = target;
        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;

        gsal = new glow(red, green, blue);
        generateTexture = new generateTexture();

        glowArea = generateTexture.genTexture(15, size, gsal.getArrayList());

        this.glowSprite = new Sprite(glowArea);
        this.glowSprite.setSize(SizeX, SizeX);
        this.glowSprite.setOriginCenter();
    }

    public Pixmap glowRingAttack(Color color, int size) {
        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;

        gral = new glowRingArrayList(red, green, blue);
        generateTexture = new generateTexture();

        RingArray = generateTexture.genPixmap(30, size, gral.getPixels());
        return RingArray;
    }

    public Pixmap glowGridAttack(Color color, int size){
        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;

        ggal = new glowGridArrayList(red, green, blue);
        generateTexture = new generateTexture();

        GridArray = generateTexture.genPixmap(15, size, ggal.getPixels());
        return GridArray;
    }

    public Pixmap glowGridAttackREACT(Color color, int size){
        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;

        ggalr = new glowGridArrayListREACT(red, green, blue);
        generateTexture = new generateTexture();

        GridArrayREACT = generateTexture.genPixmap(15, size, ggalr.getPixels());
        return GridArrayREACT;
    }


    public void updatePos(){
        if (glowSprite == null || target == null) return;
        glowSprite.setCenter(
            target.getX() + target.getWidth() * 0.5f,
            target.getY() + target.getHeight() * 0.5f
        );
    }

    public Sprite getSprite(){
        return glowSprite;
    }
}
