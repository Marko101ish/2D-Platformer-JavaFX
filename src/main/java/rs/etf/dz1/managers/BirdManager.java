package rs.etf.dz1.managers;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Bird;

import java.net.URL;

public class BirdManager  extends SpriteManager<Bird> {
    private static final String BIRD_SPRITE_SHEET_FILE = "/rs/etf/dz1/images/bird_sprite_sheet.png";
    private static final int SHEET_ROWS = 2;
    private static final int SHEET_COLS = 3;

    private Image spriteSheet;
    Rectangle2D[] spriteBounds = new Rectangle2D[SHEET_ROWS * SHEET_COLS];

    public BirdManager(SpawnerConfig config) {
        super(config);

        URL birdSpriteSheetUrl = Main.class.getResource(BIRD_SPRITE_SHEET_FILE);
        if (birdSpriteSheetUrl == null) {
            System.out.println("Bird image not found, Bird Manager won't be able to spawn any birds");
            return;
        }

        spriteSheet = new Image(birdSpriteSheetUrl.toString());
        final int spriteWidth = (int) (spriteSheet.getWidth() / SHEET_COLS);
        final int spriteHeight = (int) (spriteSheet.getHeight() / SHEET_ROWS);

        for (int i = 0; i < SHEET_ROWS; ++i) {
            for (int j = 0; j < SHEET_COLS; ++j) {
                spriteBounds[i * SHEET_COLS + j] = new Rectangle2D(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
            }
        }
    }

    @Override
    protected Bird createSprite() {
        if (spriteSheet == null) {
            System.out.println("Couldn't create a bird, spriteSheet is null");
            return null;
        }


        return new Bird(spriteSheet, spriteBounds);
    }
}
