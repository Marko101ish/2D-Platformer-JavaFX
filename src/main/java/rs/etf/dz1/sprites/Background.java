/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author om180345d
 */
public class Background extends Sprite {
    private final int INITIAL_NUMBER_OF_CLOUDS = 3;
    private static final double CLOUD_SPEED = 400.0;
    private static final double CLOUD_SPAWN_COOLDOWN = 1.5;
    private static final double CLOUD_SPAWN_INITIAL_COOLDOWN = 0.250;

    private final int width;
    private final int height;
    private final double cloudSpawnOutOfScreenX;

    private List<Cloud> clouds;
    private double cloudSpawnTimer = CLOUD_SPAWN_INITIAL_COOLDOWN;

    public Background(int width, int height) {
        this.width = width;
        this.height = height;

        this.cloudSpawnOutOfScreenX = width + 200.0;

        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTBLUE), new Stop(1, Color.DEEPSKYBLUE)};
        LinearGradient gradient = new LinearGradient(0, 0, 0, height, false, CycleMethod.NO_CYCLE, stops);

        Rectangle bg = new Rectangle(0, 0, width, height);
        bg.setFill(gradient);
        getChildren().add(bg);

        this.clouds = new LinkedList<>();
        // spawn some clouds on init
        // then proceed to spawn them in the update loop
        double offset = (double) width / INITIAL_NUMBER_OF_CLOUDS;
        for (int i = 0; i < INITIAL_NUMBER_OF_CLOUDS; i++) {
            spawnCloud(i * offset);
        }
    }

    @Override
    public void update(double deltaTime) {
        cloudSpawnTimer -= deltaTime;
        if (cloudSpawnTimer <= 0) {
            cloudSpawnTimer = CLOUD_SPAWN_COOLDOWN;
            spawnCloud(cloudSpawnOutOfScreenX);
        }

        removeOutOfBoundsClouds();

        clouds.forEach(e -> e.update(deltaTime));
    }

    private double getRandomCloudHeight()
    {
        Random rand = new Random();
        return rand.nextDouble() * (height / 2.0);
    }

    private void removeOutOfBoundsClouds() {
        List<Cloud> cloudsToRemove = new LinkedList<>();
        for (Cloud cloud : clouds) {

            Bounds bounds = cloud.getBoundsInParent();
            if (bounds.getMaxX() < 0) {
                cloudsToRemove.add(cloud);
            }
        }

        getChildren().removeAll(cloudsToRemove);
        clouds.removeAll(cloudsToRemove);
    }

    private void spawnCloud(double spawnPosX) {
        Cloud cloud = new Cloud(CLOUD_SPEED);

        double spawnPosY = getRandomCloudHeight();

        cloud.setTranslateX(spawnPosX);
        cloud.setTranslateY(spawnPosY);
        clouds.add(cloud);
        getChildren().add(cloud);
    }
}
