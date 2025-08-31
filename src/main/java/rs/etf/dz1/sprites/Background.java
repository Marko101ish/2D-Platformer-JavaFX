/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import rs.etf.dz1.managers.BirdManager;
import rs.etf.dz1.managers.CloudManager;
import rs.etf.dz1.managers.SpawnerConfig;

/**
 *
 * @author om180345d
 */
public class Background extends Sprite {
    private static final double CLOUD_SPAWN_COOLDOWN = 1.5;
    private static final double BIRD_SPAWN_COOLDOWN = 4.0;

    private CloudManager cloudManager;
    private BirdManager birdManager;

    public Background(int width, int height) {
        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTBLUE), new Stop(1, Color.DEEPSKYBLUE)};
        LinearGradient gradient = new LinearGradient(0, 0, 0, height, false, CycleMethod.NO_CYCLE, stops);

        Rectangle bg = new Rectangle(0, 0, width, height);
        bg.setFill(gradient);
        getChildren().add(bg);

        SpawnerConfig cloudSpawnConfig = new SpawnerConfig(
                width + 200.0,
                0.0,
                height / 2.0,
                CLOUD_SPAWN_COOLDOWN
        );

        SpawnerConfig birdSpawnConfig = new SpawnerConfig(
                width + 200.0,
                0.0,
                height / 2.0,
                BIRD_SPAWN_COOLDOWN
        );

        this.cloudManager = new CloudManager(cloudSpawnConfig);
        this.birdManager = new BirdManager(birdSpawnConfig);
        getChildren().add(this.cloudManager);
        getChildren().add(this.birdManager);
    }

    @Override
    public void update(double deltaTime) {
        cloudManager.update(deltaTime);
        birdManager.update(deltaTime);
    }
}
