package rs.etf.dz1.utils.collisions;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import rs.etf.dz1.sprites.Sprite;
import rs.etf.dz1.utils.TimeHelper;

public class CollisionHelper {

    // Checking collision between two sprites (they need to have the same parent)
    public static CollisionResult checkCollision(Sprite s1, Sprite s2) {
        final double deltaTime = TimeHelper.getDeltaTime();

        // Old translation points
        final Point2D old1 = new Point2D(s1.getTranslateX(), s1.getTranslateY());
        final Point2D old2 = new Point2D(s2.getTranslateX(), s2.getTranslateY());

        // simulate first sprite movement
        s1.setTranslateX(old1.getX() + s1.getVelocityX() * deltaTime);
        s1.setTranslateY(old1.getY() + s1.getVelocityY() * deltaTime);

        // simulate second sprite movement
        s2.setTranslateX(old2.getX() + s2.getVelocityX() * deltaTime);
        s2.setTranslateY(old2.getY() + s2.getVelocityY() * deltaTime);

        Bounds b1 = s1.getBoundsInParent();
        Bounds b2 = s2.getBoundsInParent();

        boolean inCollision = b1.intersects(b2);
        Point2D c1 = new Point2D(b1.getCenterX(), b1.getCenterY());
        Point2D c2 = new Point2D(b2.getCenterX(), b2.getCenterY());
        CollisionResult result = new CollisionResult(inCollision, c1.subtract(c2));

        // revert first sprite translations
        s1.setTranslateX(old1.getX());
        s1.setTranslateY(old1.getY());

        // revert second sprite translations
        s2.setTranslateX(old2.getX());
        s2.setTranslateY(old2.getY());

        return result;
    }
}
