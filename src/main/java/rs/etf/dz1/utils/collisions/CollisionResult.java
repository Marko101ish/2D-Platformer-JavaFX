package rs.etf.dz1.utils.collisions;

import javafx.geometry.Point2D;

public class CollisionResult {
    public final boolean inCollision;
    public final Point2D direction;

    public CollisionResult(boolean inCollision, Point2D direction) {
        this.inCollision = inCollision;
        this.direction = direction;
    }

    // negative X -> n1 is to the left of n2
    // positive X -> n1 is to the right of n2
    // negative Y -> n1 is above n2
    // positive Y -> n1 is below n2

    public boolean isLeft() {
        return direction.getX() < 0;
    }
    public boolean isRight() {
        return direction.getX() > 0;
    }
    public boolean isAbove() {
        return direction.getY() < 0;
    }
    public boolean isBelow() {
        return direction.getY() > 0;
    }
}
