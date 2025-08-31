/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;

import java.util.Random;

/**
 *
 * @author om180345d
 */
public class FloorBlock extends Sprite {

    public FloorBlock() {
        Polygon foreground = new Polygon();

        // Grass polygon
        //        1                       7  ======== y =  -0.5  - top of the block
        //              3           5        ======== y = -0.25 - quarter from top
        //        2           4           6  ======== y =   0.0 - middle of the block
        //
        // x = -0.5  -0.25   0.0   0.25  0.5
        foreground.getPoints().addAll(new Double[]{
                 -0.5,  -0.5,   // 1
                 -0.5,   0.0,   // 2
                -0.25, -0.25,   // 3
                  0.0,   0.0,   // 4
                 0.25, -0.25,   // 5
                  0.5,   0.0,   // 6
                  0.5,  -0.5,   // 7
        });

        Rectangle background = new Rectangle(-0.5, -0.5, 1, 1);
        Random rand = new Random();
        background.setFill(Color.SIENNA);
        foreground.setFill(Color.LAWNGREEN);
        getChildren().add(background);
        getChildren().add(foreground);
    }

}
