package com.taskmansys.gui.helpers.buttons;

import com.taskmansys.gui.Controller;
import com.taskmansys.gui.helpers.tables.ReminderTableViewHelper;
import com.taskmansys.model.Reminder;

import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RemindersButton {
    public static void setupRemindersButton(Controller controller, Button totalRemindersButton, TableView<Reminder> reminderTableView, TextField searchTask) {
        // Handler for Reminders Button and Reminders Functions:
        totalRemindersButton.setOnAction(event -> {
            ReminderTableViewHelper.populateReminderTableView(reminderTableView, searchTask);
            reminderTableView.toFront();
        });

        // reminderTableView Functions (Edit/Delete)
        ContextMenu reminderContextMenu = new ContextMenu();

        MenuItem editReminder = new MenuItem("Edit Reminder");
        MenuItem deleteReminder = new MenuItem("Delete Reminder");

        editReminder.setOnAction(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                System.out.println("Edit Reminder: " + selectedReminder.getReminderDate().toString());
            }
        });
        deleteReminder.setOnAction(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                selectedReminder.deleteReminder();
                ReminderTableViewHelper.populateReminderTableView(reminderTableView, searchTask);
            }
        });

        reminderContextMenu.getItems().addAll(editReminder, deleteReminder);

        reminderTableView.setOnMouseClicked(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                reminderContextMenu.show(reminderTableView, eh.getScreenX(), eh.getScreenY());
            }
        });
    }
}
