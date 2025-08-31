package rs.etf.dz1.managers;

import rs.etf.dz1.sprites.Enemy;
import rs.etf.dz1.sprites.Player;

public class EnemyManager extends SpriteManager<Enemy> {

    private static final double ENEMY_SPEED = 350.0;
    private Player player;

    public EnemyManager(SpawnerConfig config) {
        super(config);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        ownedSprites.forEach(e -> {
            if (player.getBoundsInParent().intersects(e.getBoundsInParent())) {
                if (!player.isDead()) {
                    player.takeHit();
                }
            }
        });
    }

    @Override
    protected Enemy createSprite() {
        return new Enemy(ENEMY_SPEED);
    }
}
