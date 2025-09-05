/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Platform;
import rs.etf.dz1.utils.collisions.CollisionHelper;

/**
 *
 * @author om180345d
 */
public class Enemy extends Character {
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    private static final double SPEED = 350.0;

    private Platform platform = null;

    public Enemy() {
        velocityX = -SPEED;

        Polygon head = new Polygon();

        // Head polygon
        //                   1       8              ======== y =  -0.5 - top of the block
        //        2    3                   6    7   ======== y =  -0.1 - middle of the block - 0.2 for legs
        //             4                   5        ======== y =   0.3 - bottom of the block - 0.2 for legs
        // x =  -0.5  -0.4 -0.2     0.2   0.4  0.5
        head.getPoints().addAll(new Double[]{
                -0.3, -0.5,   // 1
                -0.5, -0.1,   // 2
                -0.4, -0.1,   // 3
                -0.4,  0.3,   // 4
                 0.4,  0.3,   // 5
                 0.4, -0.1,   // 6
                 0.5, -0.1,   // 7
                 0.3, -0.5,   // 8
        });
        head.setFill(Color.SADDLEBROWN);

        Rectangle leftLeg = new Rectangle(-0.4, 0.3, 0.2, 0.2);
        leftLeg.setFill(Color.BLACK);

        Rectangle rightLeg = new Rectangle(0.2, 0.3, 0.2, 0.2);
        rightLeg.setFill(Color.BLACK);

        Circle leftEye = new Circle(-0.2f, -0.15, 0.15);
        leftEye.setFill(Color.WHITE);

        Circle rightEye = new Circle(0.2f, -0.15, 0.15);
        rightEye.setFill(Color.WHITE);

        Circle leftPupil = new Circle(-0.275f, -0.15, 0.05);
        leftPupil.setFill(Color.BLACK);

        Circle rightPupil = new Circle(0.125f, -0.15, 0.05);
        rightPupil.setFill(Color.BLACK);

        // y - a bit less from top (which is -0.5)
        // x - a bit outside the head (-0.45, 0.45)
        Rectangle leftBrow = new Rectangle(-0.45, -0.4, 0.45, 0.125);
        // rotates around the pivot in the middle of the bounding box of the shape
        leftBrow.setRotate(20.0f);
        leftBrow.setFill(Color.BLACK);
        Rectangle rightBrow = new Rectangle(0.0, -0.4, 0.45, 0.125);
        rightBrow.setRotate(-20.0f);
        rightBrow.setFill(Color.BLACK);

        Rectangle mouth = new Rectangle(-0.325f, 0.15, 0.65, 0.05);
        leftBrow.setFill(Color.BLACK);

        // tooth width = 0.05
        // Left Tooth polygon
        //              1        y =  0.05
        //        2           3  y =  0.175
        // x =  -0.3 -0.225 -0.15
        Polygon leftTooth = new Polygon();
        leftTooth.getPoints().addAll(new Double[]{
                -0.225,  0.05,   // 1
                  -0.3, 0.175,   // 2
                 -0.15, 0.175,   // 3
        });
        leftTooth.setFill(Color.WHITE);

        // tooth width = 0.05
        // Right Tooth polygon
        //              1        y =  0.05
        //        2           3  y =  0.175
        // x =  -0.15  0.225  0.3
        Polygon rightTooth = new Polygon();
        rightTooth.getPoints().addAll(new Double[]{
                0.225,  0.05,   // 1
                  0.3, 0.175,   // 2
                 0.15, 0.175,   // 3
        });
        rightTooth.setFill(Color.WHITE);

        getChildren().add(head);
        getChildren().add(leftLeg);
        getChildren().add(rightLeg);
        getChildren().add(leftEye);
        getChildren().add(rightEye);
        getChildren().add(leftPupil);
        getChildren().add(rightPupil);
        getChildren().add(leftBrow);
        getChildren().add(rightBrow);
        getChildren().add(mouth);
        getChildren().add(leftTooth);
        getChildren().add(rightTooth);

        TranslateTransition leftPupilTransition = new TranslateTransition(Duration.millis(600),  leftPupil);
        leftPupilTransition.setByX(0.15);
        leftPupilTransition.setAutoReverse(true);
        leftPupilTransition.setCycleCount(Timeline.INDEFINITE);
        leftPupilTransition.play();

        TranslateTransition rightPupilTransition = new TranslateTransition(Duration.millis(600),  rightPupil);
        rightPupilTransition.setByX(0.15);
        rightPupilTransition.setAutoReverse(true);
        rightPupilTransition.setCycleCount(Timeline.INDEFINITE);
        rightPupilTransition.play();

        Scale scale = new Scale(ENEMY_WIDTH, ENEMY_HEIGHT);
        getTransforms().add(scale);
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
        setVelocityX(getVelocityX() + platform.getVelocityX());
    }

    @Override
    public void update() {
        super.update();
        if (platform != null) {
            if(!CollisionHelper.containsX(platform, this)) {
                setVelocityX(-getVelocityX() + platform.getVelocityX());
            }
        }
    }

    @Override
    public void die() {
        Main.getInstance().getSoundManager().playEnemyDeathSound();
    }
}
