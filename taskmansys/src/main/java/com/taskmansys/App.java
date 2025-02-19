package com.taskmansys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.taskmansys.data.Storing;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ArrayList<Priority> priorities;
    private static ArrayList<Category> categories;

    @Override
    public void start(Stage stage) throws IOException {
        initializeData();
        scene = new Scene(loadFXML("primary"), 1280, 720);
        stage.setTitle("MediaLab Assistant");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exiting Application");
            alert.setHeaderText("Unsaved changes");
            alert.setContentText("Exit and save changes?");

            ButtonType buttonTypeSave = new ButtonType("Save and exit");
            ButtonType buttonTypeNoSave = new ButtonType("Exit without saving");
            ButtonType buttonTypeCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeNoSave, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeSave) {
                try {
                    Storing.storeData();
                } catch (IOException e) {
                    System.out.println("Error storing data: " + e.getMessage());
                }
            } else if (result.get() == buttonTypeNoSave) {
                // Close without saving
                Platform.exit();
            } else {
                event.consume();
            }
        });
    }

    private void initializeData() {
        priorities = new ArrayList<>();
        categories = new ArrayList<>();
        TaskList.tasks = new ArrayList<>();

        // Create default priority
        priorities.add(new Priority());

        // Load the data
        try {
            Storing.loadData(priorities, categories);
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            System.err.println("Current working directory: " + System.getProperty("user.dir"));
        }

        // Check for delays
        for (Task task : TaskList.tasks) {
            task.checkDate();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    // Getters

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static ArrayList<Priority> getPriorities() {
        return priorities;
    }

    // Methods
    public static void deleteTask(Task task) {
        TaskList.tasks.remove(task);
        task.deleteTask();
    }

    public static void deleteCategory(Category category) {
        categories.remove(category);
        category.deleteCategory();
    }

    public static void deletePriority(Priority priority) {
        if (!priority.getName().equals("Default")) {
            priorities.remove(priority);
            priority.deletePriority();
        }
    }

}
