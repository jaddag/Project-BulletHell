package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Player.player;

public class drawHUD {
    private float screenW;
    private float screenH;

    private OrthographicCamera camera;
    private Viewport hudViewport;
    private SpriteBatch hudBatch;
    private BitmapFont font;

    private Vector2 touchPos;
    private String dev;
    private player player;
    private boolean enableInput;
    private joyStick js;
    private HUD.button button;
    private float shipSpeed;
    private float size;
    private boolean enableDevStats;

    public drawHUD(boolean enableDevStats, boolean enableInput, Vector2 joyStickCords, Vector2 buttonCords, player player){
        screenH = Gdx.graphics.getHeight();
        screenW = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        hudViewport = new FitViewport(screenW, screenH, camera);

        size = 2f;

        this.player = player;

        js = new joyStick(150, joyStickCords, player);
        button = new button(200, buttonCords);

        this.enableInput = enableInput;
        this.enableDevStats = enableDevStats;

        hudBatch = new SpriteBatch();

        //Dev HUD
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1);
        font.getData().setScale(size);
    }

    public void draw(Vector2 touchPos){
        this.touchPos = touchPos;

        devText();
        updateJoyStickAndButton(); // Multitouch methode
       // updateButton();
        js.draw();
        button.draw();
        devText();
    }

    private void updateJoyStickAndButton() {
        if (!enableInput)
            return;

        boolean joystickHandled = false;
        boolean buttonHandled = false;

        for (int i = 0; i < 2; i++) { // erkennen von max. 2 Fingern

            if (Gdx.input.isTouched(i)) {
                Vector2 fingerPos = new Vector2(Gdx.input.getX(i), Gdx.input.getY(i));
                hudViewport.unproject(fingerPos);

// prüft Joystick
                if (!joystickHandled && js.getTouchArea().contains(fingerPos)) {
                    js.moveJoyStick(shipSpeed, fingerPos);
                    joystickHandled = true;

                }

                // prüft button
                if (!buttonHandled) {
                    button.update(fingerPos);
                    buttonHandled = true;
                }
            }
        }
        if (!joystickHandled) {
            js.reset();
        }
    }

//    private void updateButton(){
//        button.update();
//   }

    private void devText(){
        if(!enableDevStats) return;
        hudBatch.begin();

        font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH - 10*size);
        font.draw(hudBatch, "PosX: " + (player.getSprite().getX() + (player.getSprite().getWidth() / 2)) + " PoxY: " + (player.getSprite().getY() + (player.getSprite().getHeight() / 2)), 20, screenH - 30*size);
        font.draw(hudBatch, "checkedButton: " + button.isPressed,20, screenH - 50*size);
        font.draw(hudBatch, "dev Options: " + dev, 20, screenH - 90*size);

        hudBatch.end();
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
        return hudBatch;
    }
}
