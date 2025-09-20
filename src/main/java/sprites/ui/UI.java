/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import sprites.Sprite;
import utils.TimeHelper;

import java.time.Duration;

/**
 *
 * @author om180345d
 */
public class UI extends Sprite {
    private static final double MAX_FONT_SIZE = 20.0;
    private static final double TOP_MARGIN = 20.0;
    private static final double IMMUNITY_HUD_OFFSET_X = 80.0;
    private static final double IMMUNITY_HUD_OFFSET_Y = 150.0;
    private int timeLeft = 0;
    private int score = 0;

    private final Label gameTimeLabel;
    private final Label scoreLabel;
    private final Label fpsLabel;
    private HealthHUD healthHUD;
    private ImmunityHUD immunityHUD;

    public UI(int width, int height, double timeToLive) {
        Font uiFont = new Font(MAX_FONT_SIZE);

        gameTimeLabel = new Label();
        gameTimeLabel.setTranslateX(width / 2.0);
        gameTimeLabel.setTranslateY(TOP_MARGIN);
        gameTimeLabel.setAlignment(Pos.CENTER);
        gameTimeLabel.setFont(uiFont);
        timeLeft = (int) timeToLive;

        scoreLabel = new Label();
        scoreLabel.setTranslateX(60.0);
        scoreLabel.setTranslateY(TOP_MARGIN);
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setFont(uiFont);

        fpsLabel = new Label();
        fpsLabel.setTranslateX(60);
        fpsLabel.setTranslateY(TOP_MARGIN);
        fpsLabel.setFont(uiFont);

        healthHUD = new HealthHUD();
        healthHUD.setTranslateX(width);
        healthHUD.setTranslateY(TOP_MARGIN);

        immunityHUD = new ImmunityHUD();
        immunityHUD.setTranslateX(width - IMMUNITY_HUD_OFFSET_X);
        immunityHUD.setTranslateY(IMMUNITY_HUD_OFFSET_Y);

        getChildren().add(gameTimeLabel);
        getChildren().add(scoreLabel);
        getChildren().add(healthHUD);
        getChildren().add(immunityHUD);
        // getChildren().add(fpsLabel);
    }

    // timeLeft is in seconds
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNumOfLives(int lives) {
        healthHUD.setLives(lives);
    }

    public void startImmunity(double duration) {
        immunityHUD.startImmunity(duration);
    }

    public void stopImmunity() {
        immunityHUD.stopImmunity();
    }

    @Override
    public void update() {
        updateTimeLeft();
        updateScore();
        healthHUD.update();
        immunityHUD.update();

        final double deltaTime = TimeHelper.getDeltaTime();
        fpsLabel.setText("FPS: " + 1./deltaTime);
    }

    private void updateTimeLeft() {
        Duration dur = Duration.ofSeconds((long)timeLeft);
        int minutes = dur.toMinutesPart();
        int seconds = dur.toSecondsPart();

        String formattedDuration = String.format("%02d:%02d", minutes, seconds);
        gameTimeLabel.setText("Time left: " + formattedDuration);
    }

    private void updateScore() {
        scoreLabel.setText("Points earned: " + score);
    }

}
