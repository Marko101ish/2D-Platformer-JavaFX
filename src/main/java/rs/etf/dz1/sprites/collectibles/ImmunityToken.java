package rs.etf.dz1.sprites.collectibles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import rs.etf.dz1.sprites.characters.Character;
import rs.etf.dz1.utils.InvulnerabilityType;

public class ImmunityToken extends Collectible {
    private final double INVULNERABILITY_DURATION = 5.0;

    private static final double SPEED = 150.0;

    public ImmunityToken() {
        super();
        velocityX = -SPEED;

        Ellipse ellipse = new Ellipse(0, 0, 20, 40);
        ellipse.setFill(Color.PURPLE);
        ellipse.setOpacity(0.5);
        getChildren().add(ellipse);
    }

    @Override
    protected void onCollected(Character character) {
        character.giveInvulnerability(InvulnerabilityType.IMMUNITY_COIN, INVULNERABILITY_DURATION);
    }
}