package camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class camera {

    private OrthographicCamera camera;
    private Sprite sprite;
    private float panning;
    private float zoom;

    public camera(Sprite sprite){

        this.sprite = sprite;
        camera = new OrthographicCamera();
        zoom = 1f;

    }

    public void update(float panning, float zoom){

        camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
        this.zoom = zoom;
        camera.zoom = this.zoom;
        camera.update();

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Vector2 getCameraPos(){
        return new Vector2((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)));
    }

    public void overrideSprite(Sprite sprite){
        this.sprite = sprite;
    }
}
