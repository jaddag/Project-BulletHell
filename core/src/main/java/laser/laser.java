package laser;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import laser.getTexture;
public class laser {
    Sprite laserSprite;
    getTexture getTexture;

    public laser(Color color){

        getTexture = new getTexture(color);
    }

    public Sprite newLaser(){

        laserSprite = new Sprite(getTexture.getPlaceholder());
        return laserSprite;
    }

}
