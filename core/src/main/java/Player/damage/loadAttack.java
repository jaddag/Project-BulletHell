package Player.damage;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class loadAttack {

    ArrayList<Color> pixels= new ArrayList<>();
    public loadAttack(float r, float b, float g){
        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));

        pixels.add(new Color(r,b,g,1f));
        pixels.add(new Color(r,b,g,1f));
    }

    public ArrayList<Color> getPixels() {
        return pixels;
    }
}
