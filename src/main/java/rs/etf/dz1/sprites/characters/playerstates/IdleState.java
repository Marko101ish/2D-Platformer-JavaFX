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
public class IdleState extends State {
    
    public IdleState(Player player) {
        super(player);
        player.setFillColor(Color.YELLOW);

        if (player.getVelocityX() != 0) {
            player.run();
        }
    }

    @Override
    public void jumpPressed() {
        super.jumpPressed();
        player.jump();
    }

    @Override
    public void leftPressed() {
        super.leftPressed();
        player.faceLeft();
        player.run();
    }

    @Override
    public void rightPressed() {
        super.rightPressed();
        player.faceRight();
        player.run();
    }

    @Override
    public void update(double deltaTime) {
        if (!player.isOnGround()) {
            player.fall();
        }
    }
    
}
