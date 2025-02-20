package com.taskmansys.gui.helpers.tables;

import com.taskmansys.gui.helpers.SearchBar;
import com.taskmansys.model.Reminder;
import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReminderTableViewHelper {
    // Reminder Table
    public static void setupReminderTableColumns(TableView<Reminder> reminderTableView, TableColumn<Reminder, String> reminderTaskNameCol, 
            TableColumn<Reminder, String> reminderTaskDescCol, TableColumn<Reminder, String> reminderTaskDeadlineCol, TableColumn<Reminder, String> reminderDateCol) {
        reminderTaskNameCol
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        reminderTaskDescCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTask().getDescription()));
        reminderTaskDeadlineCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTask().getDeadline().toString()));
        reminderDateCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getReminderDate().toString()));
    }

    public static void populateReminderTableView(TableView<Reminder> reminderTableView, TextField searchTask) {
        reminderTableView.getItems().clear();
        for (Task task : TaskList.tasks) {
            for (Reminder reminder : task.getReminders()) {
                reminderTableView.getItems().add(reminder);
            }
        }
        reminderTableView.refresh();
        SearchBar.hideSearchBar(searchTask);
    }
}
