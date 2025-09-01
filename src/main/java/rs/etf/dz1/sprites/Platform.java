package rs.etf.dz1.sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Platform extends Sprite {
    private static final double SPEED = 100.0;

    public Platform(double width, double height) {
        velocityX = -SPEED;

        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);

    }
}
