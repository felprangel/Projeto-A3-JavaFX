package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskItemController {
    @FXML
    private Label taskLabel;

    public void setTaskName(String name) {
        taskLabel.setText(name);
    }
}
