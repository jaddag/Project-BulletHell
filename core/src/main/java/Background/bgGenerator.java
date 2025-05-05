package Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;
import java.util.WeakHashMap;

import sun.font.GlyphRenderData;

public class bgGenerator {

    float worldWidth = Gdx.graphics.getWidth();
    float worldHeight = Gdx.graphics.getHeight();

    public Texture backgroundGenerator(int seed, int particleAmount, boolean planet){
        Random rand = new Random(seed);

//        int gridSize = size;

        SpriteBatch StarBackgroundBatch = new SpriteBatch();

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        int cellWidth = Gdx.graphics.getWidth()/particleAmount;

        int cellHeight = Gdx.graphics.getHeight()/particleAmount;


        for (int row = 0; row < particleAmount; row++) {
            for (int col = 0; col < particleAmount; col++){
//                int x = row * cellWidth + (int)(Math.random() * cellWidth -4);
//                int y = col * cellHeight + (int)(Math.random() * cellHeight -4);

                int x = row * cellWidth + (int)(rand.nextFloat() * (cellWidth - 4));
                int y = col * cellHeight + (int)(rand.nextFloat() * (cellHeight - 4));

                pixmap.setColor(1f, 1f, 1f, (0.1f +(float)rand.nextFloat() * 0.7f));
                pixmap.fillRectangle(x, y, 8,8);

                if((int)(Math.random() * 1000) == 1 && planet){
                    
                    pixmap.setColor(12/255f, 190/255f, 1f, 1);
                    pixmap.fillRectangle((int)worldWidth/2, (int)worldHeight/2, 40,40);

                }

            }

        }

        Texture backgroundTexture = new Texture(pixmap);
        pixmap.dispose();

        return backgroundTexture;
    }

    private Texture starGen(){

        Pixmap p = new Pixmap(4, 4, Pixmap.Format.RGBA8888);

        p.setColor(1f, 1f, 1f, 0.5f); // RGBA values between 0–1

        p.fillRectangle(0, 0, 4, 4);

        Texture t = new Texture(p);
        p.dispose();


        return t;
    }

}
