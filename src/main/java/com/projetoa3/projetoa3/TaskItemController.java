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
    private Task task; // Armazena o objeto Task persistido
    private TaskRepository taskRepository = new TaskRepository(); // Novo: Instância do repositório

    // Novo: Define o objeto Task completo
    public void setTask(Task task) {
        this.task = task;
        this.taskLabel.setText(task.getDescription());
    }

    // Novo: Atualiza a UI para refletir o estado do banco (chamado no loadTasks)
    public void updateUIFromTask() {
        if (task.isCompleted()) {
            // Mudar ícone para 'concluído' (ex: um visto)
            statusIcon.setImage(new Image(getClass().getResourceAsStream("icons8-visto-32.png")));
            taskLabel.getStyleClass().add("completed-task");
        } else {
            // Mudar ícone para 'não concluído' (ex: círculo vazio)
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

        // 1. Atualiza no banco de dados
        boolean success = taskRepository.updateCompletion(task.getId(), newStatus);

        if (success) {
            // 2. Atualiza o objeto Task local e a UI
            task.setCompleted(newStatus);
            updateUIFromTask(); // Reutiliza o método para atualizar visualmente

            // 3. Notifica o controlador principal para atualizar os contadores
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
            // Passa o nó e o objeto Task para o MainController remover do BD e da UI
            mainController.removeTask(taskItemBox, task);
        }
    }
}