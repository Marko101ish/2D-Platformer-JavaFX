/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.playerstates;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.util.Duration;
import rs.etf.dz1.sprites.Player;

/**
 *
 * @author om180345d
 */
public class DeadState extends State {

    public DeadState(Player player) {
        super(player);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(300), player);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();

        Bounds playerBounds = player.getBoundsInParent();
        final double playerDeathPosY = playerBounds.getCenterY();

        KeyValue kv0 = new KeyValue(player.translateYProperty(), playerDeathPosY);
        KeyValue kv1 = new KeyValue(player.translateYProperty(), playerDeathPosY - 200.0);
        KeyValue kv2 = new KeyValue(player.translateYProperty(), playerDeathPosY + 400.0);

        Duration dur0 = Duration.millis(0);
        Duration dur1 = Duration.millis(300);
        Duration dur2 = Duration.millis(1200);

        KeyFrame kf0 = new KeyFrame(dur0, kv0);
        KeyFrame kf1 = new KeyFrame(dur1, kv1);
        KeyFrame kf2 = new KeyFrame(dur2, kv2);

        Timeline animTimeline = new Timeline(kf0, kf1, kf2);
        animTimeline.play();
    }
}
