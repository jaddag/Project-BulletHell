package Enemy.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class attackEnemy {
    ArrayList<Vector2> ringVelocities = new ArrayList<>();

    ArrayList<Sprite> rings = new ArrayList<Sprite>();
    ArrayList<Rectangle> hitBoxes = new ArrayList<>();
    // Store the original spawn positions of the rings
    ArrayList<Vector2> ringStartPositions = new ArrayList<>();
    Texture attackGridREACT;
    Texture attackGrid;
    Texture attackRing;
    Texture glowShot;

    float angle;
    float x;
    float y;
    public attackEnemy(){
        this.attackGridREACT = loadAttacks.attackGridREACT;
        this.attackGrid = loadAttacks.attackGrid;
        this.attackRing = loadAttacks.attackRing;
        this.glowShot = loadAttacks.glowShot;
    }

    public void ringAttack(int amountOfRings, float radius, Sprite sprite, float rotationOffsetDegrees){
        rings.clear();
        ringVelocities.clear();
        hitBoxes.clear();
        ringStartPositions.clear();
        float angleOffsetRad = rotationOffsetDegrees * MathUtils.degreesToRadians;
        for (int i = 0; i < amountOfRings; i++){
            angle = (2 * MathUtils.PI * i) / amountOfRings + angleOffsetRad;

            x = (sprite.getX() + (sprite.getWidth()/2)) + radius * MathUtils.cos(angle);
            y = (sprite.getY() + (sprite.getHeight()/2)) + radius * MathUtils.sin(angle);

            Sprite ring = new Sprite(attackRing);
            ring.setPosition(x - ring.getWidth()/2, y - ring.getHeight()/2);
            rings.add(ring);
            ringVelocities.add(new Vector2());
            hitBoxes.add(getRingBounds(ring));
            // Store the original spawn position of this ring
            ringStartPositions.add(new Vector2(ring.getX(), ring.getY()));
        }
    }

    public void resetRingPositions() {
        for (int i = 0; i < rings.size(); i++) {
            Vector2 originalPos = ringStartPositions.get(i);
            rings.get(i).setPosition(originalPos.x, originalPos.y);
        }
    }

    public Rectangle getRingBounds(Sprite ring){
        float shrinkFactor = 0.5f;
        float newWidth = ring.getWidth() * shrinkFactor;
        float newHeight = ring.getHeight() * shrinkFactor;

        // Get visual center
        float centerX = ring.getX() + ring.getWidth() / 2f;
        float centerY = ring.getY() + ring.getHeight() / 2f;

        // Calculate top-left for centered rectangle
        float centeredX = centerX - newWidth / 2f;
        float centeredY = centerY - newHeight / 2f;

        return new Rectangle(centeredX, centeredY, newWidth, newHeight);
    }

    public void updateHitBoxes() {
        for (int i = 0; i < rings.size(); i++) {
            Sprite ring = rings.get(i);
            hitBoxes.set(i, getRingBounds(ring));
        }
    }

    public ArrayList<Sprite> getRings() {
        return rings;
    }

    public ArrayList<Vector2> getRingVelocities() {
        return ringVelocities;
    }

    public ArrayList<Rectangle> getHitBoxes() {
        return hitBoxes;
    }

    public void rotateAllRings(float degrees, Sprite centerReference) {
        float radians = degrees * MathUtils.degreesToRadians;
        float cx = centerReference.getX() + centerReference.getWidth() / 2f;
        float cy = centerReference.getY() + centerReference.getHeight() / 2f;

        for (int i = 0; i < rings.size(); i++) {
            Vector2 original = ringStartPositions.get(i).cpy();
            float dx = original.x - cx;
            float dy = original.y - cy;

            float rotatedX = dx * MathUtils.cos(radians) - dy * MathUtils.sin(radians);
            float rotatedY = dx * MathUtils.sin(radians) + dy * MathUtils.cos(radians);

            rings.get(i).setPosition(cx + rotatedX, cy + rotatedY);
            Sprite ring = rings.get(i);
            hitBoxes.set(i, getRingBounds(ring));
        }
    }
}
