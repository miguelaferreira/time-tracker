package com.functorful;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class TimerViewModel {

    public static final LocalTime ZERO_TIME = LocalTime.of(0, 0, 0);

    private final ObjectProperty<LocalTime> time = new SimpleObjectProperty<>(ZERO_TIME);
    private final Timer timer = new Timer(TimeUnit.SECONDS);

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public void reset() {
        time.set(ZERO_TIME);
    }

    public void stop() {
        timer.stop();
    }

    public void start(Consumer<Long> onClockTick) {
        timer.start(onClockTick);
    }

    public void start() {
        timer.start(tick -> this.addClockTick());
    }

    public void startOnJavaFx() {
        timer.start(tick -> Platform.runLater(this::addClockTick));
    }

    public void addClockTick() {
        setTime(getTime().plusSeconds(1));
    }

    public void removeTime(LocalTime time) {
        setTime(getTime().minus(Duration.between(ZERO_TIME, time)));
        log.debug("Updated time is {}", getTime());
    }
}
