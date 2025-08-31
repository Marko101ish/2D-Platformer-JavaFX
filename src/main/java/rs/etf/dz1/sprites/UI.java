/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.dz1.sprites;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.time.Duration;

/**
 *
 * @author om180345d
 */
public class UI extends Sprite {
    private static final double MAX_FONT_SIZE = 20.0;

    private double timeLeft;
    private final Label gameTimeLabel;
    private final Label fpsLabel;

    public UI(int width, int height, double timeToLive) {
        gameTimeLabel = new Label();
        gameTimeLabel.setTranslateX(width / 2.0);
        gameTimeLabel.setTranslateY(20);
        gameTimeLabel.setAlignment(Pos.CENTER);
        gameTimeLabel.setFont(new Font(MAX_FONT_SIZE));
        timeLeft = timeToLive;

        fpsLabel = new Label();
        fpsLabel.setTranslateX(60);
        fpsLabel.setTranslateY(20);
        fpsLabel.setFont(new Font(MAX_FONT_SIZE));

        getChildren().add(gameTimeLabel);
        getChildren().add(fpsLabel);
    }

    // timeLeft is in seconds
    public void setTimeLeft(double timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    @Override
    public void update(double deltaTime) {
        updateTimeLeft();
        // fpsLabel.setText("FPS: " + 1./deltaTime);
    }

    private void updateTimeLeft() {
        Duration dur = Duration.ofSeconds((long)timeLeft);
        int minutes = dur.toMinutesPart();
        int seconds = dur.toSecondsPart();

        String formattedDuration = String.format("%02d:%02d", minutes, seconds);
        gameTimeLabel.setText("Time left: " + formattedDuration);
    }
}
