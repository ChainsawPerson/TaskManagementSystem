package com.taskmanagementsystem;
import java.util.ArrayList;

public class Priority extends DefaultPriority{
    private ArrayList<Task> tasks;

    // Constructor
    public Priority() { // Default
        super.name = "Default";
    }

    public Priority(String n) {
        if (n != "Default") {
            super.name = n;
            tasks = new ArrayList<Task>();
        }
    }

    // Getters
    public ArrayList<Task> getTasks() {
        if (name == "Default") return DefaultPriority.tasks;
        else return tasks;
    }

    // Setters
    public void changeName(String n) {
        if (n != "Default") super.name = n;
    }

    public void addTask(Task t) {
        if (t.getPriorityName() != "Default") tasks.add(t);
        else {DefaultPriority.tasks.add(t); System.out.println("I am executed");}
    }

    public void moveTask(Task t) { // Method to move between Custom and Default

        if (t.gePriority() == this) { // Custom to Default
            tasks.remove(t);
            DefaultPriority.tasks.add(t);
            t.changePriority(new Priority());
        } 

        else if (t.getPriorityName() == "Default") { // Default to Custom
            DefaultPriority.tasks.remove(t);
            tasks.add(t);
            t.changePriority(this);
        } 

        else { // Another Custom to this Custom
            t.gePriority().getTasks().remove(t);
            tasks.add(t);
            t.changePriority(this);
        }

    }

    public void deleteTask(Task t) {
        if (name == "Default") DefaultPriority.tasks.remove(t);
        else tasks.remove(t);
    }

    public void deletePriority() {
        for (Task task : tasks) {
            DefaultPriority.tasks.add(task);
            task.changePriority(new Priority());
        }
        tasks.clear();
        super.name = null;
    }
}