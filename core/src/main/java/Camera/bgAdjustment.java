package Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class bgAdjustment{
    camera camera;
    Vector2 camCords;
    float screenW;
    float screenH;
    boolean camL;
    boolean bcR;
    boolean bcL;
    boolean leftBorderR;
    boolean rightBorderR;
    boolean leftBordercamL;
    boolean rightBordercamL;
    float rightBorderBGR;
    float leftBorderBGR;
    float leftBorderBGL;
    float rightBorderBGL;
    float instanceR;
    float instanceL;
    float rightBorder;
    float leftBorder;

    public bgAdjustment(camera camera, float scrollrate){
        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        leftBorderBGR = 0f;
        leftBorderBGL = 0f;

        instanceR = 0f;
        instanceL = 1f;

        this.camera = camera;
        camCords = this.camera.getSpritePosition();
        bcR = false;
        bcL =true;
        leftBorderR = false;
        rightBorderR = true;
        leftBordercamL = false;
        rightBordercamL = true;
    }

    public void updateCam(){
        camCords = this.camera.getSpritePosition();
    }

    public float getInfiniteBGCordsR() {
        rightBorderBGR = leftBorderBGR + screenW;
        float rightBorder = camCords.x + screenW / 2;
        float leftBorder = camCords.x - screenW / 2;
        if (rightBorder > rightBorderBGR && rightBorderR) {
            instanceR+= 2f;
            if(instanceR == 0f) instanceR+= 2f;
            leftBorderR = true;
            rightBorderR = false;
            if(bcR) {
                leftBorderBGR = screenW * instanceR;
                bcL = true;
            }
        } else if (leftBorder < leftBorderBGR && leftBorderR) {
            instanceR-= 2f;
            if(instanceR == 0f) instanceR-= 2f;
            leftBorderR = false;
            rightBorderR = true;
            if(bcR) {
                leftBorderBGR = screenW * instanceR;
                bcL = true;
            }
        } else if(leftBorder == leftBorderBGR){
            instanceR = 0f;
        }
        return leftBorderBGR;
    }

    public float getInfiniteBGCordsL() {
        rightBorderBGL = leftBorderBGL + screenW;
        rightBorder = camCords.x + screenW / 2;
        leftBorder = camCords.x - screenW / 2;
        if (rightBorder > rightBorderBGL && rightBordercamL) {
            instanceL += 2f;
            if (instanceL == 0f) instanceL += 2f;
            leftBordercamL = true;
            rightBordercamL = false;
            if (bcL) {
                leftBorderBGL = screenW * instanceL;
                bcR = true;
            }
        } else if (leftBorder < leftBorderBGL && leftBordercamL) {
            instanceL -= 2f;
            if (instanceL == 0f) instanceL -= 2f;
            leftBordercamL = false;
            rightBordercamL = true;
            if (bcL) {
                leftBorderBGL = screenW * instanceL;
                bcR = true;
            }
        } else if (leftBorder == leftBorderBGL) {
            instanceL = 1f;
        }
        return leftBorderBGL;
    }

    public float getCords(){
        return camCords.x + (screenW / 2f);
    }

    public float testL(){
        return instanceR;
    }

    public boolean testR(){
        return rightBorder > rightBorderBGR;
    }

}
