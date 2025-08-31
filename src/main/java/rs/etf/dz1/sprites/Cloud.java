/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;

/**
 *
 * @author om180345d
 */
public class Cloud extends Sprite {
    private static final double SPEED = 400.0;

    private final double velocity;

    public Cloud() {
        this.velocity = SPEED;

        Circle middleCircle = new Circle(0.6);
        middleCircle.setFill(Color.WHITE);

        Circle leftCircle = new Circle(-0.5, 0.2, 0.4);
        leftCircle.setFill(Color.WHITE);

        Circle rightCircle = new Circle(0.5, 0.2, 0.4);
        rightCircle.setFill(Color.WHITE);

        getChildren().add(middleCircle);
        getChildren().add(leftCircle);
        getChildren().add(rightCircle);

        Scale scale = new Scale(100, 100);
        getTransforms().add(scale);
    }

    @Override
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - velocity * deltaTime);
    }
}
