package com.functorful;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopWatchView extends GridPane {

    public static final Font FONT = new Font(24);
    private final StartStopButton btnStartStop = new StartStopButton();
    private final Label lblTime = new Label("00:00:00");

    private final TimerViewModel viewModel = new TimerViewModel();

    public StopWatchView(boolean showButton) {
        createView(showButton);
        bindViewModel();
    }

    private void bindViewModel() {
        Bindings.bindBidirectional(
                lblTime.textProperty(),
                viewModel.timeProperty(),
                Utils.timeConverter);
    }

    private void createView(boolean showButton) {
        this.setPadding(new Insets(10));
        this.setHgap(4);
        this.setVgap(8);
        this.add(btnStartStop, 0, 1);
        this.add(lblTime, 1, 1);

        btnStartStop.setDefaultButton(true);
        wireStopStartEventHandlers();

        final ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        this.getColumnConstraints().addAll(col, col);

        lblTime.setFont(FONT);
        btnStartStop.setFont(FONT);
    }

    private void wireStopStartEventHandlers() {
        this.addEventHandler(StopWatchEvent.START, event -> {
            log.debug("Start event");
            viewModel.startOnJavaFx();
        });

        this.addEventHandler(StopWatchEvent.STOP, event -> {
            log.debug("Stop event");
            viewModel.stop();
        });
    }

    public void removeTime(RemoveTimeEvent event) {
        log.debug("Remove time event");
        viewModel.removeTime(event.getTimeToRemove());
    }
}
