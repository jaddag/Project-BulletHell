package Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import CameraClass.camera;
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
    SpriteBatch tempSpriteBatch;

    attack attack;

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
    attack firstAttack;
    boolean firstDraw;
    int offset;

    ArrayList<attack> attackList;
    public enemy(Color glowColour){
        attackList = new ArrayList<>();
        firstAttack = new attack();

        this.attack = new attack();
        this.tempSpriteBatch = new SpriteBatch();

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        size = 7;

        sizeX = (float)Gdx.graphics.getWidth()/size;
        sizeY = (float)Gdx.graphics.getHeight()/size;

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
    }

    public void updateGlow(){
        glow.updatePos();
    }

    public Sprite getGlow(){
        return glow.getSprite();
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
            if (attackList.isEmpty() || !areAllProjectilesOutside(attackList.get(0))) {
                genSingleRings();
            }
            delayTimer = 0f;
            firstDraw = false;
        }

        for(attack elem: attackList) {
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

    public boolean areAllProjectilesOutside(attack attack) {
        Rectangle playfield = new Rectangle(
            0,
            0,
            Gdx.graphics.getWidth() * 2f,
            Gdx.graphics.getHeight() * 2f
        );

        for(Sprite elem :attack.getRings()){
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

    public void showRings(attack attack){
        for(Sprite elem: attack.getRings()){
            tempSpriteBatch.begin();
            elem.draw(tempSpriteBatch);
            tempSpriteBatch.end();
        }
    }

    public void shootRings(float speed, attack attack){
        for(int i = 0; i < attack.getRings().size(); i++){
            Sprite elem = attack.getRings().get(i);
            Vector2 velocity = attack.getRingVelocities().get(i);

            currentRingCords.set((elem.getX() + elem.getWidth()/2f), (elem.getY() + elem.getHeight()/2f));

            angleCalc.set(currentRingCords.x - getEnemyCords().x, currentRingCords.y - getEnemyCords().y);
            shotAngle = (float)Math.atan2(angleCalc.y, angleCalc.x);
            velocity.set(speed * MathUtils.cos(shotAngle), speed * MathUtils.sin(shotAngle));
        }
    }

    public void updateRings(float deltaTime, attack attack){
        for (int i = 0; i < attack.getRings().size(); i++) {
            Sprite ring = attack.getRings().get(i);
            Vector2 vel = attack.getRingVelocities().get(i);
            ring.translate(vel.x * deltaTime, vel.y * deltaTime);
        }
    }

    public void genRings() {
        attackList.clear();
        for (int i = 0; i <= howmanyrings; i++) {
            attackList.add(new attack());
        }

        for(attack elem: attackList){
            elem.ringAttack(12, 250f, enemyShipSprite, 0);
        }
    }

    public void genSingleRings(){
        if (stopGen) {
            attack atk = new attack();
            atk.ringAttack(12, 250f, enemyShipSprite, offset);
            offset = offset + 10;
            attackList.add(atk);

            if (firstAttack == null) {
                firstAttack = atk;
            }
        }
    }

    public void updateRotation(){
        for(attack elem: attackList){
            degree++;
            elem.rotateAllRings(degree, enemyShipSprite);

            if (degree == 360){
                degree = 0;
            }
        }
    }

    public ArrayList<attack> getAttackList() {
        return attackList;
    }
}

