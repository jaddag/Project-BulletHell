package ArrayListDraw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import Background.planetGen;

public class planet {

    ArrayListDraw.planetGen pl = new ArrayListDraw.planetGen();

    String name;

    Texture texture;
    Sprite sprite;
    Rectangle bounds;

    public planet(){

    }

    public planet(int pixelSize) {
        texture = pl.getPlanetTexture(this.name, pixelSize);
        sprite = new Sprite(texture);
    }

    protected ArrayList<Color> pixels = new ArrayList<>();

    public ArrayList<Color> getArrayList(){
        return pixels;
    }


    public void getBounds() {

    }

    public Sprite getSprite(){
        return sprite;
    }
}
