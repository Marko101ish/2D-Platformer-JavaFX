package rs.etf.dz1.managers;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import rs.etf.dz1.sprites.Sprite;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class SpriteManager<T extends Sprite> extends Sprite {
    private LinkedList<T> ownedSprites = new LinkedList<T>();
    private SpawnerConfig config;

    private double timeUntilSpawn;
    private Random randomizer;

    public SpriteManager(SpawnerConfig config) {
        this.config = config;
        this.timeUntilSpawn = 0;
        this.randomizer = new Random();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        if (this.timeUntilSpawn <= 0) {
            this.timeUntilSpawn = config.spawnCooldown();

            spawnSprite();
        }

        this.timeUntilSpawn -= deltaTime;

        removeOutOfBoundsSprites();
        ownedSprites.forEach(e -> e.update(deltaTime));
        System.out.println(ownedSprites.size());
    }

    protected abstract T createSprite();

    private void spawnSprite() {
        T newSprite = createSprite();
        ownedSprites.add(newSprite);
        getChildren().add(newSprite);
        Point2D spawnPoint = getSpawnPoint();
        newSprite.setTranslateX(spawnPoint.getX());
        newSprite.setTranslateY(spawnPoint.getY());
    }

    private Point2D getSpawnPoint()
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

        getChildren().removeAll(spritesToRemove);
        ownedSprites.removeAll(spritesToRemove);
    }

}
