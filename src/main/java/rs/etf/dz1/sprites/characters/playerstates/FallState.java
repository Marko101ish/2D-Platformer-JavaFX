/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters.playerstates;

import javafx.scene.paint.Color;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.utils.TimeHelper;

/**
 *
 * @author om180345d
 */
public class FallState extends State {
    private static final double STRAFE_SPEED = 450.0f;
    private static final double GRAVITY_ACCELERATION = -1800.0f;

    public FallState(Player player) {
        super(player);
        player.setFillColor(Color.BLUE);

        player.setFalling(true);
    }

    @Override
    public void leftReleased() {
        super.leftReleased();
        if (player.isFacingLeft()) {
            player.setVelocityX(0);
        }
    }

    @Override
    public void leftPressed() {
        super.leftPressed();
        player.faceLeft();
        player.setVelocityX(-STRAFE_SPEED);
    }

    @Override
    public void rightReleased() {
        super.rightReleased();
        if (player.isFacingRight()) {
            player.setVelocityX(0);
        }
    }

    @Override
    public void rightPressed() {
        super.rightPressed();
        player.faceRight();
        player.setVelocityX(STRAFE_SPEED);
    }

    @Override
    public void update() {
        super.update();
        final double deltaTime = TimeHelper.getDeltaTime();

        double newVelocityY = player.getVelocityY() - GRAVITY_ACCELERATION * deltaTime;
        player.setVelocityY(newVelocityY);

        // Allowing the player to pass through a platform if coming from underneath it
        // player will collide with the platform only if
        // they were falling in the previous frame, and weren't in collision with a platform
        if (player.isOnGround() || player.isOnPlatform()) {
            player.setVelocityY(0);
            player.setFalling(false);
            player.stop();
        }
    }

}
