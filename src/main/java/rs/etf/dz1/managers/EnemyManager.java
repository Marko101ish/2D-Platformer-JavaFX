package rs.etf.dz1.managers;

import javafx.scene.Group;
import rs.etf.dz1.main.Main;
import rs.etf.dz1.sprites.characters.Enemy;
import rs.etf.dz1.sprites.characters.Player;
import rs.etf.dz1.utils.collisions.CollisionHelper;
import rs.etf.dz1.utils.collisions.CollisionResult;

public class EnemyManager extends SpriteManager<Enemy> {
    private static int ENEMY_DEATH_POINTS = 15;

    public EnemyManager(SpawnerConfig config, Group layer) {
        super(config, layer);
    }

    @Override
    public void update() {
        super.update();

        Player player = Main.getInstance().getPlayer();
        if (!player.isAlive()) {
            return;
        }

        boolean playerIsFalling = player.isFalling();

        ownedSprites.forEach(enemy -> {
            CollisionResult collisionWithPlayer = CollisionHelper.checkCollision(enemy, player);
            // if player is above the enemy, enemy is killed
            if (collisionWithPlayer.inCollision) {
                if (player.isImmune() || (collisionWithPlayer.isBelow() && playerIsFalling)) {
                    enemy.takeHit();
                    if (!enemy.isAlive()) {
                        Main.getInstance().addPoints(ENEMY_DEATH_POINTS);
                        readyForRemoval.add(enemy);
                    }
                }
                else {
                      player.takeHit();
                }
            }
        });
    }

    @Override
    protected Enemy createSprite() {
        return new Enemy();
    }
}
