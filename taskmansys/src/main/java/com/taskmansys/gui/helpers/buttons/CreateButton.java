package com.taskmansys.gui.helpers.buttons;

import java.io.IOException;

import com.taskmansys.gui.Controller;
import com.taskmansys.gui.EditWindow;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;


public class CreateButton {
    public static void setupCreateButton(Controller controller, MenuButton createButton) {
        MenuItem createTask = new MenuItem("Create Task");

        createTask.setOnAction(eh -> {
            try {
                controller.openCreateWindow();
            } catch (IOException e) {
                System.err.println("Could not open window: " + e.getMessage());
            }
        });

        MenuItem createCategory = new MenuItem("Create Category");

        createCategory.setOnAction(eh -> {
            System.out.println("Open Quick Editor");
            EditWindow.createWindow("Category", controller);
        });

        MenuItem createPriority = new MenuItem("Create Priority");

        createPriority.setOnAction(eh -> {
            System.out.println("Open Quick Editor");
            EditWindow.createWindow("Priority", controller);
        });

        createButton.getItems().addAll(createTask, createCategory, createPriority);
    }
}
