package com.taskmanagementsystem;
import java.util.ArrayList;

public class DefaultPriority {
    protected String name = "Default";
    protected static ArrayList<Task> tasks = new ArrayList<Task>();

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
}
