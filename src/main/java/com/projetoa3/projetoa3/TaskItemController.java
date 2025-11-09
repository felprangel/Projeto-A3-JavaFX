package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TaskItemController {
    @FXML
    private HBox taskItemBox;

    @FXML
    private Label taskLabel;

    @FXML
    private ImageView statusIcon;

    private ToDoController mainController;
    private boolean isCompleted = false;

    public void setTaskName(String name) {
        taskLabel.setText(name);
    }

    public void setMainController(ToDoController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void toggleCompletion() {
        isCompleted = !isCompleted;
        if (isCompleted) {
            statusIcon.setImage(new Image(getClass().getResourceAsStream("icons8-ok-32.png")));
            taskLabel.getStyleClass().add("completed-task");
            mainController.incrementCompletedTasks();
            return;
        }

        statusIcon.setImage(new Image(getClass().getResourceAsStream("icons8-círculo-32.png")));
        taskLabel.getStyleClass().remove("completed-task");
        mainController.decrementCompletedTasks();
    }

    @FXML
    private void deleteTask() {
        if (mainController != null) {
            mainController.removeTask(taskItemBox, isCompleted);
        }
    }
}
