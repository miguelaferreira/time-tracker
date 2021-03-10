package com.functorful;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void timeFormatter() {
        assertThat(LocalTime.of(0, 0, 0).format(Utils.timeFormatter))
                .isEqualTo("00:00:00");
        assertThat(LocalTime.of(1, 30, 5).format(Utils.timeFormatter))
                .isEqualTo("01:30:05");
    }
}
