package MainMethod;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.projectbullethell.loadingScreen;

public class bulletHellMain extends Game {

    public SpriteBatch batch;
    public AssetManager assetManager;

    boolean genEverytime;

    loadingScreen ls;

    @Override
    public void create() {
        batch = new SpriteBatch();
        genEverytime = false;
        ls = new loadingScreen(this, genEverytime);
        assetManager = new AssetManager(fileName -> Gdx.files.local(fileName));
        this.setScreen(ls);
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        super.dispose();
        if (batch != null) batch.dispose();
        if(genEverytime){
            ls.disposeTextures();
        }
    }
}
