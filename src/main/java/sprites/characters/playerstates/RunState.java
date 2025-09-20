/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.characters.playerstates;

import javafx.scene.paint.Color;
import sprites.characters.Player;

/**
 *
 * @author om180345d
 */
public class RunState extends State {
    private static final double RUN_SPEED = 450.0f;

    public RunState(Player player) {
        super(player);
        player.setFillColor(Color.GREEN);

        if (player.isFacingRight()) {
            player.setVelocityX(RUN_SPEED);
        }
        else {
            player.setVelocityX(-RUN_SPEED);
        }
    }

    @Override
    public void jumpPressed() {
        super.jumpPressed();
        player.jump();
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
        player.run();
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
        player.run();
    }

    @Override
    public void update() {
        super.update();

        if (!player.isOnGround() && !player.isOnPlatform()) {
            player.fall();
            return;
        }

        if (player.getVelocityX() == 0) {
            player.stop();
        }
    }

}
