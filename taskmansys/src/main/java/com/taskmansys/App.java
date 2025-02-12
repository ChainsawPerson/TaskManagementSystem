package com.taskmansys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.taskmansys.data.Storing;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Task;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private ArrayList<Priority> priorities;
    private ArrayList<Category> categories;
    private static ArrayList<Task> tasks;

    @Override
    public void start(Stage stage) throws IOException {
        initializeData();
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeData() {
        priorities = new ArrayList<>();
        categories = new ArrayList<>();
        tasks = new ArrayList<>();

        try {
            Storing.loadData(priorities, categories, tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Task> getTasks() {
        return tasks;
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

}