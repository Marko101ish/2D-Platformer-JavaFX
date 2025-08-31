package rs.etf.dz1.managers;

import javafx.geometry.Point2D;
import rs.etf.dz1.sprites.Enemy;
import rs.etf.dz1.sprites.Platform;
import rs.etf.dz1.sprites.Player;

public class EnemyManager extends SpriteManager<Enemy> {
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    private Player player;

    public EnemyManager(SpawnerConfig config) {
        super(config);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        ownedSprites.forEach(e -> {
            if (player.getBoundsInParent().intersects(e.getBoundsInParent())) {
                if (!player.isDead()) {
                    player.takeHit();
                }
            }
        });
    }

    public void spawnSprite(Point2D spawnPoint, Platform platform) {
        super.spawnSprite(spawnPoint);
    }

    @Override
    protected Enemy createSprite() {
        return new Enemy();
    }
}
