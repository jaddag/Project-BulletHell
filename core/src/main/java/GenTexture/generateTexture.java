package GenTexture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class generateTexture {

    public Texture genTexture(int gridSizeX, int gridSizeY, int pixelSize, ArrayList<Color> arrayList){
        int arrCount = -1;

        Pixmap pixmap = new Pixmap(gridSizeX * pixelSize, gridSizeY * pixelSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSizeY; column++ ){
            for (int row = 0; row < gridSizeX; row++){
                arrCount++;

                pixmap.setColor(arrayList.get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);
            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

    public Pixmap genPixmap(int gridSizeX, int gridSizeY, int pixelSize, ArrayList<Color> arrayList){
        int arrCount = -1;

        Pixmap pixmap = new Pixmap(gridSizeX * pixelSize, gridSizeY * pixelSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSizeY; column++ ) {
            for (int row = 0; row < gridSizeX; row++) {
                arrCount++;

                pixmap.setColor(arrayList.get(arrCount));
                pixmap.fillRectangle(pixelSize * row, pixelSize * column, pixelSize, pixelSize);
            }
        }

        return pixmap;
    }

    public Texture genTexture(int gridSize, int pixelSize, ArrayList<Color> arrayList){
        int arrCount = -1;

        Pixmap pixmap = new Pixmap(gridSize * pixelSize, gridSize * pixelSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSize; column++ ){
            for (int row = 0; row < gridSize; row++){
                arrCount++;

                pixmap.setColor(arrayList.get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);
            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

    public Pixmap genPixmap(int gridSize, int pixelSize, ArrayList<Color> arrayList){
        int arrCount = -1;

        Pixmap pixmap = new Pixmap(gridSize * pixelSize, gridSize * pixelSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSize; column++ ){
            for (int row = 0; row < gridSize; row++){
                arrCount++;

                pixmap.setColor(arrayList.get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);
            }
        }

        return pixmap;
    }
}
