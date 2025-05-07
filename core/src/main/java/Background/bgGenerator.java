package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import ExceptionFolder.WrongInputEE;

public class bgGenerator {

    boolean isBright;
    boolean isMid;
    boolean isViolett;

    int rnd;
    float worldWidth = Gdx.graphics.getWidth();
    float worldHeight = Gdx.graphics.getHeight();

    public Texture starGen(int seed, int particleAmount, float alphaValue, float  planetSpawnRate , boolean planet, int maxPixelSize, int minPixelSize) throws Exception{

        if(particleAmount<0 || alphaValue<0 || planetSpawnRate<0 || maxPixelSize<0 || minPixelSize<0 || minPixelSize>maxPixelSize){
            throw new WrongInputEE("Value cant be below 0");
        }

        Random rand = new Random(seed);

//        int gridSize = size;

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        int cellWidth = Gdx.graphics.getWidth()/particleAmount;

        int cellHeight = Gdx.graphics.getHeight()/particleAmount;


        for (int row = 0; row < particleAmount; row++) {
            for (int col = 0; col < particleAmount; col++){
//                int x = row * cellWidth + (int)(Math.random() * cellWidth -4);
//                int y = col * cellHeight + (int)(Math.random() * cellHeight -4);

                int x = Math.min((int)(row * cellWidth + rand.nextFloat() * (cellWidth - 4)), (int)worldWidth - 1);
                int y = Math.min((int)(col * cellHeight + rand.nextFloat() * (cellHeight - 4)), (int)worldHeight - 1);

                pixmap.setColor(1f, 1f, 1f, (0.1f +(float)rand.nextFloat() * alphaValue));
                rnd = rand.nextInt(maxPixelSize - minPixelSize + 1) + minPixelSize;
                pixmap.fillRectangle(x, y, rnd,rnd);


                //make special stars (other color, other style)
                if((int)(rand.nextInt() * estimateChanceInverse(planetSpawnRate/100,particleAmount*particleAmount)) == 1 && planet){

                    int specialX = Math.min((int)(row * cellWidth + rand.nextFloat() * (cellWidth - 4)), (int)worldWidth - 1);
                    int specialY = Math.min((int)(col * cellHeight + rand.nextFloat() * (cellHeight - 4)), (int)worldHeight - 1);

                    pixmap.setColor(12/255f, 190/255f, 1f, 1);
                    pixmap.fillRectangle( specialX, specialY, 40,40);

                }

            }

        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

    public Texture starBackground(int gridSize,  int minPixelSize, int maxPixelSize, int sizeX, int sizeY, float brightnessChance, float isMid, float violettChance){

        Pixmap pixmap = new Pixmap(sizeX , sizeY, Pixmap.Format.RGBA8888);
        pixmap.fill();


        for (int column = 0; column < sizeY; column++ ){
            for (int row = 0; row < sizeX; row++){
                int index = (int)(Math.random() * 20);

                Color color;
                switch (index) {
                    case 0:  color = new Color(78/255f, 41/255f, 110/255f, 1f); break;
                    case 1:  color = new Color(102/255f, 51/255f, 153/255f, 1f); break;
                    case 2:  color = new Color(128/255f, 0/255f, 128/255f, 1f); break;
                    case 3:  color = new Color(186/255f, 85/255f, 211/255f, 1f); break;
                    case 4:  color = new Color(148/255f, 0/255f, 211/255f, 1f); break;
                    case 5:  color = new Color(75/255f, 0/255f, 130/255f, 1f); break;
                    case 6:  color = new Color(0/255f, 0/255f, 128/255f, 1f); break;
                    case 7:  color = new Color(25/255f, 25/255f, 112/255f, 1f); break;
                    case 8:  color = new Color(0/255f, 191/255f, 255/255f, 1f); break;
                    case 9:  color = new Color(30/255f, 144/255f, 255/255f, 1f); break;
                    case 10: color = new Color(70/255f, 130/255f, 180/255f, 1f); break;
                    case 11: color = new Color(72/255f, 61/255f, 139/255f, 1f); break;
                    case 12: color = new Color(255/255f, 105/255f, 180/255f, 1f); break;
                    case 13: color = new Color(255/255f, 20/255f, 147/255f, 1f); break;
                    case 14: color = new Color(255/255f, 69/255f, 0/255f, 1f); break;
                    case 15: color = new Color(255/255f, 140/255f, 0/255f, 1f); break;
                    case 16: color = new Color(255/255f, 165/255f, 0/255f, 1f); break;
                    case 17: color = new Color(255/255f, 215/255f, 0/255f, 1f); break;
                    case 18: color = new Color(255/255f, 255/255f, 224/255f, 1f); break;
                    case 19: color = new Color(255/255f, 250/255f, 250/255f, 1f); break;
                    default: color = new Color(0f, 0f, 0f, 0f); break; // fallback transparent
                }

                isBright = false;
                this.isMid = false;
                isViolett = false;


                switch (index) {
                    case 3:
                    case 8:
                    case 9:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        isBright = true;
                        break;
                }

                switch (index) {
                    case 1:
                    case 2:
                    case 10:
                    case 11:
                        this.isMid = true;
                        break;
                }

                switch (index) {
                    case 0:
                    case 4:
                    case 5:
                    case 7:
                        isViolett = true;
                        break;
                }

                if (index == 6 || (isBright && Math.random() < brightnessChance)) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else if (this.isMid && Math.random() < isMid) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else if (isViolett && Math.random() < violettChance) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else {
                    color = new Color(0/255f, 0/255f, 128/255f, 1f);
                    pixmap.fillRectangle(gridSize * row, gridSize * column, gridSize, gridSize);
                }

            }
        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;

    }

    private Texture planet(){

        Pixmap p = new Pixmap(4, 4, Pixmap.Format.RGBA8888);

        p.setColor(1f, 1f, 1f, 0.5f); // RGBA values between 0–1

        p.fillRectangle(0, 0, 4, 4);

        Texture t = new Texture(p);
        p.dispose();


        return t;
    }

    private static double estimateChanceInverse(double probability, int trials) {
        return 1.0 / (1.0 - Math.pow(1.0 - probability, 1.0 / trials));
    }

//    private Texture planetGen(){
//
//
//
//    }

}
