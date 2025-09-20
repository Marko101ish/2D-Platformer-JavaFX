package sprites.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import sprites.Sprite;

import java.net.URL;

public class HealthHUD extends Sprite {
    private static final String HEART_IMAGE = "/images/heart.png";
    private static final double HEART_OFFSET = 10.0;
    private Image heartImage;
    public HealthHUD() {
        URL heartImageUrl = Main.class.getResource(HEART_IMAGE);
        if (heartImageUrl == null) {
            System.err.println("File not found: " + HEART_IMAGE +", couldn't create health hud");
            return;
        }

        heartImage = new Image(heartImageUrl.toString());
    }

    public void setLives(int numOfLives) {
        getChildren().clear();
        for (int i = 0; i < numOfLives; i++) {
            ImageView heart = new ImageView(heartImage);
            heart.setTranslateX((i+1) * -(heartImage.getWidth() + HEART_OFFSET));
            getChildren().add(heart);
        }
    }

}
