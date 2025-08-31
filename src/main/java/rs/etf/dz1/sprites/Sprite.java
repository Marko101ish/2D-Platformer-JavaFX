/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.Group;
import rs.etf.dz1.utils.IUpdatable;

/**
 *
 * @author om180345d
 */
public abstract class Sprite extends Group implements IUpdatable {
    // method should be redefined and called in Main.update() method to update the object status
    public void update(double deltaTime) {

    }
}
