package rs.etf.dz1.managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.IUpdatable;
import rs.etf.dz1.utils.TimeHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SpriteManager<T extends Sprite> implements IUpdatable {
    protected List<T> ownedSprites = new LinkedList<T>();
    protected List<T> readyForRemoval = new LinkedList<T>();

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

            if (spawnSprite(getRandomSpawnPoint()) != null) {
                justSpawned = true;
            }
        }
        else {
            justSpawned = false;
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

    private Point2D getRandomSpawnPoint()
    {
        double randomHeight = randomizer.nextDouble() * (config.maxSpawnY() - config.minSpawnY()) +  config.minSpawnY();
        return new Point2D(config.spawnX(), randomHeight);
    }

    private void removeOutOfBoundsSprites() {
        for (T sprite : ownedSprites) {
            Bounds bounds = sprite.getBoundsInParent();
            if (bounds.getMaxX() < 0) {
                readyForRemoval.add(sprite);
            }
        }
    }

}
