/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites.characters.playerstates;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.utils.IUpdatable;

/**
 *
 * @author om180345d
 */
public abstract class State implements IUpdatable {

    protected Player player;
    
    public State(Player player){
        this.player = player;
    }
    
    public void rightPressed() {
    }

    public void rightReleased() {
    }

    public void leftPressed() {
    }

    public void leftReleased() {
    }

    public void jumpPressed() {
    }

    public void jumpReleased() {
    }

    @Override
    public void update(double deltaTime) {
    }
}
