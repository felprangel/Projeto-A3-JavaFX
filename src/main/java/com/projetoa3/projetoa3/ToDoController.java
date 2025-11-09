package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ToDoController {

    private int counter;

    @FXML
    private Label taskCount;

    @FXML
    private TextField taskInput;

    @FXML
    private VBox taskListContainer;

    @FXML
    protected void addTask() {
        counter++;
        taskCount.setText(String.valueOf(counter));

        String taskName = taskInput.getText();

        if (taskName == null || taskName.trim().isEmpty()) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskItem.fxml"));

            Node taskItem = loader.load();

            TaskItemController taskController = loader.getController();
            taskController.setTaskName(taskName);

            taskListContainer.getChildren().add(taskItem);

            taskInput.clear();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar TaskItem.fxml");
        }
    }
}
