//package HUD;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//
//public class JoyStick1 {
//    private int radiusSmallCircle;
//    private int radiusBigCircle;
//    private boolean isPressed;
//    private boolean inputJoystick;
//    private Vector2 bigCircleCords;
//    private Vector2 smallCircleCords;
//    private ShapeRenderer shapeRendererJoystick;
//    private Vector2 cameraWeltPosition;
//    private Vector2 direction;
//    private char playerDirection;
//    private Vector2 originalVector;
//    boolean reset;
//
//    Rectangle touchArea;
//
//    public JoyStick1(Vector2 cords){
//        cameraWeltPosition = new Vector2(800,800);
//
//        radiusSmallCircle = 60;
//        radiusBigCircle = 150;
//        originalVector = cords;
//        bigCircleCords = new Vector2(originalVector.x/20*3f,originalVector.y/10*3f);
//        touchArea = new Rectangle((float) Gdx.graphics.getWidth() /4, (float) Gdx.graphics.getHeight() /2, (float) Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight());
//
//        smallCircleCords = bigCircleCords;
//        inputJoystick = false;
//        isPressed = false;
//        shapeRendererJoystick = new ShapeRenderer();
//
//        reset = true;
//
//    }
//
//
//    public void moveJoystick(Vector2 touchPosition, Sprite batch, float speed){
//
//        inputJoystick = touchPosition.dst(bigCircleCords) <= radiusBigCircle;
//
//        if(Gdx.input.justTouched() && inputJoystick) {
//            isPressed = true;
//
//        }else if(!Gdx.input.isTouched()){
//            isPressed = false;
//        }
//
//        if(Gdx.input.isPress() && reset){
//            smallCircleCords = touchPosition;
//            bigCircleCords = touchPosition;
//            reset = false;
//        }
//
//        //Joystick-Logik
//        if(isPressed) {
//
//
//            //Wenn Joystick im großen Kreis gehalten wird
//            if(inputJoystick){
//                smallCircleCords = touchPosition;
//
//                //Wenn man außerhalb des Joysticks geht
//            }else{
//                float angle = MathUtils.atan2(touchPosition.y - bigCircleCords.y,touchPosition.x - bigCircleCords.x);
//
//                smallCircleCords.x = bigCircleCords.x + MathUtils.cos(angle) * radiusBigCircle;
//                smallCircleCords.y = bigCircleCords.y + MathUtils.sin(angle) * radiusBigCircle;
//            }
//
//            //Führt Animation aus je nach Direction
//            direction = new Vector2(smallCircleCords).sub(bigCircleCords);
//            if (Math.abs(direction.x) > Math.abs(direction.y)) {
//                if (direction.x > 0) {
//
//                } else {
//
//                }
//            } else {
//                if (direction.y > 0) {
//
//                } else {
//
//                }
//            }
//
//            //Wenn Joystick losgelassen wurde
//        }else{
//
//            reset = true;
//
//
//            bigCircleCords = new Vector2(originalVector.x/20*3f,originalVector.y/10*3f);
//            smallCircleCords = bigCircleCords;
//
//        }
//
//
//        //Bewegung
//        batch.translateX((smallCircleCords.x - bigCircleCords.x)/radiusBigCircle*speed);
//        batch.translateY((smallCircleCords.y-bigCircleCords.y)/radiusBigCircle*speed);
//
//    }
//    public void draw(){
//
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        //Großer Kreis
//        shapeRendererJoystick.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRendererJoystick.setColor(new Color(80/255f,80/255f,80/255f,0.8f));
//        shapeRendererJoystick.circle(bigCircleCords.x, bigCircleCords.y, radiusBigCircle);
//
//        //Kleiner Kreis
//        shapeRendererJoystick.setColor(Color.WHITE);
//        shapeRendererJoystick.circle(smallCircleCords.x,smallCircleCords.y,radiusSmallCircle);
//        shapeRendererJoystick.end();
//    }
//
//    public Vector2 getCameraWeltPosition(){
//        return cameraWeltPosition;
//    }
//
//}
