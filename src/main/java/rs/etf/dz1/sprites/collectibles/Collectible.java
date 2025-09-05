package rs.etf.dz1.sprites.collectibles;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.sprites.characters.Character;

public abstract class Collectible extends Sprite {
    public Collectible() {
    }

    public void collect(Character character) {
        onCollected(character);
    }

    protected abstract void onCollected(Character character);

    @Override
    public void onInit() {
        TranslateTransition floatingAnimation = new TranslateTransition(Duration.millis(400.0), this);
        floatingAnimation.setByY(15.0);
        floatingAnimation.setCycleCount(Timeline.INDEFINITE);
        floatingAnimation.setAutoReverse(true);
        floatingAnimation.play();
    }
}
