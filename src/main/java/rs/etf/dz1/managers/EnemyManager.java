package rs.etf.dz1.managers;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Enemy;
import rs.etf.dz1.sprites.Platform;
import rs.etf.dz1.sprites.Player;
import rs.etf.dz1.utils.Helper;

public class EnemyManager extends SpriteManager<Enemy> {
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    public EnemyManager(SpawnerConfig config, Group layer) {
        super(config, layer);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        Player player = Main.getInstance().getPlayer();

        ownedSprites.forEach(e -> {
            if (Helper.CheckCollision(player, e)) {
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
