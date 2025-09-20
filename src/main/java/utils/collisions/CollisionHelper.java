package utils.collisions;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import sprites.Sprite;
import utils.TimeHelper;

public class CollisionHelper {

    // Checking collision between two sprites (they need to have the same parent)
    public static CollisionResult checkCollision(Sprite s1, Sprite s2) {
        Bounds b1 = getBoundsInNextFrame(s1);
        Bounds b2 = getBoundsInNextFrame(s2);

        boolean inCollision = b1.intersects(b2);
        Point2D c1 = new Point2D(b1.getCenterX(), b1.getCenterY());
        Point2D c2 = new Point2D(b2.getCenterX(), b2.getCenterY());
        return new CollisionResult(inCollision, c1.subtract(c2));
    }

    // Returns true if s1 contains s2 on the X axis
    public static boolean containsX(Sprite s1, Sprite s2) {
        Bounds b1 = getBoundsInNextFrame(s1);
        Bounds b2 = getBoundsInNextFrame(s2);

        return b1.getMinX() < b2.getMinX() && b1.getMaxX() > b2.getMaxX();
    }

    private static Bounds getBoundsInNextFrame(Sprite sprite) {
        final double deltaTime = TimeHelper.getDeltaTime();

        // Old translation points
        final Point2D oldPos = new Point2D(sprite.getTranslateX(), sprite.getTranslateY());
        // simulate movement
        sprite.setTranslateX(oldPos.getX() + sprite.getVelocityX() * deltaTime);
        sprite.setTranslateY(oldPos.getY() + sprite.getVelocityY() * deltaTime);

        Bounds nextFrameBounds = sprite.getBoundsInParent();
        // revert sprite translations
        sprite.setTranslateX(oldPos.getX());
        sprite.setTranslateY(oldPos.getY());

        return nextFrameBounds;
    }
}
