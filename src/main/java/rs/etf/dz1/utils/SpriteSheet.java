package rs.etf.dz1.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import rs.etf.dz1.main.Main;

import java.net.URL;

public class SpriteSheet {
    private Image image;
    private Rectangle2D[] frames;

    public static SpriteSheet createSpriteSheet(String spriteSheetFile, int rows, int cols) {
        URL spriteSheetUrl = Main.class.getResource(spriteSheetFile);
        if (spriteSheetUrl == null) {
            System.err.println("File not found, couldn't create sprite sheet for " + spriteSheetFile);
            return null;
        }

        Image image = new Image(spriteSheetUrl.toString());
        return new SpriteSheet(image,  rows, cols);
    }

    public Rectangle2D[] getFrames() {
        return frames;
    }

    public ImageView createImageView() {
        ImageView imageView = new ImageView(image);

        imageView.setViewport(frames[0]);
        return imageView;
    }

    private SpriteSheet(Image image, int rows, int cols) {
        this.image = image;

        frames = new Rectangle2D[rows * cols];
        final int spriteWidth = (int) (image.getWidth() / cols);
        final int spriteHeight = (int) (image.getHeight() / rows);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                frames[i * cols + j] = new Rectangle2D(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
        }
    }
}
