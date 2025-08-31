package rs.etf.dz1.managers;

import rs.etf.dz1.sprites.Cloud;

public class CloudManager extends SpriteManager<Cloud> {

    public CloudManager(SpawnerConfig config) {
        super(config);
    }

    @Override
    protected Cloud createSprite() {
        return new Cloud();
    }
}
