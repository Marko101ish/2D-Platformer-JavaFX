package rs.etf.dz1.sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Platform extends Sprite {
    private static final double SPEED = 200.0;

    private double velocity;

    public Platform(double width, double height) {
        velocity = SPEED;

        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);

    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - velocity * deltaTime);
    }
}
