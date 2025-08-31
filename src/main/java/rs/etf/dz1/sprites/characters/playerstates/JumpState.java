/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters.playerstates;

import javafx.scene.paint.Color;
import rs.etf.dz1.sprites.characters.Player;

/**
 *
 * @author om180345d
 */
public class JumpState extends State {

    private static final double STRAFE_SPEED = 450.0f;
    private static final double JUMP_VELOCITY = 900.0f;
    private static final double GRAVITY_ACCELERATION = -30.0f;

    public JumpState(Player player) {
        super(player);
        player.setFillColor(Color.RED);

        player.setVelocityY(-JUMP_VELOCITY);
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
        player.setVelocityX(0);
    }

    @Override
    public void rightPressed() {
        super.rightPressed();
        player.faceRight();
        player.setVelocityX(STRAFE_SPEED);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        double newVelocityY = player.getVelocityY() - GRAVITY_ACCELERATION;
        player.setVelocityY(newVelocityY);

        if (newVelocityY > 0.0f) {
            player.fall();
        }
    }

}
