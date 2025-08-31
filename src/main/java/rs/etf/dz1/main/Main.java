package rs.etf.dz1.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import rs.etf.dz1.cameras.Camera;
import rs.etf.dz1.managers.EnemyManager;
import rs.etf.dz1.managers.SpawnerConfig;
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

    public static final double TIME_TO_LIVE_S = 60.0;

    private Background background;
    private Player player;
    private List<Platform> platforms;
    private UI ui;
    private EnemyManager enemyManager;

    private static Main instance;
    
    private long lastFrameNanoTime;
    private double timeLeft = TIME_TO_LIVE_S;

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
    private void update(double deltaTime) {
        background.update(deltaTime);
        player.update(deltaTime);
        enemyManager.update(deltaTime);
        platforms.forEach(e->e.update(deltaTime));

        timeLeft = timeLeft - deltaTime;

        // When there's no time left the game closes automatically
        if(timeLeft < 0)
        {
            // System.exit(0);
        }

        camera.update(deltaTime);

        ui.setTimeLeft(timeLeft);
        ui.update(deltaTime);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        SpawnerConfig enemySpawnerconfig = new SpawnerConfig(
                WINDOW_WIDTH + ENEMY_WIDTH,
                WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2.,
                WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2.,
                2.5
        );

        enemyManager = new EnemyManager(enemySpawnerconfig);
        platforms = new LinkedList<>();
        background = new Background(WINDOW_WIDTH, WINDOW_HEIGHT);

        Floor floor = new Floor(FLOOR_WIDTH, FLOOR_HEIGHT);
        floor.setTranslateY(WINDOW_HEIGHT);

        camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        camera.getChildren().add(floor);

        Group sprites = new Group();
        camera.getChildren().add(sprites);

        sprites.getChildren().add(enemyManager);

        Random rand =  new Random();
        // making 10 platforms and adding them to the scene
        for (int j = 0; j < 10; j++) {
            Platform platform = new Platform();
            double platformHeight = rand.nextDouble() * (WINDOW_HEIGHT - 3 * ENEMY_HEIGHT) + ENEMY_HEIGHT;
            platform.setTranslateX((1 + j) * 300);
            platform.setTranslateY(platformHeight);
            sprites.getChildren().add(platform);
            platforms.add(platform);
        }

        player = new Player(camera);
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - ENEMY_HEIGHT / 2);
        sprites.getChildren().add(player);
        enemyManager.setPlayer(player);

        ui = new UI(WINDOW_WIDTH, WINDOW_HEIGHT, TIME_TO_LIVE_S);

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
                // Delta time is in seconds
                double deltaNanoTime = (double) (currentNanoTime - lastFrameNanoTime);
                double deltaTime = deltaNanoTime / 1_000_000_000.0;;
                lastFrameNanoTime = currentNanoTime;

                double fixedDeltaTime = 1./60.0;

                update(fixedDeltaTime);
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
