package com.taskmansys;

import com.taskmansys.model.Task;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    private Button totalTasksButton;

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> nameColumn;
    
    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, String> categoryColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, String> deadlineColumn;

    public void initialize() {
        // Set up the TableView columns
        nameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName()));
        descriptionColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDescription()));
        categoryColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCategory()));
        statusColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatus()));
        priorityColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPriorityName()));
        deadlineColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDeadline().toString()));

        // Populate the Task TableView
        PopulateTaskTableView();

        // Set up the button actions
        totalTasksButton.setOnAction(event -> PopulateTaskTableView()); // Refresh the TableView
        
    }

    // Actions
    public void PopulateTaskTableView() {
        taskTableView.getItems().clear();
        taskTableView.getItems().addAll(App.getTasks());
    }
}
