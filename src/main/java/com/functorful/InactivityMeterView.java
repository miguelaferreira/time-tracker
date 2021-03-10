package com.functorful;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
public class InactivityMeterView extends VBox {

    private final HBox hbTimeManagement = new HBox();
    private final HBox hbTimeManagementButtons = new HBox();
    private final Label lblTimeManagement = new Label("Time management");
    private final Button btnKeepTime = new Button("Keep");
    private final Button btnRemoveTime = new Button("Remove");
    private final Label lblInactivityThreshold = new Label("Inactivity Threshold");
    private final TextField txfInactivityThreshold = new TextField("5");
    private final Label lblInactivityThresholdUnit = new Label("seconds");
    private final Label lblInactivityCounter = new Label("Inactivity Counter");
    private final Label lblTime = new Label("00:00:00");

    private final TimerViewModel timerViewModel = new TimerViewModel();
    private final InactivityMeterViewModel inactivityMeterViewModel = new InactivityMeterViewModel();
    private final GlobalActionListener listener;
    private final LocalTimeStringConverter localTimeStringConverter = new LocalTimeStringConverter(FormatStyle.MEDIUM, Locale.getDefault());

    public InactivityMeterView(GlobalActionListener listener) {
        this.listener = listener;
        createView();
        bindViewModel();
    }

    private void bindViewModel() {
        Bindings.bindBidirectional(
                txfInactivityThreshold.textProperty(),
                inactivityMeterViewModel.thresholdProperty(),
                new NumberStringConverter());

        Bindings.bindBidirectional(
                lblTime.textProperty(),
                timerViewModel.timeProperty(),
                localTimeStringConverter);
    }

    private void createView() {
        final GridPane gp = new GridPane();

        txfInactivityThreshold.setFocusTraversable(false);

        final HBox hbThreshold = new HBox();
        hbThreshold.setSpacing(5);
        hbThreshold.setAlignment(Pos.CENTER_LEFT);
        hbThreshold.getChildren().addAll(txfInactivityThreshold, lblInactivityThresholdUnit);

        gp.setPadding(new Insets(10));
        gp.setHgap(4);
        gp.setVgap(8);
        gp.add(lblTimeManagement, 0, 1);
        gp.add(hbTimeManagementButtons, 1, 1);
        gp.add(lblInactivityCounter, 0, 2);
        gp.add(lblTime, 1, 2);
        gp.add(lblInactivityThreshold, 0, 3);
        gp.add(hbThreshold, 1, 3);

        final ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        gp.getColumnConstraints().addAll(col, col);

        hbTimeManagementButtons.setAlignment(Pos.CENTER_LEFT);
        hbTimeManagementButtons.setSpacing(5);
        hbTimeManagementButtons.getChildren().addAll(btnKeepTime, btnRemoveTime);
        hbTimeManagementButtons.setVisible(false);

        btnKeepTime.setOnAction(this::keepTime);
        btnRemoveTime.setOnAction(this::removeTime);

        this.getChildren().addAll(hbTimeManagement, gp);
        this.setAlignment(Pos.CENTER);
    }

    private void removeTime(ActionEvent actionEvent) {
        final LocalTime time = timerViewModel.getTime();
        log.debug("Removing {} time", localTimeStringConverter.toString(time));
        hideManagementTimeButtons();
        timerViewModel.reset();
        this.fireEvent(RemoveTimeEvent.remove(time));
    }

    private void keepTime(ActionEvent actionEvent) {
        log.debug("Keeping time");
        hideManagementTimeButtons();
        listener.resetLastActivity();
        timerViewModel.reset();
    }

    public void stopTrackingTime() {
        log.debug("Stop event");
        timerViewModel.stop();
        GlobalActionListener.stopListening();
    }

    public void startTrackingTime() {
        log.debug("Start event");
        listener.resetLastActivity();
        GlobalActionListener.startListening(listener);
        timerViewModel.start(this::trackInactiveTime);
    }

    private void trackInactiveTime(Long aLong) {
        final LocalTime now = LocalTime.now();
        final long inactivityDuration = SECONDS.between(listener.getLastActivity(), now);
        log.trace("Clock tick {} => inactive for {}", aLong, inactivityDuration);
        if (inactivityDuration > inactivityMeterViewModel.getThreshold()) {
            log.trace("Inactive");
            Platform.runLater(() -> {
                timerViewModel.addClockTick();
                showManagementTimeButtons();
            });
        } else {
            log.trace("Active");
        }
    }

    private void showManagementTimeButtons() {
        hbTimeManagementButtons.setVisible(true);
    }

    private void hideManagementTimeButtons() {
        hbTimeManagementButtons.setVisible(false);
    }

}
