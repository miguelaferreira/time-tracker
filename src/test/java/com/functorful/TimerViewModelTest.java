package com.functorful;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class TimerViewModelTest {

    @BeforeAll
    static void beforeAll() {
        Logger LOG = (Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        LOG.setLevel(Level.TRACE);
    }

    @Test
    void newModel() {
        final TimerViewModel timerViewModel = new TimerViewModel();

        assertThat(timerViewModel.getTime()).isEqualTo(TimerViewModel.ZERO_TIME);
    }

    @Test
    void setTime() {
        final LocalTime newTime = LocalTime.of(1, 1, 1);
        final TimerViewModel timerViewModel = new TimerViewModel();
        timerViewModel.setTime(newTime);

        assertThat(timerViewModel.getTime()).isEqualTo(newTime);
    }

    @Test
    void resetTime() {
        final LocalTime newTime = LocalTime.of(1, 1, 1);
        final TimerViewModel timerViewModel = new TimerViewModel();
        timerViewModel.setTime(newTime);
        timerViewModel.reset();

        assertThat(timerViewModel.getTime()).isEqualTo(TimerViewModel.ZERO_TIME);
    }

    @Test
    void addClockTick() {
        final TimerViewModel timerViewModel = new TimerViewModel();
        timerViewModel.addClockTick();
        timerViewModel.addClockTick();

        assertThat(timerViewModel.getTime()).isEqualTo(TimerViewModel.ZERO_TIME.plusSeconds(2));
    }

    @Test
    void removeTime() {
        final LocalTime newTime = LocalTime.of(2, 2, 2);
        final TimerViewModel timerViewModel = new TimerViewModel();
        timerViewModel.setTime(newTime);
        timerViewModel.removeTime(LocalTime.of(1, 1, 1));

        assertThat(timerViewModel.getTime()).isEqualTo(LocalTime.of(1, 1, 1));
    }

    @Test
    void startStop() throws InterruptedException {
        final TimerViewModel timerViewModel = new TimerViewModel();
        timerViewModel.start();
        Thread.sleep(2000);
        timerViewModel.stop();

        assertThat(timerViewModel.getTime()).isAfter(TimerViewModel.ZERO_TIME);
    }

    @Test
    void startStop_whenNotCollectingTime() throws InterruptedException {
        final TimerViewModel timerViewModel = new TimerViewModel();
        final Consumer<Long> doNothing = aLong -> {
        };
        timerViewModel.start(doNothing);
        Thread.sleep(2000);
        timerViewModel.stop();

        assertThat(timerViewModel.getTime()).isEqualTo(TimerViewModel.ZERO_TIME);
    }


}
