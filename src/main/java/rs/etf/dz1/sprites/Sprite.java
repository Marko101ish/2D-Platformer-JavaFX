/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import rs.etf.dz1.utils.IUpdatable;
import rs.etf.dz1.utils.TimeHelper;

/**
 *
 * @author om180345d
 */
public abstract class Sprite extends Group implements IUpdatable {
    protected double velocityX;
    protected double velocityY;
    protected Bounds oldBounds;

    private boolean facingRight = true;

    public Sprite() {
        oldBounds = getBoundsInParent();
    }

    public Sprite(boolean flipSpriteX) {
        this();
        if (flipSpriteX) {
            facingRight = false;
            setScaleX(-getScaleX());
        }
    }

    public Bounds getOldBounds() {
        return oldBounds;
    }

    public void initOldBounds() {
        oldBounds = getBoundsInParent();
    }

    public double getVelocityX() {
        return velocityX;
    }
    public double getVelocityY() {
        return velocityY;
    }
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isFacingLeft() {
        return !facingRight;
    }

    public boolean isGoingLeft() {
        return velocityX < 0;
    }

    public boolean isGoingRight() {
        return velocityX > 0;
    }

    public boolean isGoingUp() {
        return velocityY < 0;
    }

    public boolean isGoingDown() {
        return velocityY > 0;
    }

    public void faceRight() {
        if (isFacingLeft()) {
            setScaleX(-getScaleX());
        }
        facingRight = true;
    }

    public void faceLeft() {
        if (isFacingRight()) {
            setScaleX(-getScaleX());
        }
        facingRight = false;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    // method should be redefined and called in Main.update() method to update the object status
    public void update() {
        oldBounds = getBoundsInParent();
        final double deltaTime = TimeHelper.getDeltaTime();
        setTranslateX(getTranslateX() + getVelocityX() * deltaTime);
        setTranslateY(getTranslateY() + getVelocityY() * deltaTime);
    }

    // For managed sprite this is called after they've spawned
    // can be used to init animations that depend on the spawn point
    public void onInit() {
    }
}
