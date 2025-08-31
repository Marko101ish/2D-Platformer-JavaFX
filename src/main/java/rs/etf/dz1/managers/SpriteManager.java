package rs.etf.dz1.managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.IUpdatable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SpriteManager<T extends Sprite> implements IUpdatable {
    protected List<T> ownedSprites = new LinkedList<T>();

    // This will be true for the duration of the frame
    // when a new Sprite has been spawned
    protected boolean justSpawned = false;
    protected final Random randomizer;

    private final SpawnerConfig config;
    private double timeUntilSpawn;

    // Layer used for displaying ownedSprites
    private Group layer;

    public SpriteManager(SpawnerConfig config, Group layer) {
        this.config = config;
        this.layer = layer;

        timeUntilSpawn = 0;
        randomizer = new Random(System.nanoTime());
    }

    @Override
    public void update(double deltaTime) {
        if (timeUntilSpawn <= 0) {
            timeUntilSpawn = config.spawnCooldown();

            spawnSprite(getRandomSpawnPoint());

            justSpawned = true;
        }
        else {
            justSpawned = false;
        }

        timeUntilSpawn -= deltaTime;

        removeOutOfBoundsSprites();
        ownedSprites.forEach(e -> e.update(deltaTime));
    }

    public T spawnSprite(Point2D spawnPoint) {
        T newSprite = createSprite();
        ownedSprites.addLast(newSprite);
        layer.getChildren().add(newSprite);

        newSprite.setTranslateX(spawnPoint.getX());
        newSprite.setTranslateY(spawnPoint.getY());

        return newSprite;
    }

    protected abstract T createSprite();

    private Point2D getRandomSpawnPoint()
    {
        double randomHeight = randomizer.nextDouble() * (config.maxSpawnY() - config.minSpawnY()) +  config.minSpawnY();
        return new Point2D(config.spawnX(), randomHeight);
    }

    private void removeOutOfBoundsSprites() {
        List<T> spritesToRemove = new LinkedList<>();
        for (T sprite : ownedSprites) {

            Bounds bounds = sprite.getBoundsInParent();
            if (bounds.getMaxX() < 0) {
                spritesToRemove.add(sprite);
            }
        }

        layer.getChildren().removeAll(spritesToRemove);
        ownedSprites.removeAll(spritesToRemove);
    }

}
