package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ToDoController implements Initializable {

    private final TaskRepository taskRepository;

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

    public ToDoController() {
        this.taskRepository = new TaskRepository();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTasks();
    }

    private void updateTaskCountLabels() {
        taskCompletedCount.setText(Integer.toString(completedTasks));
        taskCount.setText(Integer.toString(totalTasks));
    }

    private void loadTasks() {
        taskListContainer.getChildren().clear();
        totalTasks = 0;
        completedTasks = 0;

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            displayTask(task);
        }
        updateTaskCountLabels();
    }

    private void displayTask(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskItem.fxml"));
            Node taskItem = loader.load();

            TaskItemController taskController = loader.getController();

            taskController.setTask(task);
            taskController.setMainController(this);

            taskController.updateUIFromTask();

            taskListContainer.getChildren().add(taskItem);

            totalTasks++;
            if (task.isCompleted()) {
                completedTasks++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar TaskItem.fxml");
        }
    }


    @FXML
    protected void addTask() {
        String taskName = taskInput.getText();

        if (taskName == null || taskName.trim().isEmpty()) {
            return;
        }

        Task newTask = new Task(taskName);
        newTask = taskRepository.create(newTask);

        if (newTask != null) {
            displayTask(newTask);

            taskInput.clear();
            updateTaskCountLabels();
        } else {
            System.err.println("Erro ao salvar tarefa no banco de dados.");
        }
    }

    public void removeTask(Node taskNode, Task task) {
        boolean success = taskRepository.delete(task.getId());

        if (success) {
            taskListContainer.getChildren().remove(taskNode);
            totalTasks--;

            if (task.isCompleted()) {
                completedTasks--;
            }

            updateTaskCountLabels();
        } else {
            System.err.println("Falha ao deletar tarefa do banco de dados.");
        }
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