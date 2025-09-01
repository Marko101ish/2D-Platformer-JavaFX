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
    private boolean focused = false;

    public Camera(int width, int height) {
        this.centerX = width / 2.;
        this.centerY = height / 2.;

        this.followTranslation = new Translate(0.0, 0.0);
        getTransforms().clear();

        getTransforms().add(followTranslation);
    }

    public void setTarget(Node target) {
        this.followTarget = target;
    }

    public void startFollowing() {
        focused = true;
    }

    public void stopFollowing() {
        focused = false;
        followTranslation.setY(0.0);
    }

    public void update(double deltaTime) {
        if (followTarget == null) {
            return;
        }

        if (focused) {
            double translationX = centerX - followTarget.getTranslateX();
            double translationY = centerY - followTarget.getTranslateY();
            followTranslation.setX(translationX);
            followTranslation.setY(translationY);
        }
    }
}
