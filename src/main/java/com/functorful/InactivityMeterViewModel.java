package com.functorful;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InactivityMeterViewModel {

    private final IntegerProperty threshold = new SimpleIntegerProperty(5);

    public int getThreshold() {
        return threshold.get();
    }

    public IntegerProperty thresholdProperty() {
        return threshold;
    }
}
