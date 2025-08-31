/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.net.URL;

/**
 *
 * @author om180345d
 */
public class Bird extends Sprite {
    private final static double FRAME_DURATION = 1000.0 / 9.0; // 9 fps
    private final static double IMAGE_SCALE = 0.4;
    // -1 to flip, 1 to leave it as is
    private final static double FLIP_IMAGE = -1.0;

    private static final double SPEED = 350.0;

    private final double velocity;
    private static ImageView imageView;

    public Bird(Image spriteSheet, Rectangle2D[] spriteBounds) {
        velocity = SPEED;

        ImageView imageView = new ImageView(spriteSheet);
        imageView.setViewport(spriteBounds[0]);

        Timeline timeline = new Timeline();
        ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();
        for (int spriteIndex = 0; spriteIndex <= spriteBounds.length; ++spriteIndex) {
            Duration frameStart = Duration.millis(spriteIndex * FRAME_DURATION);

            final int currentFrameIndex = spriteIndex % spriteBounds.length;
            keyFrames.add(new KeyFrame(frameStart, event -> imageView.setViewport(spriteBounds[currentFrameIndex])));
        }

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        getChildren().add(imageView);
        setTranslateX(100.0);
        getTransforms().add(new Scale(FLIP_IMAGE * IMAGE_SCALE, IMAGE_SCALE));
    }

    @Override
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - velocity * deltaTime);
    }
}
