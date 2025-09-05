/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.collectibles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.characters.Character;
import rs.etf.dz1.utils.SpriteSheet;

/**
 *
 * @author om180345d
 */
public class Coin extends Collectible {
    private final static double FRAME_DURATION = 1000.0 / 9.0; // 9 fps
    private final static double IMAGE_SCALE = 0.25;
    private final static double GROUND_OFFSET = 250.0;

    private static final double SPEED = 150.0;
    private final int coinValue;

    public Coin(SpriteSheet spriteSheet, int coinValue) {
        velocityX = -SPEED;
        this.coinValue = coinValue;

        Rectangle2D[] spriteFrames = spriteSheet.getFrames();

        ImageView imageView = spriteSheet.createImageView();
        Timeline timeline = new Timeline();

        ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();
        for (int spriteIndex = 0; spriteIndex <= spriteFrames.length; ++spriteIndex) {
            Duration frameStart = Duration.millis(spriteIndex * FRAME_DURATION);
            final Rectangle2D currentSprite = spriteFrames[spriteIndex % spriteFrames.length];

            keyFrames.add(new KeyFrame(frameStart, event -> imageView.setViewport(currentSprite)));
        }

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        getChildren().add(imageView);
        getTransforms().add(new Scale(IMAGE_SCALE, IMAGE_SCALE));
        getTransforms().add(new Translate(0.0, -GROUND_OFFSET));
    }

    protected void onCollected(Character character) {
        Main.getInstance().addPoints(coinValue);
    }
}
