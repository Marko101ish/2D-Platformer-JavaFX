package rs.etf.dz1.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

public class FXEvent extends Event {
    public static final EventType<FXEvent> ADD =
            new EventType<>(Event.ANY, "ADD");
    public static final EventType<FXEvent> REMOVE =
            new EventType<>(Event.ANY, "REMOVE");

    public Node fxNode;
    public FXEvent(EventType<FXEvent> type, Node fxNode) {
        super(type);
        this.fxNode = fxNode;
    }
}
