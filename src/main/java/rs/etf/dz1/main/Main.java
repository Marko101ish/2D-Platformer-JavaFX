package rs.etf.dz1.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import rs.etf.dz1.cameras.Camera;
import rs.etf.dz1.managers.*;
import rs.etf.dz1.sprites.*;
import rs.etf.dz1.sprites.characters.Player;

/**
 *
 * @author om180345d
 */

// For disabling annoying warnings when running the app add these into VM options
//--enable-native-access=javafx.graphics
// --sun-misc-unsafe-memory-access=allow

public class Main extends Application {

    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public static final int FLOOR_WIDTH = WINDOW_WIDTH;
    public static final int FLOOR_HEIGHT = 50;

    private static final double PLATFORM_SPAWN_COOLDOWN = 2.0;
    private static final double ENEMY_SPAWN_COOLDOWN = 2.5;
    private static final double CLOUD_SPAWN_COOLDOWN = 1.5;
    private static final double BIRD_SPAWN_COOLDOWN = 4.0;

    public static final String TITLE = "Platformer";

    public static final double TIME_TO_LIVE_S = 60.0;

    private static Main instance;

    private Group bgLayer;
    private Group floorLayer;
    private Group characterLayer;
    private Group playerLayer;
    private Group uiLayer;

    private Player player;
    private Floor floor;
    private PlatformManager platformManager;
    private EnemyManager enemyManager;
    private CloudManager cloudManager;
    private BirdManager birdManager;
    private UI ui;
    
    private long lastFrameNanoTime;
    private double timeLeft = TIME_TO_LIVE_S;

    private Camera camera; // camera used for applying transformations
    
    // returns the instance of the game (Main class)
    public static Main getInstance() {
        return instance;
    }

    public Camera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    public Floor getFloor() {
        return floor;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    public PlatformManager getPlatformManager() {
        return platformManager;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        initScene(primaryStage);
        initManagers();

        this.lastFrameNanoTime = System.nanoTime();

        // calling update once every frame
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // Delta time is in seconds
                double deltaNanoTime = (double) (currentNanoTime - lastFrameNanoTime);
                lastFrameNanoTime = currentNanoTime;

                // Proper delta time calculation
                double deltaTime = deltaNanoTime / 1_000_000_000.0;
                deltaTime = 1./60.0;
                update(deltaTime);
            }
        }.start();
    }

    // called once per frame to update game state
    // deltaTime is in milliseconds here
    private void update(double deltaTime) {
        timeLeft = timeLeft - deltaTime;

        // When there's no time left the game closes automatically
        if(timeLeft < 0)
        {
            // System.exit(0);
        }

        player.update(deltaTime);
        platformManager.update(deltaTime);
        enemyManager.update(deltaTime);
        cloudManager.update(deltaTime);
        birdManager.update(deltaTime);
        camera.update(deltaTime);

        ui.setTimeLeft(timeLeft);
        ui.update(deltaTime);
    }

    void initScene(Stage primaryStage) {
        // Background
        bgLayer = new Group();

        // Middle-ground
        floorLayer = new Group();
        characterLayer = new Group();
        playerLayer = new Group();
        camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        camera.getChildren().add(floorLayer);
        camera.getChildren().add(characterLayer);
        camera.getChildren().add(playerLayer);

        // Foreground
        uiLayer = new Group();

        SkyBox skyBox = new SkyBox(WINDOW_WIDTH, WINDOW_HEIGHT);
        bgLayer.getChildren().add(skyBox);

        floor = new Floor(FLOOR_WIDTH, FLOOR_HEIGHT);
        floor.setTranslateY(WINDOW_HEIGHT);
        floorLayer.getChildren().add(floor);

        player = new Player();
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - EnemyManager.ENEMY_HEIGHT / 2.);
        playerLayer.getChildren().add(player);

        this.ui = new UI(WINDOW_WIDTH, WINDOW_HEIGHT, TIME_TO_LIVE_S);
        uiLayer.getChildren().add(ui);

        Group root = new Group();
        // Background
        root.getChildren().add(bgLayer);
        // Middle-ground
        root.getChildren().add(camera);
        // Foreground
        root.getChildren().add(uiLayer);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    void initManagers() {
        SpawnerConfig platformSpawnerConfig = new SpawnerConfig(
                WINDOW_WIDTH + 200.0,
                4 * EnemyManager.ENEMY_HEIGHT,
                WINDOW_HEIGHT - (FLOOR_HEIGHT + 1.2 * EnemyManager.ENEMY_HEIGHT),
                PLATFORM_SPAWN_COOLDOWN
        );
        platformManager = new PlatformManager(platformSpawnerConfig, floorLayer);

        SpawnerConfig enemySpawnerconfig = new SpawnerConfig(
                WINDOW_WIDTH + EnemyManager.ENEMY_WIDTH,
                WINDOW_HEIGHT - FLOOR_HEIGHT - EnemyManager.ENEMY_HEIGHT / 2.,
                WINDOW_HEIGHT - FLOOR_HEIGHT - EnemyManager.ENEMY_HEIGHT / 2.,
                ENEMY_SPAWN_COOLDOWN
        );
        enemyManager = new EnemyManager(enemySpawnerconfig, characterLayer);

        SpawnerConfig cloudSpawnConfig = new SpawnerConfig(
                WINDOW_WIDTH + 200.0,
                0.0,
                WINDOW_HEIGHT * 0.5,
                CLOUD_SPAWN_COOLDOWN
        );
        cloudManager = new CloudManager(cloudSpawnConfig, bgLayer);

        SpawnerConfig birdSpawnConfig = new SpawnerConfig(
                WINDOW_WIDTH + 200.0,
                0.0,
                WINDOW_HEIGHT * 0.33,
                BIRD_SPAWN_COOLDOWN
        );
        birdManager = new BirdManager(birdSpawnConfig, bgLayer);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
