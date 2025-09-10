/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javafx.util.Duration;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Floor;
import rs.etf.dz1.sprites.FloorBlock;
import rs.etf.dz1.sprites.Platform;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.sprites.characters.playerstates.*;
import rs.etf.dz1.utils.InvulnerabilityType;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author om180345d
 */
public class Player extends Character implements EventHandler<KeyEvent> {

    private static final Paint EYE_COLOR = Color.BLACK;

    private State state;

    private Circle body;
    private Circle eye;

    private boolean onGround = false;
    private boolean onPlatform = false;
    private boolean falling = false;

    public Player() {
        super();
        getChildren().clear();
        body = new Circle(0, 0, 40);
        body.setFill(Color.YELLOW);
        eye = new Circle(15, 0, 10);
        eye.setFill(EYE_COLOR);
        getChildren().add(body);
        getChildren().add(eye);
        state = new IdleState(this);
    }

    public void setFillColor(Color color) {
         body.setFill(color);
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void run() {
        state = new RunState(this);
    }

    public String getState() {
        return state.toString();
    }

    public void jump() {
        state = new JumpState(this);
    }

    public void fall() {
        state = new FallState(this);
    }

    public void stop() {
        if (getVelocityX() != 0) {
            run();
        }
        else {
            state = new IdleState(this);
        }
    }

    @Override
    public void die() {
        Main.getInstance().pauseGame();
        Main.getInstance().getSoundManager().playPlayerDeathSound();
        state = new DeadState(this);
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isOnPlatform() {
        return onPlatform;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isFalling() {
        return falling;
    }

    @Override
    public void onInvulnerabilityGained(InvulnerabilityType type, double duration) {
        if (type == InvulnerabilityType.RESPAWN) {
            KeyValue kv0 = new KeyValue(opacityProperty(), 0);
            KeyValue kv1 = new KeyValue(opacityProperty(), 1);

            Duration dur0 = Duration.millis(0);
            Duration dur1 = Duration.millis(500);

            KeyFrame kf0 = new KeyFrame(dur0, kv0);
            KeyFrame kf1 = new KeyFrame(dur1, kv1);

            Timeline respawnAnimation = new Timeline(kf0, kf1);
            respawnAnimation.setCycleCount(Timeline.INDEFINITE);
            setInvulnerabilityAnimation(respawnAnimation);
        }
        else if (type == InvulnerabilityType.IMMUNITY_COIN) {
            Circle invulnerabilityCircle = new Circle(0, 0, 45);
            invulnerabilityCircle.setFill(Color.PURPLE);
            invulnerabilityCircle.setOpacity(0.5);

            KeyValue kvin0 = new KeyValue(invulnerabilityCircle.fillProperty(), Color.LIGHTPINK);
            KeyValue kvin1 = new KeyValue(invulnerabilityCircle.fillProperty(), Color.PURPLE);

            Duration durin0 = Duration.millis(0);
            Duration durin1 = Duration.millis(1000);

            KeyFrame kfin0 = new KeyFrame(durin0, kvin0);
            KeyFrame kfin1 = new KeyFrame(durin1, kvin1);

            Timeline immunityAnimation = new Timeline(kfin0, kfin1);
            immunityAnimation.setCycleCount(Timeline.INDEFINITE);

            setInvulnerabilityVisual(invulnerabilityCircle);
            setInvulnerabilityAnimation(immunityAnimation);

            Main.getInstance().getSoundManager().playImmunityGainedSound();
            Main.getInstance().getUI().startImmunity(duration);
        }
    }

    @Override
    public void onInvulnerabilityLost(InvulnerabilityType type) {
        if (type == InvulnerabilityType.RESPAWN) {
            setOpacity(1);
        }
        else if (type == InvulnerabilityType.IMMUNITY_COIN) {
            Main.getInstance().getSoundManager().playImmunityLostSound();
            Main.getInstance().getUI().stopImmunity();
        }
    }

    @Override
    public void update() {
        super.update();
        state.update();

        onGround = false;
        checkFloorCollisions();
        onPlatform = false;
        checkPlatformCollisions();

        // Player stays inside the window
        setTranslateX(Math.clamp(getTranslateX(), Main.PLAYABLE_AREA_MIN_X, Main.PLAYABLE_AREA_MAX_X));
        if (getTranslateY() > Main.WINDOW_HEIGHT + Main.FALL_DEATH_OFFSET) {
            die();
        }
    }

    // executed on keyboard input to perform particular actions
    @Override
    public void handle(KeyEvent event) {
        if ((event.getCode() == KeyCode.RIGHT) && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.rightPressed();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.leftPressed();
        } else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.rightReleased();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.leftReleased();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.jumpPressed();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.jumpReleased();
        } else if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().getCamera().stopFollowing();
        } else if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().getCamera().startFollowing();
        } else if (event.getCode() == KeyCode.DIGIT3 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            
        } else if (event.getCode() == KeyCode.ESCAPE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().togglePause();
        } else if (event.getCode() == KeyCode.F1 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().slowDownGame();
        } else if (event.getCode() == KeyCode.F2 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().speedUpGame();
        }
    }

    private void checkFloorCollisions() {
        List<Sprite> allSprites = new ArrayList<>(Arrays.asList(Main.getInstance().getFloor().getFloorBlocks()));

        Bounds newPlayerBounds = getBoundsInParent();
        List<Sprite> collisionSprites = new ArrayList<>();
        for (Sprite sprite : allSprites) {
            if (!sprite.isVisible()) {
                continue;
            }
            if (sprite.getBoundsInParent().intersects(newPlayerBounds)) {
                collisionSprites.add(sprite);
            }
        }

        for (Sprite sprite : collisionSprites) {
            // collision below the player
            Bounds newSpriteBounds = sprite.getBoundsInParent();
            if (newPlayerBounds.getMaxY() >= newSpriteBounds.getMinY()
                    && getOldBounds().getMaxY() <= sprite.getOldBounds().getMinY()) {
                setTranslateY(newSpriteBounds.getMinY() - newPlayerBounds.getHeight() / 2.0);
                newPlayerBounds = getBoundsInParent();
                onGround = true;
            }
        }
    }

    private void checkPlatformCollisions() {
        final double EPSILON = 0.001;
        List<Platform> allSprites = Main.getInstance().getPlatformManager().getOwnedSprites();

        Bounds newPlayerBounds = getBoundsInParent();
        List<Sprite> collisionSprites = new ArrayList<>();
        for (Sprite sprite : allSprites) {
            if (sprite.getBoundsInParent().intersects(newPlayerBounds)) {
                collisionSprites.add(sprite);
            }
        }

        for (Sprite sprite : collisionSprites) {
            // collision on the right of the player
            Bounds newSpriteBounds = sprite.getBoundsInParent();
            if (newPlayerBounds.getMaxX() >= newSpriteBounds.getMinX()
            && getOldBounds().getMaxX() <= sprite.getOldBounds().getMinX()) {
                setTranslateX(newSpriteBounds.getMinX() - newPlayerBounds.getWidth() / 2.0 - EPSILON);
                newPlayerBounds = getBoundsInParent();
            }
            // collision on the left of the player
            else if (newPlayerBounds.getMinX() <= newSpriteBounds.getMaxX()
            && getOldBounds().getMinX() >= sprite.getOldBounds().getMaxX()) {
                setTranslateX(newSpriteBounds.getMaxX() + newPlayerBounds.getWidth() / 2.0 + EPSILON);
                newPlayerBounds = getBoundsInParent();
            }
            // collision below the player
            else if (newPlayerBounds.getMaxY() >= newSpriteBounds.getMinY()
            && getOldBounds().getMaxY() <= sprite.getOldBounds().getMinY()) {
                setTranslateY(newSpriteBounds.getMinY() - newPlayerBounds.getHeight() / 2.0);
                newPlayerBounds = getBoundsInParent();
                onPlatform = true;
            }
        }
    }
}
