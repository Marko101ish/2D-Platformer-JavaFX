package rs.etf.dz1.utils.collisions;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import rs.etf.dz1.sprites.Sprite;

public class CollisionHelper {

    // Checking collision between two sprites (they need to have the same parent)
    public static CollisionResult checkCollision(Sprite s1, Sprite s2, double deltaTime) {
        Bounds b1 = s1.getBoundsInParent();
        Bounds b2 = s2.getBoundsInParent();

        boolean inCollision = b1.intersects(b2);

        // negative X -> s1 is to the left of s2
        // positive X -> s1 is to the right of s2
        // negative Y -> s1 is above s2
        // positive Y -> s1 is below s2

        Point2D c1 = new Point2D(b1.getCenterX(), b1.getCenterY());
        Point2D c2 = new Point2D(b2.getCenterX(), b2.getCenterY());
        return new CollisionResult(inCollision, c1.subtract(c2));
    }
}
