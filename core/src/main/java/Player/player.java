package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import GenTexture.generateTexture;
import Glow.generateGlowTextures;

public class player implements Disposable {

    generateGlowTextures glow;
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
    int health;
    int size;

    public player(Color glowColour){

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        size = 20;

        sizeX = (float)Gdx.graphics.getWidth()/size;
        sizeY = (float)Gdx.graphics.getHeight()/size;

        genT = new generateTexture();
        pal = new playerArrayList();
        pal.load();
        shipTextureL = genT.genTexture(27, 10, pal.getArrayList());
        shipTextureR = new TextureRegion(shipTextureL);
//        shipTextureR.flip(true, false);
//        shipTexture = new Texture("Player/ship.png");

        shipSprite = new Sprite(shipTextureR);

        shipSprite.setSize(sizeX, sizeX);
        shipSprite.setOriginCenter();

        bounds = new Rectangle(
            shipSprite.getX() + shipSprite.getWidth() * 0.25f,
            shipSprite.getY() + shipSprite.getHeight() * 0.25f,
            shipSprite.getWidth() * 0.5f,
            shipSprite.getHeight() * 0.5f
        );

//        bounds = new Rectangle(shipSprite.getX(), shipSprite.getY(), shipSprite.getWidth(), shipSprite.getHeight());

        shipSprite.setCenter((screenW), screenH);

        glow = new generateGlowTextures();
        glow.glow(glowColour, 10, sizeX, shipSprite);
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

    public Rectangle getBounds(){
        bounds.set(
            shipSprite.getX() + shipSprite.getWidth() * 0.25f,
            shipSprite.getY() + shipSprite.getHeight() * 0.25f,
            shipSprite.getWidth() * 0.5f,
            shipSprite.getHeight() * 0.5f
        );
        return bounds;
    }


    public Sprite getSprite(){
        return shipSprite;
    }
        private int maxHealth;
        private int currentHealth;

    public void HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void render(ShapeRenderer shapeRenderer, float x, float y, float width, float height) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, width, height);

        // Foreground (HP)
        shapeRenderer.setColor(Color.RED);
        float healthPercent = (float) currentHealth / maxHealth;
        shapeRenderer.rect(x, y, width * healthPercent, height);

        shapeRenderer.end();
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth < 0) currentHealth = 0;
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
    }

    public int getHealth() {
        return currentHealth;
    }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
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
