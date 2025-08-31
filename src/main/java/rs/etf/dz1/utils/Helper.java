package rs.etf.dz1.utils;

import javafx.scene.Node;

public class Helper {

    // Checking collision between two sprites (they need to have the same parent)
    public static boolean CheckCollision(Node s1, Node s2) {
        return s1.getBoundsInParent().intersects(s2.getBoundsInParent());
    }
}
