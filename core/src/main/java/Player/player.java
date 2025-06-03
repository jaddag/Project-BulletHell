package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import CameraClass.camera;

import GenTexture.generateTexture;
import Glow.generateGlowTextures;

import Player.damage.attackPlayer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class player implements Disposable {
    attackPlayer attackPlayer;

    float deltaTime;

    long lastShotTime = 0;

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
    ShapeRenderer healthShape;
    ShapeRenderer healthBarBorderShape;
    int fireRate;
    SpriteBatch batch;
    float borderThickness;

    private int maxHealth;
    private int currentHealth;
    public player(Color glowColour){

        this.fireRate = 5;

        screenW = Gdx.graphics.getBackBufferWidth();
        screenH = Gdx.graphics.getBackBufferHeight();

        size = 20;

        sizeX = (float)Gdx.graphics.getBackBufferWidth()/size;
        sizeY = (float)Gdx.graphics.getBackBufferHeight()/size;

        genT = new generateTexture();
        pal = new playerArrayList();
        pal.load();
        shipTextureL = genT.genTexture(27, 10, pal.getArrayList());
        shipTextureR = new TextureRegion(shipTextureL);

        shipSprite = new Sprite(shipTextureR);

        shipSprite.setSize(sizeX, sizeX);
        shipSprite.setOriginCenter();

        bounds = new Rectangle(
            shipSprite.getX() + shipSprite.getWidth() * 0.25f,
            shipSprite.getY() + shipSprite.getHeight() * 0.25f,
            shipSprite.getWidth() * 0.5f,
            shipSprite.getHeight() * 0.5f
        );

        shipSprite.setCenter((screenW), screenH);

        glow = new generateGlowTextures();
        glow.glow(glowColour, 10, sizeX, shipSprite);

        healthShape = new ShapeRenderer();
        healthBarBorderShape = new ShapeRenderer();

        attackPlayer = new attackPlayer(glowColour);

        batch = new SpriteBatch();

        borderThickness = 2f;
    }

    public HashMap<Sprite, Vector2> getShotsMap() {
        return attackPlayer.getVelocities();
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

    public void HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void renderHealthBar(float x, float y, float width, float height) {
        healthShape.begin(ShapeRenderer.ShapeType.Filled);

        // Fake border by drawing a larger dark rectangle
        healthShape.setColor(Color.WHITE);
        healthShape.rect(x - borderThickness, y - borderThickness, width + 2 * borderThickness, height + 2 * borderThickness);

        // Background
        healthShape.setColor(Color.DARK_GRAY);
        healthShape.rect(x, y, width, height);

        // Foreground (HP)
        healthShape.setColor(Color.RED);
        float healthPercent = (float) currentHealth / maxHealth;
        healthShape.rect(x, y, width * healthPercent, height);
        healthShape.end();
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

    public void shoot(float degree) {
        if (System.currentTimeMillis() - lastShotTime >= 1000 / fireRate) {
            float radians = (float) Math.toRadians(degree);
            Vector2 velocity = new Vector2((float)Math.cos(radians), (float)Math.sin(radians)).nor().scl(500f);
            attackPlayer.genShots(velocity, getShipCenter(), degree);
            lastShotTime = System.currentTimeMillis();
        }
    }

    public boolean projectileOutsideBorder(Sprite sprite) {
        Rectangle playfield = new Rectangle(
            0,
            0,
            screenW * 2f,
            screenH * 2f
        );


        Rectangle spriteBounds = new Rectangle(
            sprite.getX(),
            sprite.getY(),
            sprite.getWidth(),
            sprite.getHeight()
        );
        if (playfield.overlaps(spriteBounds)) {
            return false;
        }
        return true;
    }

    public void updateShot(camera cam) {
        deltaTime = Gdx.graphics.getDeltaTime();
        java.util.ArrayList<Sprite> toDelete = new java.util.ArrayList<>();

        for (Sprite elem : attackPlayer.getVelocities().keySet()) {
            if (projectileOutsideBorder(elem)) {
                toDelete.add(elem);
            } else {
                batch.setProjectionMatrix(cam.getCamera().combined);
                batch.begin();
                elem.draw(batch);
                batch.end();
                Vector2 velocity = attackPlayer.getVelocities().get(elem);
                elem.translate(velocity.x * deltaTime, velocity.y * deltaTime);
            }
        }

        for (Sprite s : toDelete) {
            attackPlayer.deleteShot(s);
        }
    }

    public float getSizeX(){
        return sizeX;
    }

    public Vector2 getShipCenter() {
        return new Vector2(
            shipSprite.getX() + shipSprite.getWidth() / 2f,
            shipSprite.getY() + shipSprite.getHeight() / 2f
        );
    }

    public float getSizeY(){
        return sizeY;
    }

    public void rotate(float angle){
        shipSprite.setRotation(angle);
    }

}
