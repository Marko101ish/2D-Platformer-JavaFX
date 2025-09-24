package events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

public class FXEvent extends Event {
    public static final EventType<FXEvent> ANY =
            new EventType<>(Event.ANY, "ANY");
    public static final EventType<FXEvent> ADD =
            new EventType<>(ANY, "ADD");
    public static final EventType<FXEvent> REMOVE =
            new EventType<>(ANY, "REMOVE");

    public Node fxNode;
    public FXEvent(EventType<FXEvent> type, Node fxNode) {
        super(type);
        this.fxNode = fxNode;
    }
}
