package com.functorful;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Timer {

    private final Flowable<Long> clock;
    private Disposable timer = null;

    public Timer(TimeUnit units) {
        clock = Flowable.interval(1, units);
    }

    public void start(Consumer<Long> onTick) {
        timer = clock.subscribe(onTick::accept);
    }

    public void stop() {
        if (timer != null) {
            timer.dispose();
            timer = null;
        }
    }
}
