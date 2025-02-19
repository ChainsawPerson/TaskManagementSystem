package com.taskmansys.model;
import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    protected String name;
    protected String description;
    protected Category category;
    protected Priority priority;
    protected LocalDate deadline;
    protected String status;
    
    protected ArrayList<Reminder> reminders;

    // Constructors

    public Task(String name, String dscr, Category ctgr, Priority pri, LocalDate dl) { // With all parameters
        this.name = name;
        this.description = dscr;
        this.category = ctgr;
        this.priority = pri;
        this.deadline = dl;
        this.status = "Open";
        this.reminders = new ArrayList<>();
        category.addTask(this); // Update category list
        priority.addTask(this); // Update priority list
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category.getName();
    }

    public String getPriorityName() {
        return priority.getName();
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    // Setters

    public void changeName(String n) {
        name = n;
    }

    public void changeDescription(String d) {
        description = d;
    }

    public void changeCategory(Category c) {
        category.deleteTask(this);
        category = c;
        c.addTask(this);
    }

    public void changePriority(Priority p) {
        priority.deleteTask(this);
        priority = p;
        p.addTask(this);
    }

    public void changeDeadline(LocalDate d) {
        deadline = d;
    }

    public void changeStatus(String s) {
        switch (s) {
            case "Open":
                this.status = s;
                break;
            case "In Progress":
                this.status = s;
                break;
            case "Postponed":
                this.status = s;
                break;
            case "Completed":
                this.status = s;
                reminders.clear();
                break;
            case "Delayed":
                this.status = s;
                break;
            default:
                System.err.println("Error, invalid status!");
                break;
        }
    }

    // Methods

    // Reminders
    public void addReminder(String d) {
        if ("Completed".equals(this.status)) return;
        switch (d) {
            case "Day":
                reminders.add(new Reminder(this, deadline.minusDays(1)));
                break;
            case "Week":
                reminders.add(new Reminder(this, deadline.minusWeeks(1)));
                break;
            case "Month":
                reminders.add(new Reminder(this, deadline.minusMonths(1)));
                break;
            default:
                System.err.println("Invalid default date. Try for custom date?");
                break;
        }
    }

    public void addReminder(LocalDate rd) {
        if (!"Completed".equals(this.status) && deadline.isAfter(rd))
            reminders.add(new Reminder(this, rd));
    }

    public void addReminder(Reminder r) {
        if (!"Completed".equals(this.status) && deadline.isAfter(r.getReminderDate()))
            reminders.add(r);
    }

    public void editReminder(Reminder r, LocalDate rd) {
        if (!"Completed".equals(this.status) && deadline.isAfter(rd))
            r.setReminder(rd);
    }

    public void deleteReminder(Reminder r) {
        reminders.remove(r);
    }

    public void checkReminders() {
        for (Reminder reminder : reminders) {
            reminder.checkDate();
        }
    }

    // Task
    public String getTaskInfo() {
        String ret = "Name: " + name + "\n";
        ret += "Description: " + description + "\n";
        ret += "Category: " + category.getName() + "\n";
        ret += "Priority: " + priority.getName() + "\n";
        ret += "Deadline: " + deadline.toString() + "\n";
        ret += "Status: " + status + "\n";
        ret += "Reminders: \n";
        for (Reminder reminder : reminders) {
            ret += reminder.getReminderDate().toString() + "\n";
        }
        return ret;
    }

    public void deleteTask() {
        reminders.clear();
        priority.deleteTask(this);
        category.deleteTask(this);
        TaskList.tasks.remove(this);
    }

    public Iterable<Reminder> getReminders() {
        return reminders;
    }

    public void checkDate() {
        if (deadline.isBefore(LocalDate.now()) && !"Completed".equals(status)) {
            status = "Delayed";
        }
        else if (status.equals("Delayed") && deadline.isAfter(LocalDate.now())) {
            status = "In Progress";           
        }
    }

}