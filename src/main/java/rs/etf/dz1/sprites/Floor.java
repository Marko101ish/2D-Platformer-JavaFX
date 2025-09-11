/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.utils.TimeHelper;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author om180345d
 */
public class Floor extends Sprite {
    private int numBlocks;
    private int blockSize;

    private final FloorBlock[] floorBlocks;
    private final List<Double> holes;

    // This is expressed in blocks
    private int holeSize = 5;
    public Floor(int floorStart, int floorEnd, int blockSize) {
        this.holes = new ArrayList<>();
        this.blockSize = blockSize;

        // This will get scaled by blockSize as well
        int floorHeight = 8;
        Scale scale = new Scale(blockSize, blockSize);
        Translate translate = new Translate(0, Main.WINDOW_HEIGHT - blockSize / 2.);

        // +1 to ensure full coverage
        this.numBlocks = (floorEnd - floorStart) / blockSize + 1;
        this.holes.add((double) numBlocks - 1);

        floorBlocks = new FloorBlock[numBlocks];

        // floor is made up of a number of floorBlocks the following loop
        // concatenates a big number of blocks to make up a floor
        for (int blockIndex = floorStart; blockIndex < numBlocks; ++blockIndex) {
            FloorBlock floorBlock = new FloorBlock((int) floorHeight);
            floorBlock.getTransforms().add(new Translate(blockIndex * blockSize, 0));
            floorBlock.getTransforms().add(translate);
            floorBlock.getTransforms().add(scale);
            getChildren().add(floorBlock);
            floorBlocks[blockIndex] = floorBlock;
            floorBlock.initOldBounds();
        }
    }

    @Override
    public void update() {
        super.update();

        for (int holeIndex = 0; holeIndex < holes.size(); ++holeIndex) {
            int oldHoleCol = holes.get(holeIndex).intValue();
            holes.set(holeIndex, holes.get(holeIndex) - (100.0 / blockSize * TimeHelper.getDeltaTime()));
            int currHoleCol = holes.get(holeIndex).intValue();

            if (oldHoleCol == currHoleCol) {
                continue;
            }
            for (int i = oldHoleCol; i > oldHoleCol - holeSize; --i) {
                if (i < 0) {
                    break;
                }

                floorBlocks[i].setVisible(true);
            }

            for (int j = currHoleCol; j > currHoleCol - holeSize; --j) {
                if (j < 0) {
                    break;
                }

                floorBlocks[j].setVisible(false);
            }

            if (currHoleCol < 0) {
                this.holes.clear();
                this.holes.add((double) numBlocks - 1);
            }
        }
    }

    public FloorBlock[] getFloorBlocks() {
        return floorBlocks;
    }

    public CollisionResult checkForGround(Sprite sprite) {
        for (FloorBlock floorBlock : floorBlocks) {
            if (!floorBlock.isVisible()) {
                continue;
            }

            CollisionResult collisionWithFloor = CollisionHelper.checkCollision(sprite, floorBlock);
            if (collisionWithFloor.inCollision) {
                return collisionWithFloor;
            }
        }

        return null;
    }
}
