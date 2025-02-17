package com.taskmansys.model;
import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Task> tasks;

    // Constructors
    public Category(String n) {
        name = n;
        tasks = new ArrayList<>();
    }

    // Getters

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Setters

    public void changeName(String n) {
        name = n;
    }

    public void deleteCategory() {
        for (Task task : tasks) {
            task.gePriority().deleteTask(task);
            TaskList.tasks.remove(task);
        }
        tasks.clear();
        tasks = null;
    } 

    public void deleteTask(Task t) {
        tasks.remove(t);
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    // Methods
    @Override
    public String toString() {
        return name;
    }
}