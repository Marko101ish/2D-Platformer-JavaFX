package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.characters.Enemy;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

public class EnemyManager extends SpriteManager<Enemy> {
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 80;

    public EnemyManager(SpawnerConfig config, Group layer) {
        super(config, layer);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        Player player = Main.getInstance().getPlayer();
        if (!player.isAlive()) {
            return;
        }

        boolean playerIsFalling = player.isFalling();

        ownedSprites.forEach(enemy -> {
            CollisionResult collisionWithPlayer = CollisionHelper.checkCollision(enemy, player);
            // if player is above the enemy, enemy is killed
            if (collisionWithPlayer.inCollision) {
                if (collisionWithPlayer.isBelow() && playerIsFalling) {
                    enemy.takeHit();
                    if (!enemy.isAlive()) {
                        readyForRemoval.add(enemy);
                    }
                }
                else {
                    // player.takeHit();
                }
            }
        });
    }

    @Override
    protected Enemy createSprite() {
        return new Enemy();
    }
}
