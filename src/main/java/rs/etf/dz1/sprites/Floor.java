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

    public Floor(int floorWidth, int floorHeight, int numRows) {
        final int blockSize = floorHeight;
        Scale scale = new Scale(blockSize, blockSize);// floorBlocks should be square and side equals to the height of the floor
        Translate translate = new Translate(0, -floorHeight / 2.);
        // floor is made up of a number of floorBlocks the following loop only concatenates a big number of blocks to make up a floor

        for (int rowIndex = 0; rowIndex < numRows; ++rowIndex) {
            int i = -4 * floorWidth;
            while (i < 5 * floorWidth + blockSize) {
                FloorBlock floorBlock = new FloorBlock(rowIndex == 0);
                floorBlock.getTransforms().add(new Translate(i, rowIndex * blockSize));
                floorBlock.getTransforms().add(translate);
                floorBlock.getTransforms().add(scale);
                getChildren().add(floorBlock);
                i += blockSize;
            }
        }
    }
}
