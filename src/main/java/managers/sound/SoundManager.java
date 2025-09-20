package managers.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Main;

import java.net.URL;

public class SoundManager {
    private MediaPlayer backgroundMusic;
    private MediaPlayer playerDeathSound;
    private MediaPlayer enemyDeathSound;
    private MediaPlayer coinCollectedSound;
    private MediaPlayer immunityGainedSound;
    private MediaPlayer immunityLostSound;
    private float[] channels;

    public SoundManager() {
        channels = new float[SoundChannel.values().length];
        channels[SoundChannel.MUSIC.ordinal()] = 0.1f;
        channels[SoundChannel.SFX.ordinal()] = 1.0f;

        backgroundMusic = createPlayer("/sounds/background.mp3", SoundChannel.MUSIC, true, true);
        playerDeathSound = createPlayer("/sounds/player_death.mp3", SoundChannel.SFX, false, false);
        enemyDeathSound = createPlayer("/sounds/enemy_death.mp3", SoundChannel.SFX, false, false);
        coinCollectedSound = createPlayer("/sounds/coin_collected.mp3", SoundChannel.SFX, false, false);
        immunityGainedSound = createPlayer("/sounds/immunity_gained.mp3", SoundChannel.SFX, false, false);
        immunityLostSound = createPlayer("/sounds/immunity_lost.mp3", SoundChannel.SFX, false, false);
    }

    public void playCoinCollectedSound() {
        playSound(coinCollectedSound);
    }

    public void playImmunityGainedSound() {
        playSound(immunityGainedSound);
    }

    public void playImmunityLostSound() {
        playSound(immunityLostSound);
    }

    public void playPlayerDeathSound() {
        playSound(playerDeathSound);
    }

    public void playEnemyDeathSound() {
        playSound(enemyDeathSound);
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

    private void playSound(MediaPlayer player) {
        if (player == null) {
            return;
        }

        if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            player.stop();
        }

        player.play();
    }
}
