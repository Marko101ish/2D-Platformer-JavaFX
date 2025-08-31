package rs.etf.dz1.utils.collisions;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class CollisionHelper {

    // Checking collision between two sprites (they need to have the same parent)
    public static CollisionResult checkCollision(Node n1, Node n2) {
        Bounds b1 = n1.getBoundsInParent();
        Bounds b2 = n2.getBoundsInParent();

        boolean inCollision = b1.intersects(b2);

        // negative X -> n1 is to the left of n2
        // positive X -> n1 is to the right of n2
        // negative Y -> n1 is above n2
        // positive Y -> n1 is below n2

        Point2D c1 = new Point2D(b1.getCenterX(), b1.getCenterY());
        Point2D c2 = new Point2D(b2.getCenterX(), b2.getCenterY());
        return new CollisionResult(inCollision, c1.subtract(c2));
    }
}
