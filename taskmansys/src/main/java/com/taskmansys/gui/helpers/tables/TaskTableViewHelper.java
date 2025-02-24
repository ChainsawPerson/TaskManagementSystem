package com.taskmansys.gui.helpers.tables;

import com.taskmansys.gui.helpers.SearchBar;
import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TaskTableViewHelper {
    // Task Table
    public static void setupTaskTableColumns(TableView<Task> taskTableView, TableColumn<Task, String> taskNameColumn,
            TableColumn<Task, String> taskDescriptionColumn, TableColumn<Task, String> taskCategoryColumn, TableColumn<Task, String> taskStatusColumn,
            TableColumn<Task, String> taskPriorityColumn, TableColumn<Task, String> taskDeadlineColumn) {

        taskNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        taskDescriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        taskCategoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        taskStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        taskPriorityColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriorityName()));
        taskDeadlineColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDeadline().toString()));

        taskTableView.getSortOrder().clear();
        taskTableView.getSortOrder().add(taskCategoryColumn);
        taskCategoryColumn.setSortType(TableColumn.SortType.ASCENDING);
    }

    public static void populateTaskTableView(TableView<Task> taskTableView, TextField searchTask) {
        taskTableView.getItems().clear();
        taskTableView.getItems().addAll(TaskList.tasks);
        taskTableView.sort();
        taskTableView.refresh();
        SearchBar.showSearchBar(searchTask);
    }
}
