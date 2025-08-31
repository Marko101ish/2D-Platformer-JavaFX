package rs.etf.dz1.managers;

import rs.etf.dz1.sprites.Cloud;

public class CloudManager extends SpriteManager<Cloud> {
    private static final double CLOUD_SPEED = 400.0;

    public CloudManager(SpawnerConfig config) {
        super(config);
    }

    @Override
    protected Cloud createSprite() {
        return new Cloud(CLOUD_SPEED);
    }
}
