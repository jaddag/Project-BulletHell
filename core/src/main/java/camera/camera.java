package camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class camera {
    float screenW;
    float screenH;
    Vector3 camPos;
    Vector2 camPos2;
    private OrthographicCamera camera;
    private Sprite sprite;
    private float panning;
    private float zoom;

    public camera(Sprite sprite){
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        camPos = new Vector3(screenW/2, screenH/2 , 0f);
        camPos2 = new Vector2(screenW/2, screenH/2);

        this.sprite = sprite;
        camera = new OrthographicCamera();
        zoom = 1f;

    }

    public void update(float panning, float zoom){

//        camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
        camera.position.set(camPos);
        this.zoom = zoom;
        camera.zoom = this.zoom;
        camera.update();

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Vector2 getCameraPos(){
//        return new Vector2((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)));
        return camPos2;
    }

    public void overrideSprite(Sprite sprite){
        this.sprite = sprite;
    }
}
