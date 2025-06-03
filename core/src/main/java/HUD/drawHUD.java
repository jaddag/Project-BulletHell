package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Player.player;
import Enemy.enemy;
import CameraClass.camera;
import ShowBounds.showBounds;

public class drawHUD {
    camera gameCamera;
    private float screenW;
    private float screenH;

    private OrthographicCamera camera;
    private Viewport hudViewport;
    private SpriteBatch textbatch;
    private SpriteBatch BarBatch;

    private BitmapFont font;

    private Vector2 touchPos;
    private String dev;
    private player player;
    private enemy enemy;
    private boolean enableInput;
    private joyStick js1;
    private joyStick js2;
    private float shipSpeed;
    private float size;
    private boolean enableDevStats;

    float offset;

    public drawHUD(boolean enableDevStats, boolean enableInput, Vector2 joyStickCords, Vector2 joyStickCords2, player player, enemy enemy){
        screenH = Gdx.graphics.getHeight();
        screenW = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();

        hudViewport = new FitViewport(screenW, screenH, camera);

        size = 0.5f;

        this.player = player;
        this.enemy = enemy;

        js1 = new joyStick(50, joyStickCords, player, "left");
        js2 = new joyStick(50, joyStickCords2, player, "right");
//        button = new button(200, buttonCords);

        this.enableInput = enableInput;
        this.enableDevStats = enableDevStats;

        textbatch = new SpriteBatch();

        BarBatch = new SpriteBatch();

        //Dev HUD
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1);
        font.getData().setScale(size);

        //HpBar
        makeHpBars();

        offset = 50f;
    }

    public void draw(Vector2 touchPos){
        this.touchPos = touchPos;

        devText();
        updateMultiTouch();
        //updateButton();
        js1.draw();
        js2.draw();
//        button.draw();
        devText();
        hpBarPlayer();
        hpBarBoss();

    }

    public void makeHpBars(){
        player.HealthBar(100);
        enemy.HealthBar(500);
    }

    public void hpBarPlayer(){
        player.renderHealthBar(40f, screenH-20f, screenW/10f, screenW/90f);
    }

    public void hpBarBoss(){
        enemy.renderHealthBar(screenW-40f-(screenW/10f), screenH-20f, screenW/10f, screenW/90f);
    }

    private void updateMultiTouch() {
        if (!enableInput) return;

        boolean leftTouched = false;
        boolean rightTouched = false;

        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector2 fingerPos = new Vector2(Gdx.input.getX(i), Gdx.input.getY(i));
                hudViewport.unproject(fingerPos);

                if (js1.getTouchArea().contains(fingerPos)) {
                    js1.moveJoyStick(shipSpeed, fingerPos);
                    leftTouched = true;
                }

                if (js2.getTouchArea().contains(fingerPos)) {
                    js2.moveJoyStick(shipSpeed, fingerPos);
                    rightTouched = true;
                }
            }
        }

        if (!leftTouched) js1.reset();
        if (!rightTouched) js2.reset();
    }

    private void updateMultiTouchAlt() {
        if (!enableInput) return;

        boolean joystickHandled = false;

        for (int i = 0; i < 2; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector2 fingerPos = new Vector2(Gdx.input.getX(i), Gdx.input.getY(i));
                hudViewport.unproject(fingerPos);

                if (js1.getTouchArea().contains(fingerPos)) {
                    js1.moveJoyStick(shipSpeed, fingerPos);
                }

                if (js2.getTouchArea().contains(fingerPos)) {
                    js2.moveJoyStick(shipSpeed, fingerPos);
                }
            }
        }


        if (!joystickHandled) {
//            js1.reset();
//            js2.reset();
        }
    }

//    private void updateButton(Vector2 touchPos){
//        button.update(touchPos);
//   }

    private void devText(){
        if(!enableDevStats) return;
        textbatch.begin();

        font.draw(textbatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH - (screenH / 15f)*1 - offset);
        font.draw(textbatch, "PosX: " + player.getShipCenter().x + " PoxY: " + player.getShipCenter().y, 20, screenH - (screenH / 15f)*1.5f - offset);
        font.draw(textbatch, "checkedButton: " + true,20, screenH - (screenH / 15f)*2 - offset);
        font.draw(textbatch, "dev Options: " + dev, 20, screenH - (screenH / 15f)*2.5f - offset);

        textbatch.end();
    }

    public void devConsole(String dev){
        this.dev = dev;
    }

    public void setPlayer(player player){
        this.player = player;
    }

    public void setShipSpeed(float speed){
        this.shipSpeed = speed;
    }

    public Viewport getHudViewport(){
        return hudViewport;
    }

    public SpriteBatch getSpriteBatch(){
        return textbatch;
    }
}
