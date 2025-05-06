package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import ArrayListDraw.*;

public class planetGen {

    int gridSize;

    int arrCount;

    public Texture Earth(int pixelSize){

        gridSize = 10   ;

        earthArrayList eal = new earthArrayList();
        eal.genEarthArrayList();


        return genTexture(gridSize, eal, pixelSize);

    }

    public Texture Jupiter(int pixelSize){

        gridSize = 21   ;

        jupiterArrayList jal = new jupiterArrayList();
        jal.genEarthArrayList();


        return genTexture(gridSize, jal, pixelSize);
    }

    public <arrayList> Texture genTexture(int gridSize, planet planet, int pixelSize){
        arrCount = -1;

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSize; column++ ){
            for (int row = 0; row < gridSize; row++){
                arrCount++;


                pixmap.setColor(planet.getArrayList().get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);

            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

}
