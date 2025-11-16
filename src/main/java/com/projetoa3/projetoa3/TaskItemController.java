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
    private Task task;
    private TaskRepository taskRepository = new TaskRepository();

    public void setTask(Task task) {
        this.task = task;
        this.taskLabel.setText(task.getDescription());
    }

    public void updateUIFromTask() {
        if (task.isCompleted()) {
            statusIcon.setImage(new Image(getClass().getResourceAsStream("icons8-ok-32.png")));
            taskLabel.getStyleClass().add("completed-task");
        } else {
            statusIcon.setImage(new Image(getClass().getResourceAsStream("icons8-círculo-32.png")));
            taskLabel.getStyleClass().remove("completed-task");
        }
    }

    public void setMainController(ToDoController controller) {
        this.mainController = controller;
    }

    @FXML
    private void toggleCompletion() {
        boolean newStatus = !task.isCompleted();

        boolean success = taskRepository.updateCompletion(task.getId(), newStatus);

        if (success) {
            task.setCompleted(newStatus);
            updateUIFromTask();

            if (newStatus) {
                mainController.incrementCompletedTasks();
            } else {
                mainController.decrementCompletedTasks();
            }
        } else {
            System.err.println("Falha ao atualizar status no banco de dados.");
        }
    }

    @FXML
    private void deleteTask() {
        if (mainController != null && task != null) {
            mainController.removeTask(taskItemBox, task);
        }
    }
}