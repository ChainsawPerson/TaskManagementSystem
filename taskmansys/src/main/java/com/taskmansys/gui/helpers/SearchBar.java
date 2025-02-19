package com.taskmansys.gui.helpers;

import java.util.stream.Collectors;

import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchBar {
    public static void setupSearchBar(TextField searchTask, TableView<Task> taskTableView) {
        searchTask.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the search field is empty, show all tasks
            if (newValue.isEmpty()) {
                taskTableView.getItems().clear();
                taskTableView.getItems().addAll(TaskList.tasks);
            } else {
                // Filter tasks based on multiple criteria (case-insensitive)
                taskTableView.getItems().clear();
                taskTableView.getItems().addAll(TaskList.tasks.stream()
                        .filter(task -> task.getName().toLowerCase().contains(newValue.toLowerCase())
                        || task.getDescription().toLowerCase().contains(newValue.toLowerCase())
                        || task.getCategory().toLowerCase().contains(newValue.toLowerCase())
                        || task.getPriorityName().toLowerCase().contains(newValue.toLowerCase())
                        || task.getDeadline().toString().contains(newValue)) // Handling LocalDate.toString()
                        // format
                        .collect(Collectors.toList()));
            }

            // Refresh the table view
            taskTableView.refresh();
        });
    }

    // Search Bar
    public static void showSearchBar(TextField searchTask) {
        searchTask.setVisible(true);
    }

    public static void hideSearchBar(TextField searchTask) {
        searchTask.setVisible(false);
    }
}
