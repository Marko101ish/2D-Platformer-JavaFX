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

    private Duration timeLeft;
    private final Label gameTimeLabel;

    public UI(int width, int height, Duration timeToLive) {
        gameTimeLabel = new Label();
        gameTimeLabel.setTranslateX(width / 2.0);
        gameTimeLabel.setTranslateY(20);
        gameTimeLabel.setAlignment(Pos.CENTER);
        gameTimeLabel.setFont(new Font(MAX_FONT_SIZE));
        timeLeft = timeToLive;

        getChildren().add(this.gameTimeLabel);
    }

    public void setTimeLeft(Duration timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    @Override
    public void update(long deltaTime) {
        updateTimeLeft();
    }

    private void updateTimeLeft() {
        int minutes = timeLeft.toMinutesPart();
        int seconds = timeLeft.toSecondsPart();

        String formattedDuration = String.format("%02d:%02d", minutes, seconds);
        gameTimeLabel.setText("Time left: " + formattedDuration);
    }
}
