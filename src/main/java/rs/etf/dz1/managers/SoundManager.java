package rs.etf.dz1.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import rs.etf.dz1.main.Main;

import java.net.URL;

public class SoundManager {
    private String backgroundMusicFile = "/sounds/background.mp3";
    private Media backgroundMusic;
    private MediaPlayer backgroundMusicPlayer;

    public SoundManager() {
        URL resource = Main.class.getResource(backgroundMusicFile);
        if (resource == null) {
            System.err.println("Failed to load background.mp3");
            return;
        }

        backgroundMusic = new Media(resource.toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.play();
    }
}
