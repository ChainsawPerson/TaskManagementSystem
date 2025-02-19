package com.taskmansys.gui.helpers.buttons;

import com.taskmansys.App;
import com.taskmansys.gui.Controller;
import com.taskmansys.gui.helpers.TaskBarChartHelper;
import com.taskmansys.gui.helpers.tables.PriorityTableViewHelper;
import com.taskmansys.model.Priority;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrioritiesButton {
    public static void setupPrioritiesButton(Controller controller, Button totalPrioritiesButton, TableView<Priority> priorityTableView, TextField searchTask, BarChart<String, Number> taskBarChart) {
        // Handler for Priorities Button and Priorities Functions:
        totalPrioritiesButton.setOnAction(event -> PriorityTableViewHelper.populatePriorityTableView(priorityTableView, searchTask));

        // priorityTableView Functions (Edit/Delete)
        ContextMenu prioContextMenu = new ContextMenu();

        MenuItem editPriority = new MenuItem("Edit Priority");
        MenuItem deletePriority = new MenuItem("Delete Priority");

        editPriority.setOnAction(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Edit Priority: " + selectedPriority.getName());
            }
        });
        deletePriority.setOnAction(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Delete Priority: " + selectedPriority.getName());
                App.deletePriority(selectedPriority);
                PriorityTableViewHelper.populatePriorityTableView(priorityTableView, searchTask);
                TaskBarChartHelper.showTaskBarChart(taskBarChart);
            }
        });

        prioContextMenu.getItems().addAll(editPriority, deletePriority);

        priorityTableView.setOnMouseClicked(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Selected Priority: " + selectedPriority.getName());
                prioContextMenu.show(priorityTableView, eh.getScreenX(), eh.getScreenY());
            }
        });
    }
}
