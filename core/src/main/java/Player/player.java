package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class player implements Disposable {

    Texture shipTexture;
    Sprite shipSprite;

    public player(){

        shipTexture = new Texture("Player/ship.png");
        shipSprite = new Sprite(shipTexture);
        shipSprite.setCenter(1600 , 300);
        shipSprite.setSize((float)Gdx.graphics.getWidth()/10, (float)Gdx.graphics.getWidth()/10);

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
}
