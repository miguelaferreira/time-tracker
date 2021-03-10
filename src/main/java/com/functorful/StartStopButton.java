package com.functorful;

import javafx.scene.control.Button;

public class StartStopButton extends Button {

    private static final String START = "Start";
    private static final String STOP = "Stop";

    public StartStopButton() {
        super(START);
    }

    @Override
    public void fire() {
        super.fire();
        if (START.equals(this.getText())) {
            this.fireEvent(StopWatchEvent.start());
            this.setText(STOP);
        } else {
            this.fireEvent(StopWatchEvent.stop());
            this.setText(START);
        }
    }
}
