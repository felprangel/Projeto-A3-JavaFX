package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ToDoController {

    private int totalTasks = 0;
    private int completedTasks = 0;

    @FXML
    private Label taskCount;

    @FXML
    private Label taskCompletedCount;

    @FXML
    private TextField taskInput;

    @FXML
    private VBox taskListContainer;

    private void updateTaskCountLabels() {
        taskCompletedCount.setText(Integer.toString(completedTasks));
        taskCount.setText(Integer.toString(totalTasks));
    }

    @FXML
    protected void addTask() {
        String taskName = taskInput.getText();

        if (taskName == null || taskName.trim().isEmpty()) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskItem.fxml"));

            Node taskItem = loader.load();

            TaskItemController taskController = loader.getController();
            taskController.setTaskName(taskName);

            taskController.setMainController(this);

            taskListContainer.getChildren().add(taskItem);
            totalTasks++;
            updateTaskCountLabels();

            taskInput.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar TaskItem.fxml");
        }
    }

    public void removeTask(Node taskNode, boolean wasCompleted) {
        taskListContainer.getChildren().remove(taskNode);
        totalTasks--;

        if (wasCompleted) {
            completedTasks--;
        }

        updateTaskCountLabels();
    }

    public void incrementCompletedTasks() {
        completedTasks++;
        updateTaskCountLabels();
    }

    public void decrementCompletedTasks() {
        completedTasks--;
        updateTaskCountLabels();
    }
}
