package com.taskmansys;

import java.io.IOException;

import com.taskmansys.model.Task;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private ListView<String> taskListView;

    public void initialize() {
        // Populate the ListView with tasks
        for (Task task : App.getTasks()) {
            taskListView.getItems().add(task.getTaskInfo());
        }
    }
}
