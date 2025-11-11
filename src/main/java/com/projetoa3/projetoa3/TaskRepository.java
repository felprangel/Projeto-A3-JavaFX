package com.projetoa3.projetoa3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TaskRepository {
    public Task create(Task task) {
        String sql = "INSERT INTO tasks (description) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, task.getDescription());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        task.setId(rs.getInt(1));
                    }
                }
            }
            return task;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Task> findAll() {
        String sql = "SELECT id, description, is_completed, created_at, updated_at FROM tasks ORDER BY created_at DESC";
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("is_completed"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean updateCompletion(int id, boolean isCompleted) {
        String sql = "UPDATE tasks SET is_completed = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBoolean(1, isCompleted);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}