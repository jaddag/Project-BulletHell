package laser;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import GenTexture.generateTexture;

public class getTexture {

    private static Texture sharedTexture;
    Texture placeholder;
    private float red;
    private float green;
    private float blue;

    public getTexture(Color color) {
        if (sharedTexture == null) {
            this.red   = color.r;
            this.green = color.g;
            this.blue  = color.b;
            generateTexture genTexture = new generateTexture();
            laserArrayList lal = new laserArrayList(red,green,blue);
            sharedTexture = genTexture.genTexture(23, 85, 10, lal.getPixels());
        }
        placeholder = sharedTexture;
    }

    public Texture getPlaceholder() {
        return placeholder;
    }
}
