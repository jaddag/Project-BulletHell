package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;
import Player.player;

public class joyStick {
    player player;
    Rectangle touchArea;
    Vector2 shipCords;
    ShapeRenderer shapeRendererJoystick;
    Vector2 bigCircleCords;
    Vector2 smallCircleCords;
    Vector2 resetCords;
    int radiusBigCircle;
    int radiusSmallCircle;
    Vector2 direction;

    boolean inputJoyStick;
    boolean touchingJoyStick;
    boolean firstTouch;


    public joyStick(int buttonSize, Vector2 cords, player player){
        this.player = player;

        shapeRendererJoystick = new ShapeRenderer();
        resetCords = new Vector2(cords);

        bigCircleCords = new Vector2(cords);
        smallCircleCords = new Vector2(cords);

        firstTouch = true;

        radiusBigCircle = buttonSize;
        radiusSmallCircle = (int)(radiusBigCircle * 0.3f);
        touchArea = new Rectangle(0,0, Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight());

    }

    public void moveJoyStick (float speed, Vector2 touchPos) {
        if (!Gdx.input.isTouched() || !touchArea.contains(touchPos)) {
            inputJoyStick = false;
            return;
        }

        if (Gdx.input.isTouched()) {
            if (touchArea.contains(touchPos)) {
                touchingJoyStick = touchPos.dst(bigCircleCords) <= radiusBigCircle;

                inputJoyStick = true;
            } else {
                inputJoyStick = false;
            }
        } else {
            inputJoyStick = false;
        }


        if (inputJoyStick) {

            smallCircleCords.set(touchPos);

            if (firstTouch) {
                bigCircleCords.set(touchPos);
                smallCircleCords.set(bigCircleCords);
                firstTouch = false;
                return;
            }
        }


        if (touchingJoyStick) {
            smallCircleCords.set(touchPos);

            //Wenn man außerhalb des Joysticks geht
        } else {
            float angle = MathUtils.atan2(touchPos.y - bigCircleCords.y, touchPos.x - bigCircleCords.x);

            smallCircleCords.x = bigCircleCords.x + MathUtils.cos(angle) * radiusBigCircle;
            smallCircleCords.y = bigCircleCords.y + MathUtils.sin(angle) * radiusBigCircle;
        }


            //Führt Animation aus je nach Direction
            //schiff cords
            direction = new Vector2(smallCircleCords).sub(bigCircleCords);
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                if (direction.x > 0) {
                    player.lookRight();
                } else {
                    player.lookLeft();
                }
//            } else {
//                if (direction.y > 0) {
//
//                } else {
//
//                }
            }

        player.getSprite().translateX((smallCircleCords.x - bigCircleCords.x)/radiusBigCircle*speed);
        player.getSprite().translateY((smallCircleCords.y-bigCircleCords.y)/radiusBigCircle*speed);

    }

    public void draw(){

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //Großer Kreis

        shapeRendererJoystick.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererJoystick.setColor(new Color(80/255f,80/255f,80/255f,0.8f));
        shapeRendererJoystick.circle(bigCircleCords.x, bigCircleCords.y, radiusBigCircle);

//        //Kleiner Kreis
        shapeRendererJoystick.setColor(Color.WHITE);
        shapeRendererJoystick.circle(smallCircleCords.x,smallCircleCords.y,radiusSmallCircle);

        shapeRendererJoystick.end();
    }

    public boolean getInput(){
        return inputJoyStick;
    }

    public boolean getFirstTouch(){
        return firstTouch;
    }

    public void reset(){
        inputJoyStick = false;
        firstTouch = true;
        bigCircleCords.set(resetCords);
        smallCircleCords.set(resetCords);

    }

}
