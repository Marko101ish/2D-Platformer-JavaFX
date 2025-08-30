/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.cameras;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.transform.Translate;

/**
 *
 * @author om180345d
 */
public class Camera extends Group {

    private Translate followTranslation;
    private Node followTarget;
    private final double centerX;
    private final double centerY;
    private double initialOffsetFromTargetX;

    public Camera(int width, int height) {
        this.centerX = width / 2.;
        this.centerY = height / 2.;

        this.followTranslation = new Translate(0.0, 0.0);
        getTransforms().clear();

        getTransforms().add(followTranslation);
    }

    public void startFollowing(Node target) {
        if (target != null) {
            followTarget = target;
            Bounds bounds = followTarget.getBoundsInParent();
            initialOffsetFromTargetX = centerX - bounds.getCenterX();
        }
    }

    public void stopFollowing() {
        followTarget = null;
        followTranslation.setX(followTranslation.getX() - initialOffsetFromTargetX);
        followTranslation.setY(0.0);
    }

    public void update(double deltaTime) {
        if (followTarget != null) {
            Bounds bounds = followTarget.getBoundsInParent();

            double translationX = centerX - bounds.getCenterX();
            double translationY = centerY - bounds.getCenterY();
            followTranslation.setX(translationX);
            followTranslation.setY(translationY);
        }
    }
}
