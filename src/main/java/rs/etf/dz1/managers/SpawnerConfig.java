package rs.etf.dz1.managers;

// spawnX defines the X coordinate of the spawn point
// minSpawnY and maxSpawnY define the range of the Y coordinate of the spawn point
public record SpawnerConfig(
        double spawnX,
        double minSpawnY,
        double maxSpawnY,
        double spawnCooldown,
        double initialCooldown) {

    public SpawnerConfig(
            double spawnX,
            double minSpawnY,
            double maxSpawnY,
            double spawnCooldown
    ) {
        this(spawnX, minSpawnY, maxSpawnY, spawnCooldown, 0.0);
    }
}
