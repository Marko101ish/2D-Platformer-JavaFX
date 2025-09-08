/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.cameras;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import rs.etf.dz1.main.Main;

/**
 *
 * @author om180345d
 */
public class Camera extends Group {

    private Translate followTranslation;
    private Node followTarget;
    private final double width;
    private final double height;
    private final double centerX;
    private final double centerY;
    private boolean focused = false;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void update() {
        if (followTarget == null) {
            return;
        }

        if (focused) {
            double translationX = centerX - followTarget.getTranslateX();
            double translationY = centerY - followTarget.getTranslateY();
            followTranslation.setX(translationX);
            followTranslation.setY(translationY);

            followTranslation.setX(Math.clamp(followTranslation.getX(), -(Main.PLAYABLE_AREA_MAX_X - width), -Main.PLAYABLE_AREA_MIN_X));
        }
        else {
            // Just making sure the target is always in view
            double translationX = centerX - followTarget.getTranslateX();
            followTranslation.setX(translationX);

            followTranslation.setX(Math.clamp(followTranslation.getX(), -(Main.PLAYABLE_AREA_MAX_X - width), -Main.PLAYABLE_AREA_MIN_X));
        }
    }
}
