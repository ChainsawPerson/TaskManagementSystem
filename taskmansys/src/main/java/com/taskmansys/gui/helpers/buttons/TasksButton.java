package com.taskmansys.gui.helpers.buttons;

import java.io.IOException;

import com.taskmansys.App;
import com.taskmansys.gui.Controller;
import com.taskmansys.gui.EditWindow;
import com.taskmansys.gui.helpers.TaskBarChartHelper;
import com.taskmansys.gui.helpers.tables.TaskTableViewHelper;
import com.taskmansys.model.Task;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TasksButton {
    public static void setupTasksButton(Controller controller, Button totalTasksButton, TableView<Task> taskTableView, TextField searchTask, BarChart<String, Number> taskBarChart) {
        // Handler for Tasks Button and Tasks Functions:
        totalTasksButton.setOnAction(event -> {
            TaskTableViewHelper.populateTaskTableView(taskTableView, searchTask);
            taskTableView.toFront();
        });
        
        // taskTableView Functions (Edit/Delete)
        ContextMenu taskContextMenu = new ContextMenu();

        MenuItem addReminder = new MenuItem("Add Reminder");
        MenuItem editTask = new MenuItem("Edit Task");
        MenuItem deleteTask = new MenuItem("Delete Task");

        addReminder.setOnAction(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Add Reminder to Task: " + selectedTask.getName());
                EditWindow.createReminderWindow(controller, selectedTask);
            }
        });
        editTask.setOnAction(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Edit Task: " + selectedTask.getName());
                try {
                    controller.openEditWindow(selectedTask);
                } catch (IOException e) {
                    System.err.println("Could not open window: " + e.getMessage());
                }
            }
        });
        deleteTask.setOnAction(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Delete Task: " + selectedTask.getName());
                App.deleteTask(selectedTask);
                TaskTableViewHelper.populateTaskTableView(taskTableView, searchTask);
                TaskBarChartHelper.showTaskBarChart(taskBarChart);
            }
        });

        taskContextMenu.getItems().addAll(addReminder, editTask, deleteTask);

        taskTableView.setOnMouseClicked(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Selected Task: " + selectedTask.getName());
                taskContextMenu.show(taskTableView, eh.getScreenX(), eh.getScreenY());
            }
        });
    }

    
}
