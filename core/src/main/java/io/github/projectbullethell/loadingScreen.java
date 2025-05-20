package io.github.projectbullethell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import Background.bgGenerator;
import MainMethod.bulletHellMain;
import Glow.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class loadingScreen extends ApplicationAdapter implements Screen{
    private SpriteBatch batch;
    private Texture image;
    private float alpha;
    private float progress = 0f;
    int screenW;
    int screenH;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    Pixmap pixmap3;
    Pixmap pixmap2;
    Pixmap pixmap1;
    Pixmap star;
    Pixmap alphaBG;

    Pixmap attackGrid;
    Pixmap attackGridREACT;
    Pixmap attackRing;
    Pixmap glowShot;
    bgGenerator bg;
    generateGlowTextures gat;

    Random rand;

    private final boolean genEverytimeFlag;

    boolean firstRender;
    boolean checkAssetsExist;

    bulletHellMain game;
    int size;

    public loadingScreen(bulletHellMain game, boolean genEverytime){
        this.game = game;
        firstRender = true;
        this.genEverytimeFlag = genEverytime;
        this.shapeRenderer = new ShapeRenderer();
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        checkAssetsExist = false;
        alpha = 0f;
        font = new BitmapFont();
        batch = new SpriteBatch();
        image = new Texture("Icon/BulletHellIconStartScreen.png");

        new Thread(() -> {
            loadAssets(); // runs in background
            checkAssetsExist = true;
        }).start();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);


        if (progress >= 1f && checkAssetsExist) {
            game.setScreen(new startScreen(this.game));
        }

        loadScreenIcon();
        drawLoadingBar();
    }

    private boolean checkAssetsExist() {
        return Gdx.files.local("cache/background1.png").exists()
            && Gdx.files.local("cache/background2.png").exists()
            && Gdx.files.local("cache/background3.png").exists()
            && Gdx.files.local("cache/starBackground.png").exists()
            && Gdx.files.local("cache/alphaBG.png").exists();
    }

    public void loadAssets(){
        if(genEverytimeFlag){
            size = 7;
            rand = new Random();
            bg = new bgGenerator();
            gat = new generateGlowTextures();
            try {
                pixmap3 = bg.starGen(rand.nextInt(), 22, 0.2f, 33, false, 5, 3, false);
                PixmapIO.writePNG(Gdx.files.local("cache/background3.png"), pixmap3);
                pixmap3.dispose();

                pixmap2 = bg.starGen(rand.nextInt(), 20, 0.4f, 33, false, 7, 4, false);
                PixmapIO.writePNG(Gdx.files.local("cache/background2.png"), pixmap2);
                pixmap2.dispose();

                pixmap1 = bg.starGen(rand.nextInt() * 1000, 17, 0.6f, 63, true, 9, 5, false);
                PixmapIO.writePNG(Gdx.files.local("cache/background1.png"), pixmap1);
                pixmap1.dispose();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            star = bg.starBackground(2, 2, 4, screenH, screenH, 0.005f, 0.005f, 0.5f, 0.1f);
            PixmapIO.writePNG(Gdx.files.local("cache/starBackground.png"), star);
            star.dispose();

            alphaBG = bg.coloredBackground(screenW, screenH, new Color(0f,0f,0f,0.5f));
            PixmapIO.writePNG(Gdx.files.local("cache/alphaBG.png"), alphaBG);
            alphaBG.dispose();

            attackGrid = gat.glowGridAttack(Color.RED, size);
            PixmapIO.writePNG(Gdx.files.local("cache/attackGrid.png"), attackGrid);
            attackGrid.dispose();

            attackGridREACT = gat.glowGridAttackREACT(Color.RED, size);
            PixmapIO.writePNG(Gdx.files.local("cache/attackGridREACT.png"), attackGridREACT);
            attackGridREACT.dispose();

            attackRing = gat.glowRingAttack(Color.RED, size);
            PixmapIO.writePNG(Gdx.files.local("cache/attackRing.png"), attackRing);
            attackRing.dispose();

//            glowShot = gat.glow(Color.RED, 64, 0.1f);
//            PixmapIO.writePNG(Gdx.files.local("cache/glowShot.png"), glowShot);
//            glowShot.dispose();
        }
    }

    private void loadScreenIcon(){
        float Percent = (Gdx.graphics.getWidth() * 0.10f);
        float xWidth = (Gdx.graphics.getWidth() - Percent) / 2f;
        float yWidth = (Gdx.graphics.getHeight() - Percent) / 2f;

        batch.begin();
        if (alpha < 1f) alpha += 0.010f;
        batch.setColor(1, 1, 1, Math.min(alpha, 1f));
        batch.draw(image, xWidth, yWidth, Percent, Percent);
        font.draw(batch, "Loading Textures", 600, 500);
        font.getData().setScale(10f);
        batch.end();
    }


    private void drawLoadingBar() {
        float barWidth = screenW * 0.6f;
        float barHeight = 20f;
        float x = (screenW - barWidth) / 2f;
        float y = 100f;

        float maxSimulatedProgress = checkAssetsExist ? 1f : 0.7f;
        float speed = checkAssetsExist ? 1.5f : 0.5f; // faster fill: ~2s to 0.7, quick finish

        progress = Math.min(progress + Gdx.graphics.getDeltaTime() * speed, maxSimulatedProgress);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, barWidth, barHeight);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(x, y, barWidth * progress, barHeight);
        shapeRenderer.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }

    public void disposeTextures(){
        Gdx.files.local("cache/background1.png").delete();
        Gdx.files.local("cache/background2.png").delete();
        Gdx.files.local("cache/background3.png").delete();
        Gdx.files.local("cache/starBackground.png").delete();
        Gdx.files.local("cache/alphaBG.png").delete();
        Gdx.files.local("cache/attackGrid.png").delete();
        Gdx.files.local("cache/attackGridREACT.png").delete();
        Gdx.files.local("cache/attackRing.png").delete();
        Gdx.files.local("cache/glowShot.png").delete();
    }
}
