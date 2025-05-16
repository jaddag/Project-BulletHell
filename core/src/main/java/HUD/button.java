package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class button {
    Stage stage;
    Skin skin;
    TextButton myButton;
    Vector2 buttonPos;
    int buttonSize;

    ShapeRenderer shapeRendererButton;

    boolean isPressed = false;

    public button(int buttonSize, Vector2 buttonPos) {
        this.buttonSize = buttonSize;
        this.buttonPos = buttonPos;
        this.shapeRendererButton = new ShapeRenderer();

    }

    public void update(Vector2 touch){



            if (touch == null) {
                isPressed = false;
                return;
            }
            boolean isTouching = Gdx.input.isTouched();
            if (isTouching){
                if (!isPressed && touch.dst(buttonPos) <= buttonSize / 2f) {
                    isPressed = true;
                }
        }else{
            isPressed = false;
        }

       return isPressed;
    }


    public boolean isPressed(){
        return isPressed;
   }

    public void draw() {
        //hallo
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //Großer Kreis

        shapeRendererButton.begin(ShapeRenderer.ShapeType.Filled);

        shapeRendererButton.setColor(new Color(80/255f,80/255f,80/255f,0.8f));
        shapeRendererButton.circle(buttonPos.x, buttonPos.y, buttonSize);

        shapeRendererButton.end();
    }

 }
