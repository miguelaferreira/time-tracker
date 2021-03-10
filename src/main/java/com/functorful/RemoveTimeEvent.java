package com.functorful;

import javafx.event.Event;
import javafx.event.EventType;

import java.time.LocalTime;

public class RemoveTimeEvent extends Event {

    public static final EventType<RemoveTimeEvent> REMOVE = new EventType<>(ANY, "REMOVE");

    public static RemoveTimeEvent remove(LocalTime timeToRemove) {
        return new RemoveTimeEvent(REMOVE, timeToRemove);
    }

    private final LocalTime timeToRemove;

    public RemoveTimeEvent(EventType<? extends Event> eventType, LocalTime timeToRemove) {
        super(eventType);
        this.timeToRemove = timeToRemove;
    }

    public LocalTime getTimeToRemove() {
        return timeToRemove;
    }
}
