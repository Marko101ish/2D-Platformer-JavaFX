package rs.etf.dz1.sprites.characters;

import rs.etf.dz1.sprites.Sprite;

public abstract class Character extends Sprite {
    private int health = 1;

    public Character() {

    }

    public Character(int health) {
        this.health = health;
    }

    public void takeHit() {
        if (health <= 0) {
            System.out.println("Character is already dead, they can't take a hit");
            return;
        }

        --health;

        if (health <= 0) {
            die();
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void die() {

    }
}
