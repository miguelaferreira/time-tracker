package com.functorful;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class InactivityActions extends HBox {

    public InactivityActions() {
        this.setPadding(new Insets(10, 10, 10, 10));
        final Button ignore = new Button("Ignore");
        final Button removeTime = new Button("Remove time");
        this.getChildren().addAll(ignore, removeTime);
        this.setVisible(false);
    }

}
