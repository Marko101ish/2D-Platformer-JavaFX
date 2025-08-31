package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.sprites.Bird;
import rs.etf.dz1.utils.SpriteSheet;

public class BirdManager  extends SpriteManager<Bird> {
    private static final String BIRD_SPRITE_SHEET_FILE = "/rs/etf/dz1/images/bird_sprite_sheet.png";
    private static final int SHEET_ROWS = 2;
    private static final int SHEET_COLS = 3;

    private SpriteSheet birdSpriteSheet;

    public BirdManager(SpawnerConfig config, Group layer) {
        super(config, layer);

        birdSpriteSheet = SpriteSheet.createSpriteSheet(BIRD_SPRITE_SHEET_FILE, SHEET_ROWS,  SHEET_COLS);
    }

    @Override
    protected Bird createSprite() {
        if (birdSpriteSheet == null) {
            System.out.println("Couldn't create a bird, spriteSheet is null");
            return null;
        }

        return new Bird(birdSpriteSheet);
    }
}
