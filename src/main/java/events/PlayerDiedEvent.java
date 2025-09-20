package events;

import javafx.event.Event;
import javafx.event.EventType;

public class PlayerDiedEvent extends Event {
    public static final EventType<PlayerDiedEvent> PLAYER_DIED_EVENT =
            new EventType<>(Event.ANY, "PLAYER_DIED_EVENT");

    public PlayerDiedEvent() {
        super(PLAYER_DIED_EVENT);
    }
}
