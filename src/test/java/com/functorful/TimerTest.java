package com.functorful;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class TimerTest {

    @Test
    void testTimer() throws InterruptedException {
        final Timer timer = new Timer(TimeUnit.SECONDS);
        final ArrayList<Long> longs = new ArrayList<>();

        timer.start(longs::add);
        Thread.sleep(3000);
        timer.stop();

        assertThat(longs)
                .isNotEmpty()
                .containsExactly(longs.stream().sorted().collect(toList()).toArray(new Long[]{}));
    }
}
