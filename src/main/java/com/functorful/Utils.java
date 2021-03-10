package com.functorful;

import javafx.util.converter.LocalTimeStringConverter;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Utils {

    public static final DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("HH:mm:ss")
            .toFormatter();

    public static final LocalTimeStringConverter timeConverter = new LocalTimeStringConverter(timeFormatter, timeFormatter);
}
