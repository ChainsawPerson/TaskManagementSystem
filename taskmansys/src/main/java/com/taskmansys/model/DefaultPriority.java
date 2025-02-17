package com.taskmansys.model;
import java.util.ArrayList;

public class DefaultPriority {
    protected String name = "Default";
    protected static ArrayList<Task> tasks = new ArrayList<>();

    // Constructor
    public DefaultPriority() {

    }

    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void deleteDefaultPriority() {
        for (Task task : DefaultPriority.tasks) {
            task.category.deleteTask(task);
            TaskList.tasks.remove(task);
        }
        DefaultPriority.tasks.clear();
    }
}
