package com.functorful;

import javafx.event.Event;
import javafx.event.EventType;

public class StopWatchEvent extends Event {

    public static final EventType<StopWatchEvent> START = new EventType<>(ANY, "START");
    public static final EventType<StopWatchEvent> STOP = new EventType<>(ANY, "STOP");
    
    public static StopWatchEvent start() {
        return new StopWatchEvent(START);
    }

    public static StopWatchEvent stop() {
        return new StopWatchEvent(STOP);
    }

    public StopWatchEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
