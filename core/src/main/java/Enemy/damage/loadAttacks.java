package Enemy.damage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import MainMethod.bulletHellMain;
public class loadAttacks {

    static Texture attackGrid;
    static Texture attackGridREACT;
    static Texture attackRing;
    static Texture glowShot;

    private final bulletHellMain game;

    public loadAttacks(bulletHellMain game){
        this.game = game;
        AssetManager assetManager = game.assetManager;

        assetManager.load("cache/attackGrid.png", Texture.class);
        assetManager.load("cache/attackGridREACT.png", Texture.class);
        assetManager.load("cache/attackRing.png", Texture.class);
//        assetManager.load("cache/glowShot.png", Texture.class);
        assetManager.finishLoading();

        attackGrid = assetManager.get("cache/attackGrid.png", Texture.class);
        attackGridREACT = assetManager.get("cache/attackGridREACT.png", Texture.class);
        attackRing = assetManager.get("cache/attackRing.png", Texture.class);
//        glowShot = assetManager.get("cache/glowShot.png", Texture.class);
    }

    public Texture getAttackGrid() {
        return attackGrid;
    }

    public Texture getAttackGridREACT() {
        return attackGridREACT;
    }

    public Texture getAttackRing() {
        return attackRing;
    }

    public Texture getGlowShot() {
        return glowShot;
    }
}
