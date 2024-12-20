import java.util.ArrayList;
import java.time.LocalDate;

public class Task {
    protected String name;
    protected String description;
    protected String category;
    protected String priority;
    protected LocalDate deadline;
    protected String status;

    protected ArrayList<Reminder> reminders;

    // Constructors

    public Task(String name, String dscr, String ctgr, String pri, LocalDate dl) { // With all parameters
        this.name = name;
        this.description = dscr;
        this.category = ctgr;
        this.priority = pri;
        this.deadline = dl;
        this.status = "Open";
        this.reminders = new ArrayList<Reminder>();
    }

    public Task(String name, String dscr, String ctgr, LocalDate dl) { // For default category
        this.name = name;
        this.description = dscr;
        this.category = ctgr;
        this.priority = "Default";
        this.deadline = dl;
        this.status = "Open";
        this.reminders = new ArrayList<Reminder>();
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
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

    public void changeCategory(String c) {
        category = c;
    }

    public void changePriority(String p) {
        priority = p;
    }

    public void changeDeadline(LocalDate d) {
        deadline = d;
    }

    public void changeStatus(String s) {
        switch (s) {
            case "Open":
                this.status = s;
                break;
            case "In Progres":
                this.status = s;
                break;
            case "Postponed":
                this.status = s;
                break;
            case "Completed":
                this.status = s;
                reminders = null;
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

    public void addReminder(LocalDate rd) {
        if (this.status != "Completed" && deadline.isAfter(rd))
            reminders.add(new Reminder(rd));
    }

    public String printTask() {
        String ret = "Name: " + name + "\n";
        ret += "Description: " + description + "\n";
        ret += "Category: " + category + "\n";
        ret += "Priority: " + priority + "\n";
        ret += "Deadline: " + deadline.toString() + "\n";
        ret += "Status: " + status + "\n";
        return ret;
    }

}