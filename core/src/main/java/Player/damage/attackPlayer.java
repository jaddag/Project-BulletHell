package Player.damage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

import GenTexture.generateTexture;

public class attackPlayer {
    loadAttack ldatt;
    generateTexture genTexture;
    Texture shotTexture;
    private float red;
    private float green;
    private float blue;
    ArrayList<Sprite> shots;
    HashMap<Sprite, Vector2> velocities;
    public attackPlayer(Color color){
        genTexture = new generateTexture();
        this.red = color.r;
        this.green = color.g;
        this.blue = color.b;
        ldatt = new loadAttack(red, green, blue);

        shotTexture = genTexture.genTexture(8,2, 4, ldatt.getPixels());

        shots = new ArrayList<>();
        velocities = new HashMap<>();
    }

    public void genShots(Vector2 velocity, Vector2 shipPosition, float degrees) {
        Sprite shot = new Sprite(shotTexture);
        shot.setOriginCenter();
        shot.setCenter(shipPosition.x, shipPosition.y);
        shot.setRotation(degrees);
        velocities.put(shot, velocity);
    }

    public void deleteShot(Sprite shot) {
        shots.remove(shot);
        velocities.remove(shot);
    }

    public HashMap<Sprite, Vector2> getVelocities() {
        return velocities;
    }
}
