package com.taskmansys.gui;

import java.time.LocalDate;

import com.taskmansys.App;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Task;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditWindow {
    public static void createWindow(String creation, Controller controller) {
        // Create a new Stage (popup window)
        Stage stage = new Stage();

        // Create a TextField for input
        TextField textField = new TextField();
        textField.setPromptText("Type a name for your new " + creation);

        // Create OK and Cancel buttons
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        // Set up the button actions
        okButton.setOnAction(eh -> {
            Boolean exists;
            String newName = textField.getText();
            switch (creation) {
                case "Priority":
                    exists = App.getPriorities().stream().anyMatch(prio -> prio.getName().equals(newName));
                    if (!exists) {
                        App.getPriorities().add(new Priority(newName));
                    }
                    break;
                case "Category":
                    exists = App.getCategories().stream().anyMatch(cat -> cat.getName().equals(newName));
                    if (!exists) {
                        App.getCategories().add(new Category(newName));
                    }
                    break;
                default:
                    return;
            }
            if (exists) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Duplicate Entry");
                alert.setHeaderText("This " + creation + " already exists!");
                alert.showAndWait();
                return;
            }
            stage.close();  // Close the window after saving
            controller.refresh();
        });

        cancelButton.setOnAction(eh -> {
            stage.close();  // Close the window without making any changes
        });

        // Layout container for the buttons (HBox places buttons horizontally)
        HBox buttonBox = new HBox(10, okButton, cancelButton); // 10 is the spacing between buttons
        buttonBox.setStyle("-fx-alignment: center;"); // Center align the buttons

        // Layout container to hold the TextField and Buttons (VBox places elements vertically)
        VBox vbox = new VBox(10, textField, buttonBox); // Add TextField and buttonBox to VBox
        vbox.setStyle("-fx-padding: 10;"); // Padding around the VBox

        // Create a Scene with the layout and a defined size
        Scene scene = new Scene(vbox, 300, 100);  // Width: 300px, Height: 200px

        // Set the scene for the stage
        stage.setScene(scene);

        // Set the title for the pop-up window
        stage.setTitle("Create a new " + creation);

        // Show the pop-up window
        stage.show();
    }

    public static void createReminderWindow(Controller controller, Task task) {
        // Create the Window
        Stage stage = new Stage();

        // Create the Buttons
        Button oneDayButton = new Button("1 Day before Deadline");
        Button oneWeekButton = new Button("1 Week before Deadline");
        Button oneMonthButton = new Button("1 Month before Deadline");
        Button customDate = new Button("Set custom date");

        // Setup button actions
        oneDayButton.setOnAction(eh -> {
            task.addReminder("Day");
            stage.close();
            controller.refresh();
        });
        oneWeekButton.setOnAction(eh -> {
            task.addReminder("Week");
            stage.close();
            controller.refresh();
        });
        oneMonthButton.setOnAction(eh -> {
            task.addReminder("Month");
            stage.close();
            controller.refresh();
        });
        customDate.setOnAction(eh -> createCustomReminderWindow(stage, controller, task));

        // Layout
        VBox buttonBox = new VBox(15, oneDayButton, oneWeekButton, oneMonthButton, customDate);
        buttonBox.setStyle("-fx-alignment: center;"); // Center align the buttons

        Scene scene = new Scene(buttonBox, 300, 200);

        // Set the scene for the stage
        stage.setScene(scene);

        // Set the title for the pop-up window
        stage.setTitle("Choose a Date");

        // Show the pop-up window
        stage.show();
    }

    private static void createCustomReminderWindow(Stage prevWin, Controller controller, Task task) {
        // Create a new Stage (popup window)
        Stage stage = new Stage();

        // Create a DatePicker for input
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Add a date for the Reminder");

        // Create OK and Cancel buttons
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");

        // Set up the button actions
        okButton.setOnAction(eh -> {
            LocalDate date = datePicker.getValue();
            if (date.isAfter(task.getDeadline())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Reminder date");
                alert.setHeaderText("Reminder can't be later than the Deadline");
                alert.showAndWait();
                return;
            } else if (date.isBefore(LocalDate.now())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Reminder date");
                alert.setHeaderText("Reminder can't be in the past!");
                alert.showAndWait();
                return;
            }
            task.addReminder(datePicker.getValue());
            stage.close();
            prevWin.close();
            controller.refresh();
        });

        cancelButton.setOnAction(eh -> {
            stage.close();  // Close the window without making any changes
        });

        // Layout container for the buttons (HBox places buttons horizontally)
        HBox buttonBox = new HBox(10, okButton, cancelButton); // 10 is the spacing between buttons
        buttonBox.setStyle("-fx-alignment: center;"); // Center align the buttons

        // Layout container to hold the datePicker and Buttons (VBox places elements vertically)
        VBox vbox = new VBox(10, datePicker, buttonBox); // Add datePicker and buttonBox to VBox
        vbox.setStyle("-fx-alignment: center;"); // Padding around the VBox

        // Create a Scene with the layout and a defined size
        Scene scene = new Scene(vbox, 300, 100);  // Width: 300px, Height: 200px

        // Set the scene for the stage
        stage.setScene(scene);

        // Set the title for the pop-up window
        stage.setTitle("Choose a Date");

        // Show the pop-up window
        stage.show();
    }
}
