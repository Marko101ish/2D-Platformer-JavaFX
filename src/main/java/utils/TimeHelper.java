package utils;

public class TimeHelper {
    private static double deltaTime;

    public static final double getDeltaTime() {
        return deltaTime;
    }

    public static void setDeltaTime(double dt) {
        deltaTime = dt;
    }
}
