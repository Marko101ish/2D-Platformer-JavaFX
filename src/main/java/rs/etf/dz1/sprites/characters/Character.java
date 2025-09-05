package rs.etf.dz1.sprites.characters;

import javafx.animation.Animation;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.InvulnerabilityType;

public abstract class Character extends Sprite {
    private int health = 1;
    private double invulnerabilityTimeLeft = 0;
    private InvulnerabilityType invulnerabilityType = InvulnerabilityType.NONE;
    private Animation invulnerabilityAnimation;

    public Character() {

    }

    public void takeHit() {
        if (isInvulnerable()) {
            return;
        }
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

    public boolean isInvulnerable() {
        return invulnerabilityType != InvulnerabilityType.NONE;
    }

    public void giveInvulnerability(InvulnerabilityType type, double duration) {
        if (type == InvulnerabilityType.NONE) {
            System.err.print("InvulnerabilityType cannot be NONE");
            return;
        }

        // Is already invulnerable
        if (invulnerabilityType != InvulnerabilityType.NONE) {
            // Override previous invulnerability
            onInvulnerabilityLost(invulnerabilityType);
        }

        invulnerabilityGainedInternal(type, duration);
    }

    public void die() {

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        invulnerabilityTimeLeft -= deltaTime;
        if (invulnerabilityTimeLeft <= 0) {
            invulnerabilityLostInternal();
        }
    }

    protected void onInvulnerabilityGained(InvulnerabilityType type) {
    }

    protected void onInvulnerabilityLost(InvulnerabilityType type) {
    }

    protected void setInvulnerabilityAnimation(Animation invulnerabilityAnimation) {
        this.invulnerabilityAnimation = invulnerabilityAnimation;
    }

    private void invulnerabilityGainedInternal(InvulnerabilityType type, double duration) {
        invulnerabilityTimeLeft = duration;
        invulnerabilityType = type;

        onInvulnerabilityGained(invulnerabilityType);

        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.play();
        }
    }

    private void invulnerabilityLostInternal() {
        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.stop();
        }

        onInvulnerabilityLost(invulnerabilityType);

        invulnerabilityType = InvulnerabilityType.NONE;
        invulnerabilityTimeLeft = 0;
        invulnerabilityAnimation = null;
    }
}
