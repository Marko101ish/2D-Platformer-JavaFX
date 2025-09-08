package rs.etf.dz1.managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.IUpdatable;
import rs.etf.dz1.utils.TimeHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SpriteManager<T extends Sprite> implements IUpdatable {
    protected List<T> ownedSprites = new LinkedList<T>();
    protected List<T> readyForRemoval = new LinkedList<T>();

    protected final Random randomizer;

    private static final double SPAWN_POINT_X = Main.PLAYABLE_AREA_MAX_X + 450.0;
    private static final double DESPAWN_POINT_X = Main.PLAYABLE_AREA_MIN_X - 0.0;
    private final SpawnerConfig config;
    // Layer used for displaying ownedSprites
    private final Group layer;

    private double timeUntilSpawn;

    public SpriteManager(SpawnerConfig config, Group layer) {
        this.config = config;
        this.layer = layer;

        timeUntilSpawn = config.initialCooldown();
        randomizer = new Random(System.nanoTime());
    }

    public final List<T> getOwnedSprites() {
        return ownedSprites;
    }

    @Override
    public void update() {
        final double deltaTime = TimeHelper.getDeltaTime();

        if (timeUntilSpawn <= 0) {
            timeUntilSpawn = config.spawnCooldown();

            T spawnedSprite = spawnSprite(getRandomSpawnPoint());
            if (spawnedSprite != null) {
                onSpriteSpawned(spawnedSprite);
            }
        }

        timeUntilSpawn -= deltaTime;

        removeOutOfBoundsSprites();

        layer.getChildren().removeAll(readyForRemoval);
        ownedSprites.removeAll(readyForRemoval);

        ownedSprites.forEach(Sprite::update);
    }

    public T spawnSprite(Point2D spawnPoint) {
        T newSprite = createSprite();
        if (newSprite == null) {
            return null;
        }

        ownedSprites.addLast(newSprite);
        layer.getChildren().add(newSprite);

        Bounds bounds = newSprite.getBoundsInParent();

        newSprite.setTranslateX(spawnPoint.getX() - bounds.getWidth() / 2.0);
        newSprite.setTranslateY(spawnPoint.getY() - bounds.getHeight() / 2.0);

        newSprite.onInit();

        return newSprite;
    }

    protected abstract T createSprite();

    protected void onSpriteSpawned(T spawnedSprite) {

    }

    private Point2D getRandomSpawnPoint()
    {
        double randomHeight = randomizer.nextDouble() * (config.maxSpawnY() - config.minSpawnY()) +  config.minSpawnY();
        return new Point2D(SPAWN_POINT_X, randomHeight);
    }

    private void removeOutOfBoundsSprites() {
        for (T sprite : ownedSprites) {
            Bounds bounds = sprite.getBoundsInParent();
            if (bounds.getMaxX() < DESPAWN_POINT_X) {
                readyForRemoval.add(sprite);
            }
        }
    }

}
