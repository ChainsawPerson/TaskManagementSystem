package com.taskmansys.model;
import java.time.LocalDate;

public class Reminder {
    private LocalDate date;
    private final Task task;

    // Constructors

    public Reminder(Task t, LocalDate d) {
        date = d;
        task = t;
    }

    // Getters
    public Task getTask() {
        return task;
    }
    
    public LocalDate getReminderDate() {
        return date;
    }

    // Setters

    public void setReminder(LocalDate d) {
        date = d;
    }

    // Methods 

    public void deleteReminder() {
        task.deleteReminder(this);
    }

    public Boolean checkDate() {
        return LocalDate.now().equals(date);
    }

}
