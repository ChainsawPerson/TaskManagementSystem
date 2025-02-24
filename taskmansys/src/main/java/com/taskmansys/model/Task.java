package com.taskmansys.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a task in a task management system. A task has a name,
 * description, category, priority, deadline, status, and reminders. The status
 * of the task can be changed between "Open", "In Progress", "Postponed",
 * "Completed", and "Delayed".
 */
public class Task {

    protected String name;
    protected String description;
    protected Category category;
    protected Priority priority;
    protected LocalDate deadline;
    protected String status;

    protected ArrayList<Reminder> reminders;

    // Constructors
    /**
     * Constructs a Task with the specified name, description, category,
     * priority, and deadline. The task is initially set to "Open" status, and
     * reminders are initialized as an empty list.
     *
     * @param name the name of the task
     * @param dscr the description of the task
     * @param ctgr the category of the task
     * @param pri the priority of the task
     * @param dl the deadline of the task
     */
    public Task(String name, String dscr, Category ctgr, Priority pri, LocalDate dl) { // With all parameters
        this.name = name;
        this.description = dscr;
        this.category = ctgr;
        this.priority = pri;
        this.deadline = dl;
        this.status = "Open";
        this.reminders = new ArrayList<>();
        initializeTask();
    }

    /**
     * Initializes the task by adding it to the associated category and
     * priority.
     */
    public final void initializeTask() {
        category.addTask(this); // Update category list
        priority.addTask(this); // Update priority list
    }

    /**
     * Gets the name of the task.
     *
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the name of the task's category.
     *
     * @return the category name of the task
     */
    public String getCategory() {
        return category.getName();
    }

    /**
     * Gets the name of the task's priority.
     *
     * @return the priority name of the task
     */
    public String getPriorityName() {
        return priority.getName();
    }

    /**
     * Gets the priority object of the task.
     *
     * @return the priority object of the task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Gets the deadline of the task.
     *
     * @return the deadline of the task
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Gets the current status of the task.
     *
     * @return the status of the task
     */
    public String getStatus() {
        return status;
    }

    // Setters

    /**
     * Changes the name of the task.
     *
     * @param n the new name of the task
     */
    public void changeName(String n) {
        name = n;
    }

    /**
     * Changes the description of the task.
     *
     * @param d the new description of the task
     */
    public void changeDescription(String d) {
        description = d;
    }

    /**
     * Changes the category of the task.
     *
     * @param c the new category of the task
     */
    public void changeCategory(Category c) {
        category.deleteTask(this);
        category = c;
        c.addTask(this);
    }

    /**
     * Changes the priority of the task.
     *
     * @param p the new priority of the task
     */
    public void changePriority(Priority p) {
        priority.deleteTask(this);
        priority = p;
        p.addTask(this);
    }

    /**
     * A method used ONLY when deleting the priority containing it.
     *
     * @param p the new priority of the task
     */
    public void deletePriority(Priority p) {
        priority = p;
    }

    /**
     * Changes the deadline of the task.
     *
     * @param d the new deadline of the task
     */
    public void changeDeadline(LocalDate d) {
        deadline = d;
        checkDate();
    }

    /**
     * Changes the status of the task. Valid status values are "Open", "In Progress", "Postponed", "Completed", and "Delayed".
     *
     * @param s the new status of the task
     */
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

    /**
     * Adds a reminder to the task with a specific time before the deadline.
     *
     * @param d the reminder type ("Day", "Week", "Month")
     */
    public void addReminder(String d) {
        if ("Completed".equals(this.status)) {
            return;
        }
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

    /**
     * Adds a reminder for a custom date.
     *
     * @param rd the reminder date
     */
    public void addReminder(LocalDate rd) {
        if (!"Completed".equals(this.status) && deadline.isAfter(rd)) {
            reminders.add(new Reminder(this, rd));
        }
    }

    /**
     * Adds a custom reminder.
     *
     * @param r the reminder object
     */
    public void addReminder(Reminder r) {
        if (!"Completed".equals(this.status) && deadline.isAfter(r.getReminderDate())) {
            reminders.add(r);
        }
    }

    /**
     * Edits an existing reminder with a new reminder date.
     *
     * @param r  the reminder to edit
     * @param rd the new reminder date
     */
    public void editReminder(Reminder r, LocalDate rd) {
        if (!"Completed".equals(this.status) && deadline.isAfter(rd)) {
            r.setReminder(rd);
        }
    }

    /**
     * Edits an existing reminder with a specific time before the deadline.
     *
     * @param r the reminder to edit
     * @param d the reminder type ("Day", "Week", "Month")
     */
    public void editReminder(Reminder r, String d) {
        if (!"Completed".equals(this.status)) {
            switch (d) {
                case "Day":
                    r.setReminder(deadline.minusDays(1));
                    break;
                case "Week":
                    r.setReminder(deadline.minusWeeks(1));
                    break;
                case "Month":
                    r.setReminder(deadline.minusMonths(1));
                    break;
                default:
                    System.err.println("Invalid default date. Try for custom date?");
                    break;
            }
        }
    }

    /**
     * Deletes a reminder from the task.
     *
     * @param r the reminder to delete
     */
    public void deleteReminder(Reminder r) {
        reminders.remove(r);
    }

    /**
     * Retrieves all reminders associated with the task.
     *
     * @return an iterable collection of reminders
     */
    public Iterable<Reminder> getReminders() {
        return reminders;
    }

    /**
     * Checks if any of the task's reminders are due.
     *
     * @return true if any reminder is due, false otherwise
     */
    public Boolean checkReminders() {
        for (Reminder reminder : reminders) {
            if (reminder.checkDate()) {
                return true;
            }
        }
        return false;
    }

    // Task

    /**
     * Retrieves detailed information about the task.
     *
     * @return a string representation of the task's details
     */
    public String getTaskInfo() {
        String ret = "Name: " + name + "\n";
        ret += "Description: " + description + "\n";
        ret += "Category: " + category.getName() + "\n";
        ret += "Priority: " + priority.getName() + "\n";
        ret += "Deadline: " + deadline.toString() + "\n";
        ret += "Status: " + status;
        return ret;
    }

    /**
     * Deletes the task, removing it from its category and priority, and clearing its reminders.
     */
    public void deleteTask() {
        reminders.clear();
        priority.deleteTask(this);
        category.deleteTask(this);
        TaskList.tasks.remove(this);
    }

    /**
     * Checks if the task's deadline is overdue and updates its status accordingly.
     */
    public void checkDate() {
        if (deadline.isBefore(LocalDate.now()) && !"Completed".equals(status)) {
            changeStatus("Delayed");
        } else if (status.equals("Delayed") && deadline.isAfter(LocalDate.now())) {
            changeStatus("In Progress");
        }
    }

}
