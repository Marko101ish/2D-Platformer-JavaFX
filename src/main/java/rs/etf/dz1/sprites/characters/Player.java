/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Floor;
import rs.etf.dz1.sprites.Platform;
import rs.etf.dz1.sprites.characters.playerstates.*;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

/**
 *
 * @author om180345d
 */
public class Player extends Character implements EventHandler<KeyEvent> {

    private static final Paint IDLE_COLOR = Color.YELLOW;
    private static final Paint RUN_COLOR = Color.GREEN;
    private static final Paint JUMP_COLOR = Color.RED;
    private static final Paint FALL_COLOR = Color.BLUE;
    private static final Paint DEAD_COLOR = Color.PURPLE;

    private static final Paint EYE_COLOR = Color.BLACK;

    private State state;

    private Circle body;
    private Circle eye;

    private boolean onGround = false;
    private boolean onPlatform = false;
    private boolean falling = false;

    public Player() {
        state = new IdleState(this);
        getChildren().clear();
        body = new Circle(0, 0, 40);
        body.setFill(IDLE_COLOR);
        eye = new Circle(15, 0, 10);
        eye.setFill(EYE_COLOR);
        getChildren().add(body);
        getChildren().add(eye);
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
        body.setFill(RUN_COLOR);
    }

    public String getState() {
        return state.toString();
    }

    public void jump() {
        state = new JumpState(this);
        body.setFill(JUMP_COLOR);
    }

    public void fall() {
        state = new FallState(this);
        body.setFill(FALL_COLOR);
    }

    public void stop() {
        if (getVelocityX() != 0) {
            run();
        }
        else {
            state = new IdleState(this);
            body.setFill(IDLE_COLOR);
        }
    }

    @Override
    public void die() {
        state = new DeadState(this);
        body.setFill(DEAD_COLOR);
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
    public void update(double deltaTime) {
        super.update(deltaTime);

        Floor floor = Main.getInstance().getFloor();

        CollisionResult collisionWithFloor = CollisionHelper.checkCollision(this, floor);
        onGround = collisionWithFloor.inCollision && collisionWithFloor.isAbove();
        onPlatform = false;

        for (Platform platform : Main.getInstance().getPlatformManager().getOwnedSprites()) {
            CollisionResult collisionWithPlatform = CollisionHelper.checkCollision(this, platform);
            if (collisionWithPlatform.inCollision) {
                onPlatform = true;
                if (collisionWithPlatform.isAbove()) {
                    onGround = true;
                }
                break;
            }
        }

        if(onGround) {
            body.setFill(Color.LIGHTBLUE);
        }
        else {
            body.setFill(Color.RED);
        }

        state.update(deltaTime);
    }

    // executed on keyboard input to perform particular actions
    @Override
    public void handle(KeyEvent event) {
        if ((event.getCode() == KeyCode.RIGHT) && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.rightPressed();
        } else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.rightReleased();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.leftPressed();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.leftReleased();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            state.jumpPressed();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_RELEASED) {
            state.jumpReleased();
        } else if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().getCamera().stopFollowing();
        } else if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().getCamera().startFollowing(this);
        } else if (event.getCode() == KeyCode.DIGIT3 && event.getEventType() == KeyEvent.KEY_PRESSED) {
            
        } else if (event.getCode() == KeyCode.ESCAPE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            Main.getInstance().togglePause();
        }
    }
}
