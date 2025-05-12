package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class drawHUD {
    private float screenW;
    private float screenH;

    private OrthographicCamera camera;
    private Viewport hudViewport;
    private SpriteBatch hudBatch;
    private BitmapFont font;

    private Vector2 touchPos;
    private String dev;
    private Sprite shipSprite;
    private boolean enableInput;
    private joyStick js;
    private HUD.button button;
    private float shipSpeed;
    private float size;
    private boolean enableDevStats;

    public drawHUD(boolean enableDevStats, boolean enableInput, Vector2 joyStickCords, Vector2 buttonCords){
        screenH = Gdx.graphics.getHeight();
        screenW = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        hudViewport = new FitViewport(screenW, screenH, camera);

        size = 2f;

        js = new joyStick(150, joyStickCords);
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
        updateJoyStick();
        js.draw();
        button.draw();

    }

    private void updateJoyStick(){
        if(!enableInput) return;

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            hudViewport.unproject(touchPos);
            js.moveJoyStick(shipSprite, shipSpeed, touchPos);
        }else{
            js.reset();
        }
    }

    private void updateButton(){
        //later implementation of button function
    }

    private void devText(){
        if(!enableDevStats) return;
        hudBatch.begin();

        font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " SR: " + screenW + " x " + screenH, 20, screenH - 10*size);
        font.draw(hudBatch, "PosX: " + (shipSprite.getX() + (shipSprite.getWidth() / 2)) + " PoxY: " + (shipSprite.getY() + (shipSprite.getHeight() / 2)), 20, screenH - 30*size);
        font.draw(hudBatch, "dev Options: " + dev, 20, screenH - 50*size);

        hudBatch.end();
    }

    public void devConsole(String dev){
        this.dev = dev;
    }

    public void setShipSprite(Sprite ShipSprite){
        this.shipSprite = ShipSprite;
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
