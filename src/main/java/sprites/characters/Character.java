package sprites.characters;

import javafx.animation.Animation;
import javafx.scene.Node;
import events.FXEvent;
import sprites.Sprite;
import utils.InvulnerabilityType;
import utils.TimeHelper;

public abstract class Character extends Sprite {
    private int health = 1;
    private double invulnerabilityTimeLeft = 0;
    private InvulnerabilityType activeInvulnerability = InvulnerabilityType.NONE;
    private Animation invulnerabilityAnimation;
    private Node invulnerabilityFX;

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
        return activeInvulnerability != InvulnerabilityType.NONE;
    }

    public boolean isImmune() {
        return activeInvulnerability == InvulnerabilityType.IMMUNITY_COIN;
    }

    public boolean justRespawned() {
        return activeInvulnerability == InvulnerabilityType.RESPAWN;
    }

    public void giveInvulnerability(InvulnerabilityType type, double duration) {
        if (type == InvulnerabilityType.NONE) {
            System.err.print("InvulnerabilityType cannot be NONE");
            return;
        }

        // Is already invulnerable
        if (isInvulnerable()) {
            // Override previous invulnerability
            invulnerabilityLostInternal();
        }

        invulnerabilityGainedInternal(type, duration);
    }

    public void die() {
        invulnerabilityLostInternal();
    }

    @Override
    public void update() {
        super.update();
        final double deltaTime = TimeHelper.getDeltaTime();
        if (invulnerabilityFX != null) {
            invulnerabilityFX.setTranslateX(getTranslateX());
            invulnerabilityFX.setTranslateY(getTranslateY());
        }

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

    protected void setInvulnerabilityFX(Node invulnerabilityVisual) {
        this.invulnerabilityFX = invulnerabilityVisual;
    }

    private void invulnerabilityGainedInternal(InvulnerabilityType type, double duration) {
        invulnerabilityTimeLeft = duration;
        activeInvulnerability = type;

        onInvulnerabilityGained(activeInvulnerability, duration);

        if (invulnerabilityFX != null) {
            FXEvent fxAddedEvent = new FXEvent(FXEvent.ADD, invulnerabilityFX);
            fireEvent(fxAddedEvent);
        }
        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.play();
        }
    }

    private void invulnerabilityLostInternal() {
        if (invulnerabilityAnimation != null) {
            invulnerabilityAnimation.stop();
        }
        if (invulnerabilityFX != null) {
            FXEvent fxAddedEvent = new FXEvent(FXEvent.REMOVE, invulnerabilityFX);
            fireEvent(fxAddedEvent);
        }

        onInvulnerabilityLost(activeInvulnerability);

        activeInvulnerability = InvulnerabilityType.NONE;
        invulnerabilityTimeLeft = 0;
        invulnerabilityAnimation = null;
    }
}
