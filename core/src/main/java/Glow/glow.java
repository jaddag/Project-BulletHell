package Glow;

import com.badlogic.gdx.graphics.Color;

public class glow {
    glowArrayList gal;
    private int red;
    private int green;
    private int blue;
    public glow(Color color){
        this.red   = (int)(color.r * 255);
        this.green = (int)(color.g * 255);
        this.blue  = (int)(color.b * 255);

        System.out.println("RED: " + red + "GREEN: " + green + "BLUE: " + blue);
    }

}
