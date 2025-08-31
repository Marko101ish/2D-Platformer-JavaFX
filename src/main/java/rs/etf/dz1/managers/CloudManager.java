package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.sprites.Cloud;

public class CloudManager extends SpriteManager<Cloud> {

    public CloudManager(SpawnerConfig config, Group layer) {
        super(config, layer);
    }

    @Override
    protected Cloud createSprite() {
        return new Cloud();
    }
}
