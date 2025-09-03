package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.Coin;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.utils.SpriteSheet;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

public class CoinManager  extends SpriteManager<Coin> {
    private static final String COIN_SPRITE_SHEET_FILE = "/rs/etf/dz1/images/coin_sprite_sheet.png";
    private static final int SHEET_ROWS = 2;
    private static final int SHEET_COLS = 4;
    private static final int COIN_VALUE = 30;

    private final SpriteSheet coinSpriteSheet;

    public CoinManager(SpawnerConfig config, Group layer) {
        super(config, layer);

        coinSpriteSheet = SpriteSheet.createSpriteSheet(COIN_SPRITE_SHEET_FILE, SHEET_ROWS,  SHEET_COLS);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        Player player = Main.getInstance().getPlayer();
        if (!player.isAlive()) {
            return;
        }

        ownedSprites.forEach(coin -> {
            CollisionResult collisionWithPlayer = CollisionHelper.checkCollision(coin, player);
            if (collisionWithPlayer.inCollision) {
                Main.getInstance().addPoints(coin.getCoinValue());
                readyForRemoval.add(coin);
            }
        });
    }

    @Override
    protected Coin createSprite() {
        if (coinSpriteSheet == null) {
            System.out.println("Couldn't create a coin, spriteSheet is null");
            return null;
        }

        return new Coin(coinSpriteSheet, COIN_VALUE);
    }
}
