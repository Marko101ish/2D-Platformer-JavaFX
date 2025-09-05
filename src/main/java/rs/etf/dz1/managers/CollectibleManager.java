package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.collectibles.Coin;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.sprites.collectibles.Collectible;
import rs.etf.dz1.sprites.collectibles.ImmunityToken;
import rs.etf.dz1.utils.SpriteSheet;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

public class CollectibleManager extends SpriteManager<Collectible> {
    private static final String COIN_SPRITE_SHEET_FILE = "/images/coin_sprite_sheet.png";
    private static final int SHEET_ROWS = 2;
    private static final int SHEET_COLS = 4;
    private static final int BASE_COIN_VALUE = 30;
    // Every time a coin is spawned there's a chance of it being an immunity coin
    private static final double IMMUNITY_TOKEN_PROBABILITY = 0.33;

    private final SpriteSheet coinSpriteSheet;

    public CollectibleManager(SpawnerConfig config, Group layer) {
        super(config, layer);

        coinSpriteSheet = SpriteSheet.createSpriteSheet(COIN_SPRITE_SHEET_FILE, SHEET_ROWS,  SHEET_COLS);
    }

    @Override
    public void update() {
        super.update();

        Player player = Main.getInstance().getPlayer();
        if (!player.isAlive()) {
            return;
        }

        ownedSprites.forEach(collectible -> {
            CollisionResult collisionWithPlayer = CollisionHelper.checkCollision(collectible, player);
            if (collisionWithPlayer.inCollision) {
                collectible.collect(player);
                readyForRemoval.add(collectible);
            }
        });
    }

    @Override
    protected Collectible createSprite() {
        if (randomizer.nextDouble() < IMMUNITY_TOKEN_PROBABILITY) {
            return new ImmunityToken();
        }

        if (coinSpriteSheet == null) {
            System.err.println("Couldn't create a coin, spriteSheet is null");
            return null;
        }

        return new Coin(coinSpriteSheet, BASE_COIN_VALUE);
    }
}
