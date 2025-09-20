package sprites.collectibles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import main.Main;
import sprites.characters.Character;
import utils.InvulnerabilityType;

public class ImmunityToken extends Collectible {
    private final double INVULNERABILITY_DURATION = 10.0;

    public ImmunityToken() {
        super();
        velocityX = -Main.OBJECT_SPEED;

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