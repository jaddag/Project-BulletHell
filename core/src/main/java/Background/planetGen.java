package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import ArrayListDraw.*;

public class planetGen {

    int gridSize;
    int pixelSize;
    int arrCount;

    public Texture Earth(int pixelSize){

        gridSize = 9;

        arrCount = -1;

        this.pixelSize = pixelSize;

        earthArrayList eal = new earthArrayList();
        eal.genEarthArrayList();

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int row = 0; row < gridSize; row++){
            for (int column = 0; column < gridSize; column++){
                arrCount++;


                pixmap.setColor(eal.pixels.get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);

            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

}
