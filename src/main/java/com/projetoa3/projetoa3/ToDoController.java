package com.projetoa3.projetoa3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; // Novo
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL; // Novo
import java.util.List; // Novo
import java.util.ResourceBundle; // Novo

// Implementar Initializable para carregar tarefas ao iniciar
public class ToDoController implements Initializable {

    private TaskRepository taskRepository; // Repositório de tarefas

    // O total e completed tasks agora serão contados a partir da lista
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

    // Construtor para inicializar o repositório
    public ToDoController() {
        this.taskRepository = new TaskRepository();
    }

    // Método chamado após o FXML ter sido carregado
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Carregar tarefas do banco ao iniciar a aplicação
        loadTasks();
    }

    private void updateTaskCountLabels() {
        taskCompletedCount.setText(Integer.toString(completedTasks));
        taskCount.setText(Integer.toString(totalTasks));
    }

    // Novo método para carregar e exibir tarefas do banco de dados
    private void loadTasks() {
        // Limpa a lista atual e os contadores antes de carregar
        taskListContainer.getChildren().clear();
        totalTasks = 0;
        completedTasks = 0;

        List<Task> tasks = taskRepository.findAll();

        for (Task task : tasks) {
            // Cria e exibe o item da tarefa
            displayTask(task);
        }
        updateTaskCountLabels();
    }

    // Novo método utilitário para criar e adicionar o nó da tarefa na UI
    private void displayTask(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskItem.fxml"));
            Node taskItem = loader.load();

            TaskItemController taskController = loader.getController();

            // Define o objeto Task completo no controlador do item
            taskController.setTask(task);
            taskController.setMainController(this);

            // Atualiza a UI baseada no status persistido
            taskController.updateUIFromTask();

            taskListContainer.getChildren().add(taskItem);

            // Atualiza contadores globais
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

        // 1. Cria o objeto Task e persiste no banco
        Task newTask = new Task(taskName);
        newTask = taskRepository.create(newTask); // O repositório preenche o ID

        if (newTask != null) {
            // 2. Exibe na interface
            displayTask(newTask);

            // 3. Limpa o input
            taskInput.clear();
        } else {
            System.err.println("Erro ao salvar tarefa no banco de dados.");
        }
    }

    // Método alterado para usar o ID persistido
    public void removeTask(Node taskNode, Task task) {
        // 1. Deleta do banco de dados
        boolean success = taskRepository.delete(task.getId());

        if (success) {
            // 2. Remove da interface se a deleção do BD foi bem sucedida
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