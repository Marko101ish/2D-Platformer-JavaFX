package rs.etf.dz1.managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import rs.etf.dz1.sprites.Enemy;
import rs.etf.dz1.sprites.Platform;

public class PlatformManager extends SpriteManager<Platform> {
    private static final double MIN_WIDTH = 2 * EnemyManager.ENEMY_WIDTH;
    private static final double MAX_WIDTH = 4 * EnemyManager.ENEMY_WIDTH;
    private static final double HEIGHT = 20.0;
    private static final double SPEED = 200.0;

    // [0, 1]
    private static double ENEMY_PROBABILITY = 0.33;
    private EnemyManager enemyManager;

    // enemyProbability defines the probability of
    // this platform spawning with an enemy on it
    public PlatformManager(SpawnerConfig config) {
        super(config);
    }

    public void setEnemyManager(EnemyManager  enemyManager) {
        this.enemyManager = enemyManager;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (!justSpawned || enemyManager == null) {
            return;
        }

        if (randomizer.nextDouble() < ENEMY_PROBABILITY) {
            Platform spawnedPlatform = ownedSprites.getLast();
            Bounds platformBounds = spawnedPlatform.getBoundsInParent();
            Point2D spawnPoint = new Point2D(
                    platformBounds.getMaxX() - EnemyManager.ENEMY_WIDTH / 2.0,
                    platformBounds.getMinY() - EnemyManager.ENEMY_HEIGHT / 2.0
            );
            Enemy spawnedEnemy = enemyManager.spawnSprite(spawnPoint);
            spawnedEnemy.setPlatform(spawnedPlatform);
        }
    }

    @Override
    protected Platform createSprite() {
        double width = randomizer.nextDouble() * (MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;
        return new Platform(width, HEIGHT, SPEED);
    }
}
