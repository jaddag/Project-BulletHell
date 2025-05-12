package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import Background.spaceBGGen;

public class player implements Disposable {

    Texture shipTexture;
    Sprite shipSprite;
    float sizeX;
    float sizeY;
    spaceBGGen bg;
    playerArrayList pal;
    Rectangle bounds;

    public player(){
        sizeX = (float)Gdx.graphics.getWidth()/10;
        sizeY = (float)Gdx.graphics.getHeight()/10;

        bg = new spaceBGGen();
        pal = new playerArrayList();
        pal.load();
//        shipTexture = bg.genTexture(30, pal.getArrayList());
        shipTexture = new Texture("Player/ship.png");

        shipSprite = new Sprite(shipTexture);

        shipSprite.setSize(sizeX, sizeX);
        shipSprite.setOriginCenter();

        bounds = new Rectangle(shipSprite.getX(), shipSprite.getY(), shipSprite.getWidth(), shipSprite.getHeight());
    }

    public void update(float delta) {
        bounds.setPosition(shipSprite.getX(), shipSprite.getY());
    }


    public void render(SpriteBatch batch) {
        shipSprite.draw(batch);
    }

    public static Rectangle getBounds() {
        return null;
    }



    public Texture getShipTexture(){
        return shipTexture;
    }

    public void setNewTexture(String path) {
        if (shipTexture != null) {
            shipTexture.dispose();
        }
        this.shipTexture = new Texture(path);
        shipSprite.setTexture(shipTexture);
    }

    public Sprite getSprite(){
        return shipSprite;
    }

    @Override
    public void dispose() {
        if (shipTexture != null) {
            shipTexture.dispose();
        }
    }

    public float getSizeX(){
        return sizeX;
    }

    public float getSizeY(){
        return sizeY;
    }
}
