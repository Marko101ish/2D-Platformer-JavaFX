package rs.etf.dz1.utils;

public interface IUpdatable {
    // deltaTime is in seconds
    //
    // when moving objects make sure to take deltaTime into account
    // so the simulation runs smoothly even with inconsistent frame rate
    void update(double deltaTime);
}
