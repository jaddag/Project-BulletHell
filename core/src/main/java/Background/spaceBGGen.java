package Background;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import ArrayListDraw.*;

public class spaceBGGen {

    int pixelSize;
    int gridSize;

    int arrCount;

    public Texture getPlanetTexture(String planetName, int pixelSize){

        this.pixelSize = pixelSize;

        if (planetName == "Earth") {
            return Earth();
        } else if (planetName == "Jupiter") {
            return Jupiter();

        } else if (planetName == "Saturn") {
            return Saturn();

        } else if (planetName == "Sun") {
            return Sun();
        } return null;
    }

    private Texture Earth(){

        gridSize = 10;

        earthArrayList eal = new earthArrayList();
        eal.genEarthArrayList();


        return genTexture(gridSize, eal);

    }

    private Texture Jupiter(){

        gridSize = 24;

        jupiterArrayList jal = new jupiterArrayList();
        jal.genJupterArrayList();


        return genTexture(gridSize, jal);
    }

    private Texture Saturn(){

        gridSize = 21;

        saturnArrayList sal = new saturnArrayList();
        sal.genSaturnArrayList();


        return genTexture(gridSize, sal);
    }

    private Texture Sun(){

        gridSize = 19;

        sunArrayList sul = new sunArrayList();
        sul.genSunArrayList();


        return genTexture(gridSize, sul);
    }

    public Texture Nebula(int pixelSize){

        gridSize = 50;

        nebulaArrayList nal = new nebulaArrayList();
        nal.genNebulaArrayList();


        return genTexture(gridSize, nal);

    }



    public Texture genTexture(int gridSize, planet planet){
        arrCount = -1;

        Pixmap pixmap = new Pixmap(gridSize * pixelSize, gridSize * pixelSize, Pixmap.Format.RGBA8888);        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        for (int column = 0; column < gridSize; column++ ){
            for (int row = 0; row < gridSize; row++){
                arrCount++;


                pixmap.setColor(p.getArrayList().get(arrCount));
                pixmap.fillRectangle(pixelSize*row, pixelSize*column, pixelSize, pixelSize);

            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

}




