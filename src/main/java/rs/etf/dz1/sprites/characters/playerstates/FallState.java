/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters.playerstates;

import rs.etf.dz1.sprites.characters.Player;

/**
 *
 * @author om180345d
 */
public class FallState extends State {
    private static final double STRAFE_SPEED = 450.0f;
    private static final double GRAVITY_ACCELERATION = -30.0f;

    public FallState(Player player) {
        super(player);
        player.setFalling(true);
    }

    @Override
    public void leftReleased() {
        super.leftReleased();
        if (player.isFaceLeft()) {
            player.setVelocityX(0);
        }
    }

    @Override
    public void leftPressed() {
        super.leftPressed();
        player.faceLeft();
        player.setVelocityX(STRAFE_SPEED);
    }

    @Override
    public void rightReleased() {
        super.rightReleased();
        player.setVelocityX(0);
    }

    @Override
    public void rightPressed() {
        super.rightPressed();
        player.faceRight();
        player.setVelocityX(STRAFE_SPEED);
    }

    @Override
    public void move(double deltaTime) {
        double currentVelocityY = player.getVelocityY();

        player.setTranslateX(player.getTranslateX() + player.getVelocityX() * deltaTime);
        player.setTranslateY(player.getTranslateY() - currentVelocityY * deltaTime);

        currentVelocityY += GRAVITY_ACCELERATION;
        player.setVelocityY(currentVelocityY);

        if (player.isOnGround()) {
            player.setVelocityY(0);
            player.stop();
        }
    }

}
