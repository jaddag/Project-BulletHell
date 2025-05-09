package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import ExceptionFolder.WrongInputEE;

public class bgGenerator {

    float chance;

    Color lastColor;

    boolean boolBrightChance;
    boolean boolBrightViolettChance;
    boolean boolDarkViolettChance;
    boolean boolBlackChance;

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

    public Pixmap starGen(int seed, int particleAmount, float alphaValue, float  planetSpawnRate , boolean planet, int maxPixelSize, int minPixelSize, boolean t) throws Exception{

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

                int index = new Random().nextInt(5); // gives 0–4

                Color color = null;
                switch (index) {
                    case 0:
                        color = new Color(62 / 255f, 32 / 255f, 88 / 255f, 1f); break;  // Deep violet
                    case 1:
                        color = new Color(204 / 255f, 84 / 255f, 144 / 255f, 1f); break;// Bright magenta
                    case 2:
                        color = new Color(0 / 255f, 152 / 255f, 204 / 255f, 1f); break; // Sky blue
                    case 3:
                        color = new Color(204 / 255f, 132 / 255f, 0 / 255f, 1f); break;  // Amber orange
                    case 4:
                        color = new Color(204 / 255f, 204 / 255f, 179 / 255f, 1f); break; // Gold-white
                }

                pixmap.setColor(color);
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

        return pixmap;
    }

    public Pixmap coloredBackground(int sizeX, int sizeY, Color color){
        Pixmap pixmap = new Pixmap(sizeX, sizeY, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        return pixmap;
    }

    public Pixmap starBackground(int gridSize,  int minPixelSize, int maxPixelSize, int sizeX, int sizeY, float BrightChance, float BrightViolettChance, float DarkViolettChance, float BlackChance){

        Pixmap pixmap = new Pixmap(sizeX , sizeY, Pixmap.Format.RGBA8888);
        Color color = new Color();

        for (int column = 0; column < sizeY; column++ ){
            for (int row = 0; row < sizeX; row++){
                int index = (int)(Math.random() * 22);

                switch (index) {
                    case 0:  color = new Color(62/255f, 32/255f, 88/255f, 1f); break;
                    case 1:  color = new Color(81/255f, 40/255f, 122/255f, 1f); break;
                    case 2:  color = new Color(102/255f, 0/255f, 102/255f, 1f); break;
                    case 3:  color = new Color(148/255f, 68/255f, 169/255f, 1f); break;
                    case 4:  color = new Color(118/255f, 0/255f, 169/255f, 1f); break;
                    case 5:  color = new Color(60/255f, 0/255f, 104/255f, 1f); break;
                    case 6:  color = new Color(0f, 0f, 0f, 0f); break; // black
                    case 7:  color = new Color(20/255f, 20/255f, 90/255f, 1f); break;
                    case 8:  color = new Color(0/255f, 152/255f, 204/255f, 1f); break;
                    case 9:  color = new Color(24/255f, 115/255f, 204/255f, 1f); break;
                    case 10: color = new Color(56/255f, 104/255f, 144/255f, 1f); break;
                    case 11: color = new Color(58/255f, 48/255f, 111/255f, 1f); break;
                    case 12: color = new Color(204/255f, 84/255f, 144/255f, 1f); break;
                    case 13: color = new Color(204/255f, 16/255f, 117/255f, 1f); break;
                    case 14: color = new Color(204/255f, 55/255f, 0/255f, 1f); break;
                    case 15: color = new Color(204/255f, 112/255f, 0/255f, 1f); break;
                    case 16: color = new Color(204/255f, 132/255f, 0/255f, 1f); break;
                    case 17: color = new Color(204/255f, 172/255f, 0/255f, 1f); break;
                    case 18: color = new Color(204/255f, 204/255f, 179/255f, 1f); break;
                    case 19: color = new Color(204/255f, 200/255f, 200/255f, 1f); break;
                    case 21: color = new Color(0/255f, 0/255f, 90/255f, 1f); break; // darkblue
                }

                boolBrightChance = false;
                boolBrightViolettChance = false;
                boolDarkViolettChance = false;
                boolBlackChance = false;

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
                        boolBrightChance = true;
                        break;
                    case 1:
                    case 2:
                    case 10:
                    case 11:
                        this.boolBrightViolettChance = true;
                        break;
                    case 0:
                    case 4:
                    case 5:
                    case 7:
                        boolDarkViolettChance = true;
                        break;
                    case 21:
                        boolBlackChance = true;
                }

                if (index == 6 || (boolBrightChance && Math.random()*10 < BrightChance)) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else if (this.boolBrightViolettChance && Math.random()*10 < BrightViolettChance) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else if (boolDarkViolettChance && Math.random()*10 < DarkViolettChance) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int)(Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                }  else if (boolBlackChance && Math.random()*10 < BlackChance) {
                    pixmap.setColor(color);
                    int rndPixelSize = (int) (Math.random() * (maxPixelSize - minPixelSize + 1)) + minPixelSize;
                    pixmap.fillRectangle(gridSize * row, gridSize * column, rndPixelSize, rndPixelSize);
                } else {
                    color = new Color(0/255f, 0/255f, 128/255f, 1f);
                    pixmap.fillRectangle(gridSize * row, gridSize * column, gridSize, gridSize);
                }

                lastColor = color;
            }
        }

        return pixmap;
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

    private float duplicateChanceDiminish(Color currentColor, Color lastCreatedColor) {
        if (!currentColor.equals(lastCreatedColor)) {
            chance = 100f;
        } else {
            chance -= 20f;
            chance = Math.max(chance, 0f);
        }
        return chance / 100f;
    }


}
