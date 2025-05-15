package Camera;

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
    float worldBorderRight;
    float worldBorderLeft;
    float worldBorderTop;
    float worldBorderBottom;

    boolean testTouchingRight;
    boolean testTouchingLeft;
    boolean testTouchingTop;
    boolean testTouchingBottom;

    public camera(Sprite sprite){
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        worldBorderRight = screenW * 2;
        worldBorderLeft = 0;
        worldBorderBottom = 0;
        worldBorderTop = screenH * 2;

        camPos = new Vector3(screenW/2, screenH/2 , 0f);
        camPos2 = new Vector2(screenW/2, screenH/2);

        this.sprite = sprite;
        camera = new OrthographicCamera();
        zoom = 1f;

    }

//    public void update1(float panning, float zoom){
//
//        if(getCameraBorderTop() > worldBorderTop){
//            camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
//            setTrue();
//            testTouchingTop = false;
//        } else if (getCameraBorderBottom() < worldBorderBottom){
//            camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
//            setTrue();
//            testTouchingBottom = false;
//        } else if (getCameraBorderRight() > worldBorderRight){
//            camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
//            setTrue();
//            testTouchingRight = false;
//        } else if (getCameraBorderLeft() < worldBorderLeft){
//            camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
//            setTrue();
//            testTouchingLeft = false;
//        } else{
//            camera.position.set((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)), 0);
//            setTrue();
//        }
//        //        camera.position.set(camPos);
//        this.zoom = zoom;
//        camera.zoom = this.zoom;
//        camera.update();
//
//    }

    public void update(float panning, float zoom){
        float targetX = sprite.getX() + sprite.getWidth()/2f;
        float targetY = sprite.getY() + sprite.getHeight()/2f;

        if (getCameraBorderTop() > worldBorderTop) {
            testTouchingTop = false;
            targetY = Math.min(targetY, worldBorderTop - camera.viewportHeight / 2f);
        }
        if (getCameraBorderBottom() < worldBorderBottom) {
            testTouchingBottom = false;
            targetY = Math.max(targetY, worldBorderBottom + camera.viewportHeight / 2f);
        }
        if (getCameraBorderRight() > worldBorderRight) {
            testTouchingRight = false;
            targetX = Math.min(targetX, worldBorderRight - camera.viewportWidth / 2f);
        }
        if (getCameraBorderLeft() < worldBorderLeft) {
            testTouchingLeft = false;
            targetX = Math.max(targetX, worldBorderLeft + camera.viewportWidth / 2f);
        }

        camera.position.set(targetX, targetY, 0);
        camera.zoom = zoom;
        camera.update();
    }



    public void setTrue(){
        testTouchingRight = true;
        testTouchingLeft = true;
        testTouchingTop = true;
        testTouchingBottom = true;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Vector2 getCameraPos(){
        return new Vector2(camera.position.x, camera.position.y);
    }

    public Vector2 getSpritePosition(){
        return new Vector2((sprite.getX() + (sprite.getWidth()/2)), (sprite.getY() + (sprite.getHeight()/2)));
//        return camPos2;
    }

    public void overrideSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public float getCameraBorderTop(){
      return getSpritePosition().y + ((screenW/2)*zoom);
    }

    public float getCameraBorderBottom(){
        return getSpritePosition().y - ((screenW/2)*zoom);
    }

    public float getCameraBorderLeft(){
        return getSpritePosition().x - ((screenW/2)*zoom);
    }

    public float getCameraBorderRight(){
        return getSpritePosition().x + ((screenW/2)*zoom);
    }

    public boolean getTop(){
        return testTouchingTop;
    }

    public boolean getBottom(){
        return testTouchingBottom;
    }

    public boolean getLeft(){
        return testTouchingLeft;
    }

    public boolean getRight(){
        return testTouchingRight;
    }
}
