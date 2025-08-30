/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.Group;

/**
 *
 * @author om180345d
 */
public abstract class Sprite extends Group {
    
    // method should be redefined and called in Main.update() method to update the object status
    // deltaTime is in milliseconds
    //
    // when moving objects make sure to take deltaTime into account
    // so the simulation runs smoothly even with inconsistent frame rate
    public void update(long deltaTime) {
        
    }
    
}
