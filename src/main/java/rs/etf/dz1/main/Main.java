package rs.etf.dz1.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import rs.etf.dz1.cameras.Camera;
import rs.etf.dz1.sprites.*;

/**
 *
 * @author om180345d
 */
public class Main extends Application {

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public static final int FLOOR_WIDTH = WINDOW_WIDTH;
    public static final int FLOOR_HEIGHT = 50;

    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    public static final String TITLE = "Platformer";

    public static final double ENEMY_SPEED = 2.5;
    public static final double CLOUD_SPEED = 5.0;
    public static final double CLOUD_SPAWN_COOLDOWN_MS = 1500.0;
    public static final double CLOUD_SPAWN_INITIAL_COOLDOWN_MS = 250.0;

    public static final Duration TIME_TO_LIVE = Duration.ofSeconds(60);

    private Background background;
    private Player player;
    private List<Enemy> enemies;
    private UI ui;

    private static Main instance;
    
    private long lastFrameNanoTime;
    private Duration timeLeft = TIME_TO_LIVE;

    private Camera camera; // camera used for applying transformations
    
    // returns the instance of the game (Main class)
    public static Main getInstance(){
        return instance;
    }
    
    public Camera getCamera(){
        return camera;
    }
    
    // called once per frame to update game state
    // deltaTime is in milliseconds here
    private void update(long deltaTime) {
        background.update(deltaTime);
        player.update(deltaTime);
        enemies.forEach(e -> e.update(deltaTime));

        // checking collision between player and enemies
        enemies.forEach(e -> {
            if (player.getBoundsInParent().intersects(e.getBoundsInParent())) {
                player.takeHit();
            }
        });

        timeLeft = timeLeft.minus(Duration.ofMillis((long)deltaTime));

        // When there's no time left the game closes automatically
        if(timeLeft.isNegative())
        {
            System.exit(0);
        }

        ui.setTimeLeft(timeLeft);
        ui.update(deltaTime);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        
        enemies = new LinkedList<>();
        background = new Background(WINDOW_WIDTH, WINDOW_HEIGHT, CLOUD_SPEED, CLOUD_SPAWN_INITIAL_COOLDOWN_MS, CLOUD_SPAWN_COOLDOWN_MS);

        Floor floor = new Floor(FLOOR_WIDTH, FLOOR_HEIGHT);
        floor.setTranslateY(WINDOW_HEIGHT);

        camera = new Camera();
        camera.getChildren().add(floor);

        Group sprites = new Group();
        camera.getChildren().add(sprites);

        // making 100 enemies and adding them to the scene
        for (int i = 0; i < 100; i++) {
            Enemy enemy = new Enemy(ENEMY_SPEED);
            enemy.setTranslateX((1 + i) * 800);
            enemy.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
            sprites.getChildren().add(enemy);
            enemies.add(enemy);
        }

        player = new Player();
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
        sprites.getChildren().add(player);

        ui = new UI(WINDOW_WIDTH, WINDOW_HEIGHT, TIME_TO_LIVE);

        Group root = new Group();
        root.getChildren().add(background);
        root.getChildren().add(camera);
        root.getChildren().add(ui);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        this.lastFrameNanoTime = System.nanoTime();

        // calling update once every frame
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // Delta time is in milliseconds
                long deltaTime = Duration.ofNanos(currentNanoTime - lastFrameNanoTime).toMillis();
                lastFrameNanoTime = currentNanoTime;

                update(deltaTime);
            }
        }.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
