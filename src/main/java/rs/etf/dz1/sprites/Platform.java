package rs.etf.dz1.sprites;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.Random;

public class Platform extends Sprite {

    private static final double MIN_WIDTH = 75.0;
    private static final double MAX_WIDTH = 250.0;
    private static final double HEIGHT = 20.0;
    private static final double SPEED = 200.0;

    public Platform() {
        Random rand = new Random();
        double width = rand.nextDouble() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;
        Rectangle rect = new Rectangle(width, HEIGHT);
        rect.setFill(Color.SIENNA);
        getChildren().add(rect);
    }

    @Override
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - SPEED * deltaTime);
    }
}
