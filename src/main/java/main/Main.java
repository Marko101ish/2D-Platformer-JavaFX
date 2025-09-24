package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import cameras.Camera;
import events.FXEvent;
import events.PlayerDiedEvent;
import managers.*;
import managers.sound.SoundManager;
import sprites.*;
import sprites.characters.Enemy;
import sprites.characters.Player;
import sprites.ui.UI;
import utils.InvulnerabilityType;
import utils.TimeHelper;

/**
 *
 * @author om180345d
 */

// For disabling annoying warnings when running the app add these into VM options
// --enable-native-access=javafx.graphics
// --enable-native-access=javafx.media
// --sun-misc-unsafe-memory-access=allow

public class Main extends Application implements EventHandler<Event> {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final double PLAYABLE_AREA_MIN_X = 0.0;
    public static final double PLAYABLE_AREA_MAX_X = Main.WINDOW_WIDTH;

    // How far can a player fall before they die
    public static final int FALL_DEATH_OFFSET = 100;
    // All objects in the foreground (except the player) move at the same speed
    public static final double OBJECT_SPEED = 300.0;

    public static final int FLOOR_HEIGHT = 50;

    private static final double PLATFORM_SPAWN_COOLDOWN = 3.2;
    private static final double ENEMY_SPAWN_COOLDOWN = 2.2;
    private static final double CLOUD_SPAWN_COOLDOWN = 1.5;
    private static final double BIRD_SPAWN_COOLDOWN = 4.0;
    private static final double COLLECTIBLE_SPAWN_COOLDOWN = 10.0;

    private static final int PLAYER_STARTING_LIVES = 3;

    public static final String TITLE = "Platformer";

    public static final double TIME_TO_LIVE_S = 120.0;
    public static final double RESPAWN_INVULNERABILITY_DURATION = 3.0;

    private static Main instance;

    private boolean isPaused = false;
    private double timeMultiplier = 1.0;
    private int livesLeft = PLAYER_STARTING_LIVES;

    private Scene scene;
    private Group bgLayer;
    private Group floorLayer;
    private Group characterLayer;
    private Group playerLayer;
    private Group fxLayer;
    private Group uiLayer;

    private Player player;
    private Floor floor;
    private PlatformManager platformManager;
    private EnemyManager enemyManager;
    private CloudManager cloudManager;
    private BirdManager birdManager;
    private CollectibleManager collectibleManager;
    private SoundManager soundManager;
    private UI ui;
    
    private long lastFrameNanoTime;
    private double timeLeft = TIME_TO_LIVE_S;
    private int oldTimeLeft = (int) TIME_TO_LIVE_S;
    private int score = 0;

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

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public UI getUI() {
        return ui;
    }

    public void togglePause() {
        if (isPaused) {
            resumeGame();
        }
        else {
            pauseGame();
        }
    }

    public void speedUpGame() {
        timeMultiplier = Math.min(timeMultiplier + 0.25, 1.75);
    }

    public void slowDownGame() {
        timeMultiplier = Math.max(timeMultiplier - 0.5, 0.25);
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public void addPoints(int points) {
        this.score += points;
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
                deltaTime *= timeMultiplier;
                TimeHelper.setDeltaTime(deltaTime);

                if (!isPaused) {
                    update();
                }
            }
        }.start();
    }

    // called once per frame to update game state
    // deltaTime is in milliseconds here
    private void update() {
        final double deltaTime = TimeHelper.getDeltaTime();
        timeLeft = timeLeft - deltaTime;
        final int secondsPassed = oldTimeLeft - (int)timeLeft;
        if (secondsPassed > 0) {
            score += secondsPassed;
            oldTimeLeft = (int) timeLeft;
        }

        // When there's no time left trigger game over
        if (timeLeft < 0)
        {
            gameOver();
        }

        floor.update();
        player.update();
        platformManager.update();
        enemyManager.update();
        cloudManager.update();
        birdManager.update();
        collectibleManager.update();
        camera.update();

        ui.setTimeLeft((int) timeLeft);
        ui.setScore(score);
        ui.setNumOfLives(livesLeft);
        ui.update();
    }

    private void initScene(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Background
        bgLayer = new Group();

        // Middle-ground
        floorLayer = new Group();
        characterLayer = new Group();
        playerLayer = new Group();
        fxLayer = new Group();
        camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
        camera.getChildren().add(floorLayer);
        camera.getChildren().add(characterLayer);
        camera.getChildren().add(playerLayer);
        camera.getChildren().add(fxLayer);

        // Foreground
        uiLayer = new Group();

        SkyBox skyBox = new SkyBox(WINDOW_WIDTH, WINDOW_HEIGHT);
        bgLayer.getChildren().add(skyBox);

        floor = new Floor((int) PLAYABLE_AREA_MIN_X, (int) PLAYABLE_AREA_MAX_X + FLOOR_HEIGHT, FLOOR_HEIGHT);
        floorLayer.getChildren().add(floor);

        spawnPlayer();

        this.ui = new UI(WINDOW_WIDTH, WINDOW_HEIGHT, TIME_TO_LIVE_S);
        uiLayer.getChildren().add(ui);

        // Background
        root.getChildren().add(bgLayer);
        // Middle-ground
        root.getChildren().add(camera);
        // Foreground
        root.getChildren().add(uiLayer);

        root.addEventHandler(PlayerDiedEvent.PLAYER_DIED_EVENT, this);
        root.addEventHandler(FXEvent.ANY, this);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    private void initManagers() {
        SpawnerConfig platformSpawnerConfig = new SpawnerConfig(
                4 * Enemy.ENEMY_HEIGHT,
                WINDOW_HEIGHT - (FLOOR_HEIGHT + 1.5 * Enemy.ENEMY_HEIGHT),
                PLATFORM_SPAWN_COOLDOWN
        );
        platformManager = new PlatformManager(platformSpawnerConfig, floorLayer);

        SpawnerConfig enemySpawnerconfig = new SpawnerConfig(
                WINDOW_HEIGHT - FLOOR_HEIGHT,
                WINDOW_HEIGHT - FLOOR_HEIGHT,
                ENEMY_SPAWN_COOLDOWN
        );
        enemyManager = new EnemyManager(enemySpawnerconfig, characterLayer);

        SpawnerConfig cloudSpawnConfig = new SpawnerConfig(
                0.0,
                WINDOW_HEIGHT * 0.5,
                CLOUD_SPAWN_COOLDOWN
        );
        cloudManager = new CloudManager(cloudSpawnConfig, bgLayer);

        SpawnerConfig birdSpawnConfig = new SpawnerConfig(
                0.0,
                WINDOW_HEIGHT * 0.33,
                BIRD_SPAWN_COOLDOWN
        );
        birdManager = new BirdManager(birdSpawnConfig, bgLayer);

        SpawnerConfig collectibleSpawnerconfig = new SpawnerConfig(
                WINDOW_HEIGHT - FLOOR_HEIGHT - Enemy.ENEMY_HEIGHT * 0.2,
                WINDOW_HEIGHT - FLOOR_HEIGHT - Enemy.ENEMY_HEIGHT * 0.2,
                COLLECTIBLE_SPAWN_COOLDOWN,
                COLLECTIBLE_SPAWN_COOLDOWN
        );

        collectibleManager = new CollectibleManager(collectibleSpawnerconfig, playerLayer);

        soundManager = new SoundManager();
    }

    private void spawnPlayer() {
        playerLayer.getChildren().remove(player);

        player = new Player();
        player.setTranslateX(100);
        player.setTranslateY(WINDOW_HEIGHT - FLOOR_HEIGHT - Enemy.ENEMY_HEIGHT / 2.);

        playerLayer.getChildren().add(player);
        camera.setTarget(player);

        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);
    }

    private void gameOver() {
        System.exit(0);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof PlayerDiedEvent) {
            if (--livesLeft > 0) {
                spawnPlayer();

                player.giveInvulnerability(InvulnerabilityType.RESPAWN, RESPAWN_INVULNERABILITY_DURATION);

                resumeGame();
            }
            else {
                gameOver();
            }
        }
        if (event instanceof FXEvent) {
            Node fxNode = ((FXEvent) event).fxNode;
            if (event.getEventType() == FXEvent.ADD) {
                fxLayer.getChildren().add(fxNode);
            }
            else {
                fxLayer.getChildren().remove(fxNode);
            }

        }
    }
}
