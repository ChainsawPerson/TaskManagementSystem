package com.taskmansys.gui.helpers.tables;

import com.taskmansys.App;
import com.taskmansys.gui.helpers.SearchBar;
import com.taskmansys.model.Priority;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PriorityTableViewHelper {
    // Priorities Table
    public static void setupPriorityTableColumns(TableView<Priority> priorityTableView, TableColumn<Priority, String> priorityNameColumn, TableColumn<Priority, Integer> priorityNoTasksColumn) {
        priorityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priorityNoTasksColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTasks().size()).asObject());
    }

    public static void populatePriorityTableView(TableView<Priority> priorityTableView, TextField searchTask) {
        priorityTableView.getItems().clear();
        priorityTableView.getItems().addAll(App.getPriorities());
        priorityTableView.refresh();
        priorityTableView.toFront();
        SearchBar.hideSearchBar(searchTask);
    }
}
