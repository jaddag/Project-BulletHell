›package ShowBounds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class showBounds {
    ShapeRenderer shapeRenderer;
    public showBounds(){
        shapeRenderer = new ShapeRenderer();
    }

    public void showB(CameraClass.camera cam, Rectangle rect) {
        shapeRenderer.setProjectionMatrix(cam.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
    }
}
