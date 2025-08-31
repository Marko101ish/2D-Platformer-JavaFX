/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author om180345d
 */
public class Floor extends Sprite {

    public Floor(int width, int height) {
        Scale scale = new Scale(height, height);// floorBlocks should be square and side equals to the height of the floor
        Translate translate = new Translate(0, -height / 2.);
        // floor is made up of a number of floorBlocks the following loop only concatenates a big number of blocks to make up a floor
        int i = -4 * width;
        while (i < 5 * width + height) {
            FloorBlock floorBlock = new FloorBlock();
            floorBlock.getTransforms().add(new Translate(i, 0));
            floorBlock.getTransforms().add(translate);
            floorBlock.getTransforms().add(scale);
            getChildren().add(floorBlock);
            i += height;
        }
    }
}
