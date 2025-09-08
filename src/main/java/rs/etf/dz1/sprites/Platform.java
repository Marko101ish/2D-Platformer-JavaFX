package rs.etf.dz1.sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import rs.etf.dz1.main.Main;

public class Platform extends Sprite {

    public Platform(double width, double height) {
        velocityX = -Main.OBJECT_SPEED;

        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);

    }
}
