package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ToDoController {

    private int counter;

    @FXML
    private Label taskCount;

    @FXML
    protected void addTask() {
        counter++;
        taskCount.setText(String.valueOf(counter));
    }
}
