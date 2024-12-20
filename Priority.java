import java.util.ArrayList;

public class Priority {
    private String name;
    private ArrayList<Task> tasks;

    // Constructors

    public Priority() {
        name = "Default";
        tasks = new ArrayList<Task>();
    }

    public Priority(String n) {
        if (n == "default") return;
        name = n;
        tasks = new ArrayList<Task>();
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
        if (name == "Default" || n == "Default") return;
        name = n;
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).changePriority(n);
        }
    }

    public void deletePriority() {
        if(name == "Default") return;
        tasks = null;
    } 

    public void addTask(Task t) {
        tasks.add(t);
    }
}