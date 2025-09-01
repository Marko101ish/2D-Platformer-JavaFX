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

    private static final Paint EYE_COLOR = Color.BLACK;

    private State state;

    private Circle body;
    private Circle eye;

    private boolean onGround = false;
    private boolean onPlatform = false;
    private boolean falling = false;

    public Player() {
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
    public void update(double deltaTime) {
        super.update(deltaTime);
        state.update(deltaTime);

        Floor floor = Main.getInstance().getFloor();

        CollisionResult collisionWithFloor = CollisionHelper.checkCollision(this, floor);
        onGround = collisionWithFloor.inCollision && collisionWithFloor.isAbove();

//        onPlatform = false;
//        for (Platform platform : Main.getInstance().getPlatformManager().getOwnedSprites()) {
//            CollisionResult collisionWithPlatform = CollisionHelper.checkCollision(this, platform);
//            if (collisionWithPlatform.inCollision) {
//                if(collisionWithPlatform.isBelow()) {
//                    setVelocityY(0.0);
//                }
//                if(collisionWithPlatform.isLeft()) {
//                    if(isGoingRight()) {
//                        setVelocityX(0.0);
//                    }
//                }
//                if(collisionWithPlatform.isRight()) {
//                    if(isGoingLeft()) {
//                        setVelocityX(0.0);
//                    }
//                }
//
//                onPlatform = true;
//                break;
//            }
//        }

        // Player stays inside the window
        setTranslateX(Math.clamp(getTranslateX(), 0.0, 1280));
        setTranslateY(Math.clamp(getTranslateY(), 0.0, 720));
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
        }
    }
}
