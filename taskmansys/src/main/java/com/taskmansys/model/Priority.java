package com.taskmansys.model;
import java.util.ArrayList;

public class Priority extends DefaultPriority{

    private ArrayList<Task> pTasks;

    // Constructor
    public Priority() { // Default
        super.name = "Default";
    }

    public Priority(String n) {
        if (!"Default".equals(n)) {
            super.name = n;
            pTasks = new ArrayList<>();
        }
    }

    // Getters
    @Override
    public ArrayList<Task> getTasks() {
        if ("Default".equals(name)) return DefaultPriority.tasks;
        else return pTasks;
    }

    // Setters
    public void changeName(String n) {
        if (!"Default".equals(n)) super.name = n;
    }

    public void addTask(Task t) {
        if (!"Default".equals(t.getPriorityName())) pTasks.add(t);
        else {DefaultPriority.tasks.add(t); System.out.println("I am executed");}
    }

    public void moveTask(Task t) { // Method to move between Custom and Default

        if (t.getPriority() == this) { // Custom to Default
            pTasks.remove(t);
            DefaultPriority.tasks.add(t);
            t.changePriority(new Priority());
        } 

        else if ("Default".equals(t.getPriorityName())) { // Default to Custom
            DefaultPriority.tasks.remove(t);
            pTasks.add(t);
            t.changePriority(this);
        } 

        else { // Another Custom to this Custom
            t.getPriority().getTasks().remove(t);
            pTasks.add(t);
            t.changePriority(this);
        }

    }

    public void deleteTask(Task t) {
        if ("Default".equals(name)) DefaultPriority.tasks.remove(t);
        else pTasks.remove(t);
    }

    public void deletePriority() {
        if (!this.name.equals("Default")) {
            pTasks.stream().map(task -> {
                DefaultPriority.tasks.add(task);
                return task;
            }).forEachOrdered(task -> {
                task.deletePriority(new Priority());
            });
            pTasks.clear();
        }
        else {
            deleteDefaultPriority();
        }
    }

}