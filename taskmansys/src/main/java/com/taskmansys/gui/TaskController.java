package com.taskmansys.gui;

import java.util.stream.Collectors;

import com.taskmansys.App;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TaskController {

    private Controller originalWindow;

    @FXML
    private AnchorPane editWindow;

    @FXML
    private TextField taskNameTF;

    @FXML
    private TextField taskDescrTF;

    @FXML
    private ComboBox<String> categSelector;

    @FXML
    private ComboBox<String> prioSelector;

    @FXML
    private ComboBox<String> statusSelector;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Boolean creationMode;
    private Task task;  // Store the task to apply or discard changes

    public void initialize() {
        categSelector.setEditable(true);
        categSelector.getItems().addAll(
                App.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList())
        );
        prioSelector.setEditable(true);
        prioSelector.getItems().addAll(
                App.getPriorities().stream() // Convert the list of priorities to a stream
                        .map(prio -> prio.getName()) // Extract the name of each priority
                        .collect(Collectors.toList()) // Collect the names into a List
        );
        String statusVars[] = {"Open", "In Progress", "Postponed", "Completed", "Delayed"};
        statusSelector.getItems().addAll(statusVars);

        // Set the saveButton and cancelButton actions
        saveButton.setOnAction(event -> saveTask());
        cancelButton.setOnAction(event -> cancelTask());
    }

    public void setOriginalWindow(Controller c) {
        this.originalWindow = c;
    }

    public void createTask() {
        statusSelector.getSelectionModel().select("Open");
        creationMode = true;
    }

    // Method to edit the task and pre-populate the fields with the task's current data
    public void editTask(Task task) {
        creationMode = false;
        this.task = task; // Store the task reference to use later
        taskNameTF.setText(task.getName()); // Populate the TextField with task name
        taskDescrTF.setText(task.getDescription()); // Populate with task description
        categSelector.getSelectionModel().select(task.getCategory()); // Set the category
        prioSelector.getSelectionModel().select(task.getPriorityName()); // Set the Priority
        statusSelector.getSelectionModel().select(task.getStatus()); // Set the Status
        datePicker.setValue(task.getDeadline());
    }

    // Save method to apply changes to the task
    private void saveTask() {
        Category selectedCategory = App.getCategories().stream()
                .filter(category -> category.getName().equals(categSelector.getValue()))
                .findFirst()
                .orElse(new Category(categSelector.getValue())); // Creates new if no category is found
        if (!App.getCategories().contains(selectedCategory)) {
            App.getCategories().add(selectedCategory); // Add category to Categories list
        }

        Priority selectedPriority = App.getPriorities().stream()
                .filter(priority -> priority.getName().equals(prioSelector.getValue()))
                .findFirst()
                .orElse(new Priority(prioSelector.getValue())); // Returns Default if no priority is found
        if (!App.getPriorities().contains(selectedPriority)) {
            App.getPriorities().add(selectedPriority);
        }

        if (creationMode) {
            final Task createdTask = new Task(taskNameTF.getText(), taskDescrTF.getText(), selectedCategory, selectedPriority, datePicker.getValue());
            TaskList.tasks.add(createdTask);

            System.out.println("Task created: \n" + createdTask.getTaskInfo());
        } 
        else {
            if (task != null) {
                task.changeName(taskNameTF.getText()); // Apply the name change

                task.changeDescription(taskDescrTF.getText()); // Apply description change

                task.changeCategory(selectedCategory);

                task.changePriority(selectedPriority);

                task.changeStatus(statusSelector.getValue());

                task.changeDeadline(datePicker.getValue());

                System.out.println("Task saved: " + task.getName());
            }
        }
        closeWindow();

    }

    // Cancel method to discard changes and close the window
    private void cancelTask() {
        System.out.println("Changes discarded.");
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) editWindow.getScene().getWindow();
        stage.close();
        originalWindow.refresh();
    }
}
