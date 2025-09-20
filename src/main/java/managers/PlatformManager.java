package managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import main.Main;
import sprites.characters.Enemy;
import sprites.Platform;

public class PlatformManager extends SpriteManager<Platform> {
    private static final double MIN_WIDTH = 2.5 * Enemy.ENEMY_WIDTH;
    private static final double MAX_WIDTH = 4.5 * Enemy.ENEMY_WIDTH;
    private static final double HEIGHT = 30.0;

    // [0, 1]
    private static final double ENEMY_PROBABILITY = 0.33;

    // enemyProbability defines the probability of
    // this platform spawning with an enemy on it
    public PlatformManager(SpawnerConfig config, Group layer) {
        super(config, layer);
    }

    @Override
    protected Platform createSprite() {
        double width = randomizer.nextDouble() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;
        return new Platform(width, HEIGHT);
    }

    @Override
    protected void onSpriteSpawned(Platform spawnedSprite) {
        EnemyManager enemyManager = Main.getInstance().getEnemyManager();
        if (enemyManager == null) {
            return;
        }

        if (randomizer.nextDouble() < ENEMY_PROBABILITY) {
            Bounds platformBounds = spawnedSprite.getBoundsInParent();

            Point2D spawnPoint = new Point2D(
                    platformBounds.getMaxX(),
                    platformBounds.getMinY()
            );
            Enemy spawnedEnemy = enemyManager.spawnSprite(spawnPoint);
            if (spawnedEnemy == null) {
                return;
            }

            spawnedEnemy.setPlatform(spawnedSprite);
        }
    }
}
