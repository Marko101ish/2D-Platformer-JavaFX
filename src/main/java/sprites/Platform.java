package sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import main.Main;

public class Platform extends Sprite {

    public Platform(double width, double height) {
        velocityX = -Main.OBJECT_SPEED;

        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);

    }
}
