package com.functorful;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TimeTrackerApplication extends Application {

    private final GlobalActionListener listener = new GlobalActionListener();

    @Override
    public void start(Stage stage) {
        stage.setScene(buildScene());
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        GlobalActionListener.stopListening();
        super.stop();
    }

    public static void main(String[] args) {
        Logger LOG = (Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        LOG.setLevel(Level.INFO);
        launch();
    }

    private Scene buildScene() {
        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        final StopWatchView stopWatchView = new StopWatchView(true);
        final InactivityMeterView inactivityMeterView = new InactivityMeterView(listener);

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label helloWorld = new Label("Time Tracker, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        vBox.getChildren().addAll(stopWatchView, inactivityMeterView, helloWorld);

        wireEvents(vBox, stopWatchView, inactivityMeterView);

        return new Scene(new StackPane(vBox), 520, 300);
    }

    private void wireEvents(VBox vBox, StopWatchView stopWatchView, InactivityMeterView inactivityMeterView) {
        vBox.addEventHandler(StopWatchEvent.START, event -> inactivityMeterView.startTrackingTime());
        vBox.addEventHandler(StopWatchEvent.STOP, event -> inactivityMeterView.stopTrackingTime());
        vBox.addEventHandler(RemoveTimeEvent.REMOVE, stopWatchView::removeTime);
    }
}
