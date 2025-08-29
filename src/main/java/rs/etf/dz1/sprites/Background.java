/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author om180345d
 */
public class Background extends Sprite {

    public Background(int width, int height) {
        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTBLUE), new Stop(1, Color.DEEPSKYBLUE)};
        LinearGradient gradient = new LinearGradient(0, 0, 0, height, false, CycleMethod.NO_CYCLE, stops);

        Rectangle bg = new Rectangle(0, 0, width, height);
        bg.setFill(gradient);
        getChildren().add(bg);
    }
}
