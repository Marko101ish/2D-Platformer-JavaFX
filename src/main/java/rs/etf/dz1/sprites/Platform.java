package rs.etf.dz1.sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Platform extends Sprite {
    private double velocity;

    public Platform(double width, double height, double velocity) {
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);

        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    @Override
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - velocity * deltaTime);
    }
}
