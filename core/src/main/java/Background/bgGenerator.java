package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import ExceptionFolder.WrongInputEE;

public class bgGenerator {

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
