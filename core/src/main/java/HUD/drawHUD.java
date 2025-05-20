package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Player.player;

public class drawHUD {
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
    private boolean enableInput;
    private joyStick js1;
    private joyStick js2;
    private HUD.button button;
    private float shipSpeed;
    private float size;
    private boolean enableDevStats;
    ShapeRenderer spr;

    public drawHUD(boolean enableDevStats, boolean enableInput, Vector2 joyStickCords, Vector2 joyStickCords2, player player){
        screenH = Gdx.graphics.getHeight();
        screenW = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        hudViewport = new FitViewport(screenW, screenH, camera);

        size = 2f;

        this.player = player;

        js1 = new joyStick(150, joyStickCords, player, "left");
        js2 = new joyStick(150, joyStickCords2, player, "right");
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
        makeHpBar();

        spr = new ShapeRenderer();
    }

    public void draw(Vector2 touchPos){
        this.touchPos = touchPos;

        devText();
        updateMultiTouch(); // Multitouch methode
        //updateButton();
        js1.draw();
        js2.draw();
//        button.draw();
        devText();
        hpBar();

    }

    public void makeHpBar(){
        player.HealthBar(100);
    }

    public void hpBar(){
        player.render(spr, Gdx.graphics.getWidth()-300f, Gdx.graphics.getHeight()-30f, Gdx.graphics.getWidth()/10f, Gdx.graphics.getWidth()/30f);
    }

    private void updateMultiTouch() {
        if (!enableInput) return;

        boolean joystickHandled = false;
//        boolean buttonPressed = false;

        for (int i = 0; i < 2; i++) { // erkennen von max. 2 Fingern

            if (Gdx.input.isTouched(i)) {
                Vector2 fingerPos = new Vector2(Gdx.input.getX(i), Gdx.input.getY(i));
                hudViewport.unproject(fingerPos);

// prüft Joystick
                if (!joystickHandled && js1.getTouchArea().contains(fingerPos)) {
                    js1.moveJoyStick(shipSpeed, fingerPos);
                    joystickHandled = true;

                }

                if (!joystickHandled && js2.getTouchArea().contains(fingerPos)) {
                    js2.moveJoyStick(shipSpeed, fingerPos);
                    joystickHandled = true;

                }

//                // prüft button
//                if (fingerPos.dst(button.getButtonPos()) <= button.getButtonSize() / 2f) {
//                    buttonPressed = true;
//                }
            }
        }

//        button.setPressed(buttonPressed);

        if (!joystickHandled) {
            js1.reset();
            js2.reset();

//        if ( !Gdx.input.isTouched()){
//            button.update(null);
        }
    }

//    private void updateButton(Vector2 touchPos){
//        button.update(touchPos);
//   }

    private void devText(){
        if(!enableDevStats) return;
        textbatch.begin();

        font.draw(textbatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH - 10*size);
        font.draw(textbatch, "PosX: " + (player.getSprite().getX() + (player.getSprite().getWidth() / 2)) + " PoxY: " + (player.getSprite().getY() + (player.getSprite().getHeight() / 2)), 20, screenH - 30*size);
        font.draw(textbatch, "checkedButton: " + true,20, screenH - 50*size);
        font.draw(textbatch, "dev Options: " + dev, 20, screenH - 90*size);

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
