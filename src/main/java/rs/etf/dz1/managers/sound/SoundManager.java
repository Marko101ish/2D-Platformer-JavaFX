package rs.etf.dz1.managers.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import rs.etf.dz1.main.Main;

import java.net.URL;

public class SoundManager {
    private MediaPlayer backgroundMusic;
    private MediaPlayer playerDeathSound;
    private MediaPlayer enemyDeathSound;
    private float[] channels;

    public SoundManager() {
        channels = new float[SoundChannel.values().length];
        channels[SoundChannel.MUSIC.ordinal()] = 0.1f;
        channels[SoundChannel.SFX.ordinal()] = 1.0f;

        backgroundMusic = createPlayer("/sounds/background.mp3", SoundChannel.MUSIC, true, true);
        playerDeathSound = createPlayer("/sounds/player_death.mp3", SoundChannel.SFX, false, false);
        enemyDeathSound = createPlayer("/sounds/enemy_death.mp3", SoundChannel.SFX, false, false);
    }

    public void playPlayerDeathSound() {
        if (playerDeathSound != null) {
            playerDeathSound.stop();
            playerDeathSound.play();
        }
    }

    public void playEnemyDeathSound() {
        if (enemyDeathSound != null) {
            enemyDeathSound.stop();
            enemyDeathSound.play();
        }
    }

    private MediaPlayer createPlayer(String musicFile, SoundChannel channel, boolean loop, boolean isAutoPlay) {
        URL resource = Main.class.getResource(musicFile);
        if (resource == null) {
            System.err.println("Failed to load file: " + musicFile);
            return null;
        }

        Media media = new Media(resource.toExternalForm());
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(channels[channel.ordinal()]);
        player.setAutoPlay(isAutoPlay);

        if (loop) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
        }

        return player;
    }
}
