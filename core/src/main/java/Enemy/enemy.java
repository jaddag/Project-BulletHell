package Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import CameraClass.camera;
import Glow.generateGlowTextures;
import GenTexture.generateTexture;

import Enemy.damage.attackEnemy;

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
    SpriteBatch tempSpriteBatch;

    attackEnemy attackEnemy;

    boolean shot;

    // Timer and delay logic for shooting
    boolean delay = false;
    float delayTimer = 0f;

    float size;

    //shoot logic
    Vector2 enemyCords;
    Vector2 currentRingCords;
    Vector2 angleCalc;
    Vector2 velocity;
    float shotAngle;
    float shootCooldown;
    int howmanyrings;
    int ringscount;
    boolean onlyonce;
    int degree;
    int index;
    boolean stopGen;
    attackEnemy firstAttackEnemy;
    boolean firstDraw;
    int offset;
    ArrayList<Rectangle> allHitBoxes;

    ArrayList<attackEnemy> attackEnemyList;

    int currentHealth;
    int maxHealth;
    ShapeRenderer healthShape;
    float borderThickness;
    public enemy(Color glowColour){
        attackEnemyList = new ArrayList<>();
        firstAttackEnemy = new attackEnemy();

        this.attackEnemy = new attackEnemy();
        this.tempSpriteBatch = new SpriteBatch();

        screenW = Gdx.graphics.getBackBufferWidth();
        screenH = Gdx.graphics.getBackBufferHeight();

        size = 7;

        sizeX = screenW/size;
        sizeY = screenH/size;

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

        howmanyrings = 5;
        ringscount = 0;

        shot = true;

        currentRingCords = new Vector2();
        enemyCords = new Vector2();
        angleCalc = new Vector2();
        velocity = new Vector2();
        shotAngle = 0f;

        shootCooldown = 0f;

        onlyonce = true;

        degree = 0;
        index = 0;
        stopGen = true;
        firstDraw = true;

        offset = 0;

        allHitBoxes = new ArrayList<>();

        healthShape = new ShapeRenderer();

        borderThickness = 2f;
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

    public void updateGlow(){
        glow.updatePos();
    }

    public Sprite getGlow(){
        return glow.getSprite();
    }

    public ArrayList<Rectangle> getRingBounds(){
        return allHitBoxes;
    }



    public void circularAttack(camera cam, float delta) {
        // Increment delay timer and update delay flag
        updateGlow();
        tempSpriteBatch.setProjectionMatrix(cam.getCamera().combined);

//        if(onlyonce1){
//            genSingleRings();
//            onlyonce1 = false;
//        }

        index = 0;

        delayTimer += delta;

        if (delayTimer >= 0.7f || firstDraw) {
            if (attackEnemyList.isEmpty() || !areAllProjectilesOutside(attackEnemyList.get(0))) {
                genSingleRings();
            }
            delayTimer = 0f;
            firstDraw = false;
        }

        for(attackEnemy elem: attackEnemyList) {
            shootRings(600, elem);
            updateRings(delta, elem);
            showRings(elem);

            if (areAllProjectilesOutside(elem)) {
                elem.resetRingPositions();
                if (onlyonce) {

                    stopGen = false;
                    onlyonce = false;

                }
            }
        }
        attackEnemy.updateHitBoxes();

        allHitBoxes.clear();
        for (attackEnemy atk : attackEnemyList) {
            allHitBoxes.addAll(atk.getHitBoxes());
        }

//        updateRotation();
    }


    public void updatePos(){
        // Set enemyCords to the center of the enemy sprite
        enemyCords = new Vector2(
            this.enemyShipSprite.getX() + this.enemyShipSprite.getWidth() / 2f,
            this.enemyShipSprite.getY() + this.enemyShipSprite.getHeight() / 2f
        );
    }

    public Vector2 getEnemyCords() {
        updatePos();
        return enemyCords;
    }

    public static Rectangle getBounds() {
        return null;
    }

    public Sprite getSprite(){
        return enemyShipSprite;
    }

    public boolean areAllProjectilesOutside(attackEnemy attackEnemy) {
        Rectangle playfield = new Rectangle(
            0,
            0,
            screenW * 2f,
            screenH * 2f
        );

        for(Sprite elem : attackEnemy.getRings()){
            Rectangle ringBounds = new Rectangle(
                elem.getX(),
                elem.getY(),
                elem.getWidth(),
                elem.getHeight()
            );
            if (playfield.overlaps(ringBounds)) {
                return false;
            }
        }

        return true; // All rings are outside
    }

    public void showRings(attackEnemy attackEnemy){
        tempSpriteBatch.begin();
        for(Sprite elem: attackEnemy.getRings()){
            elem.draw(tempSpriteBatch);
        }
        tempSpriteBatch.end();
    }

    public void initBatchIfNeeded() {
        if (tempSpriteBatch == null) {
            tempSpriteBatch = new SpriteBatch();
        }
    }

    public void shootRings(float speed, attackEnemy attackEnemy){
        for(int i = 0; i < attackEnemy.getRings().size(); i++){
            Sprite elem = attackEnemy.getRings().get(i);
            Vector2 velocity = attackEnemy.getRingVelocities().get(i);

            currentRingCords.set((elem.getX() + elem.getWidth()/2f), (elem.getY() + elem.getHeight()/2f));

            angleCalc.set(currentRingCords.x - getEnemyCords().x, currentRingCords.y - getEnemyCords().y);
            shotAngle = (float)Math.atan2(angleCalc.y, angleCalc.x);
            velocity.set(speed * MathUtils.cos(shotAngle), speed * MathUtils.sin(shotAngle));
        }
    }

    public void updateRings(float deltaTime, attackEnemy attackEnemy){
        for (int i = 0; i < attackEnemy.getRings().size(); i++) {
            Sprite ring = attackEnemy.getRings().get(i);
            Vector2 vel = attackEnemy.getRingVelocities().get(i);
            ring.translate(vel.x * deltaTime, vel.y * deltaTime);
        }
        attackEnemy.updateHitBoxes();
    }

    public void genRings() {
        attackEnemyList.clear();
        for (int i = 0; i <= howmanyrings; i++) {
            attackEnemyList.add(new attackEnemy());
        }

        for(attackEnemy elem: attackEnemyList){
            elem.ringAttack(12, 250f, enemyShipSprite, 0);
        }
    }

    public void genSingleRings(){
        if (stopGen) {
            attackEnemy atk = new attackEnemy();
            atk.ringAttack(12, 250f, enemyShipSprite, offset);
            offset = offset + 10;
            attackEnemyList.add(atk);

            if (firstAttackEnemy == null) {
                firstAttackEnemy = atk;
            }
        }
    }

    public void updateRotation(){
        for(attackEnemy elem: attackEnemyList){
            degree++;
            elem.rotateAllRings(degree, enemyShipSprite);

            if (degree == 360){
                degree = 0;
            }
        }
    }

    public ArrayList<attackEnemy> getAttackList() {
        return attackEnemyList;
    }
}

