package rs.etf.dz1.sprites.characters;

import javafx.animation.Animation;
import javafx.scene.Node;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.InvulnerabilityType;
import rs.etf.dz1.utils.TimeHelper;

public abstract class Character extends Sprite {
    private int health = 1;
    private double invulnerabilityDuration = 0;
    private double invulnerabilityTimeLeft = 0;
    private InvulnerabilityType invulnerabilityType = InvulnerabilityType.NONE;
    private Animation invulnerabilityAnimation;
    private Node invulnerabilityVisual;

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

    public boolean isImmune() {
        return invulnerabilityType == InvulnerabilityType.IMMUNITY_COIN;
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
    public void update() {
        super.update();
        final double deltaTime = TimeHelper.getDeltaTime();

        invulnerabilityTimeLeft -= deltaTime;
        if (invulnerabilityTimeLeft <= 0) {
            invulnerabilityLostInternal();
        }
    }

    protected void onInvulnerabilityGained(InvulnerabilityType type, double duration) {
    }

    protected void onInvulnerabilityLost(InvulnerabilityType type) {
    }

    protected void setInvulnerabilityAnimation(Animation invulnerabilityAnimation) {
        this.invulnerabilityAnimation = invulnerabilityAnimation;
    }

    protected void setInvulnerabilityVisual(Node invulnerabilityVisual) {
        this.invulnerabilityVisual = invulnerabilityVisual;
    }

    private void invulnerabilityGainedInternal(InvulnerabilityType type, double duration) {
        invulnerabilityDuration = duration;
        invulnerabilityTimeLeft = duration;
        invulnerabilityType = type;

        onInvulnerabilityGained(invulnerabilityType, duration);

        if (invulnerabilityVisual != null) {
            getChildren().add(invulnerabilityVisual);
        }
        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.play();
        }
    }

    private void invulnerabilityLostInternal() {
        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.stop();
        }
        if (invulnerabilityVisual != null) {
            getChildren().remove(invulnerabilityVisual);
        }

        onInvulnerabilityLost(invulnerabilityType);

        invulnerabilityType = InvulnerabilityType.NONE;
        invulnerabilityTimeLeft = 0;
        invulnerabilityDuration = 0;
        invulnerabilityAnimation = null;
    }
}
