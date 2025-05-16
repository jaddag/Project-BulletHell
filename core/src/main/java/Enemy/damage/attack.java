package Enemy.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class attack {
    ArrayList<Sprite> rings = new ArrayList<Sprite>();
    Texture attackGridREACT;
    Texture attackGrid;
    Texture attackRing;
    Texture glowShot;
    public attack(){
        this.attackGridREACT = loadAttacks.attackGridREACT;
        this.attackGrid = loadAttacks.attackGrid;
        this.attackRing = loadAttacks.attackRing;
        this.glowShot = loadAttacks.glowShot;

    }

    public void ringAttack(int amountOfRings){
        for(int a = 0; a <= amountOfRings; a++){
            rings.add(new Sprite(attackRing));
        }
    }

}
