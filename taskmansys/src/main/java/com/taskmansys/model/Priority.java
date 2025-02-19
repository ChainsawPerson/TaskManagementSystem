package com.taskmansys.model;
import java.util.ArrayList;

public class Priority extends DefaultPriority{

    private ArrayList<Task> tasks;

    // Constructor
    public Priority() { // Default
        super.name = "Default";
    }

    public Priority(String n) {
        if (!"Default".equals(n)) {
            super.name = n;
            tasks = new ArrayList<>();
        }
    }

    // Getters
    public ArrayList<Task> getTasks() {
        if ("Default".equals(name)) return DefaultPriority.tasks;
        else return tasks;
    }

    // Setters
    public void changeName(String n) {
        if (!"Default".equals(n)) super.name = n;
    }

    public void addTask(Task t) {
        if (!"Default".equals(t.getPriorityName())) tasks.add(t);
        else {DefaultPriority.tasks.add(t); System.out.println("I am executed");}
    }

    public void moveTask(Task t) { // Method to move between Custom and Default

        if (t.getPriority() == this) { // Custom to Default
            tasks.remove(t);
            DefaultPriority.tasks.add(t);
            t.changePriority(new Priority());
        } 

        else if ("Default".equals(t.getPriorityName())) { // Default to Custom
            DefaultPriority.tasks.remove(t);
            tasks.add(t);
            t.changePriority(this);
        } 

        else { // Another Custom to this Custom
            t.getPriority().getTasks().remove(t);
            tasks.add(t);
            t.changePriority(this);
        }

    }

    public void deleteTask(Task t) {
        if ("Default".equals(name)) DefaultPriority.tasks.remove(t);
        else tasks.remove(t);
    }

    public void deletePriority() {
        tasks.stream().map(task -> {
            DefaultPriority.tasks.add(task);
            return task;
        }).forEachOrdered(task -> {
            task.changePriority(new Priority());
        });
        tasks.clear();
    }

}