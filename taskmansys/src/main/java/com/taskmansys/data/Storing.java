package com.taskmansys.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Reminder;
import com.taskmansys.model.Task;

public class Storing {
    private static final String TASKS_JSON = "src/main/medialab/tasks.json";

    // Store Data as JSON
    public static void storeData(ArrayList<Task> tasks) {
        JSONArray tasksArray = new JSONArray();

        for (Task task : tasks) {
            JSONObject taskObj = new JSONObject();
            taskObj.put("name", task.getName());
            taskObj.put("description", task.getDescription());
            taskObj.put("category", task.getCategory());
            taskObj.put("priorityName", task.getPriorityName());
            taskObj.put("deadline", task.getDeadline().toString());
            taskObj.put("status", task.getStatus());

            JSONArray remindersArray = new JSONArray();
            for (Reminder reminder : task.getReminders()) {
                JSONObject reminderObj = new JSONObject();
                reminderObj.put("reminderDate", reminder.getReminderDate().toString());
                remindersArray.add(reminderObj);
            }
            taskObj.put("reminders", remindersArray);

            tasksArray.add(taskObj);
            
        }

        // Write JSON file
        try {
            FileWriter file = new FileWriter(TASKS_JSON);
            file.write(tasksArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // Load Data
    public static void loadData(ArrayList<Priority> prios, ArrayList<Category> categs, ArrayList<Task> tasks) throws Exception {

        try {
            // Parse JSON file
            Object obj = new JSONParser().parse(new FileReader(TASKS_JSON));
            JSONArray ar = (JSONArray) obj;

            for (Object taskObj : ar) {
                JSONObject jo = (JSONObject) taskObj;
                
                // Extract task properties
                String name = (String) jo.get("name");
                String description = (String) jo.get("description");
                String categoryName = (String) jo.get("category");
                String priorityName = (String) jo.get("priorityName");
                String status = (String) jo.get("status");

                // Parse deadline from string
                LocalDate deadline = LocalDate.parse((String) jo.get("deadline"));

                // Find or create Category
                Category category = categs.stream()
                    .filter(c -> c.getName().equals(categoryName))
                    .findFirst()
                    .orElseGet(() -> {
                        Category newCategory = new Category(categoryName);
                        categs.add(newCategory);
                        return newCategory;
                    });

                // Find or create Priority
                Priority priority = prios.stream()
                    .filter(p -> p.getName().equals(priorityName))
                    .findFirst()
                    .orElseGet(() -> {
                        Priority newPriority = new Priority(priorityName);
                        prios.add(newPriority);
                        return newPriority;
                    });

                // Create Task and set status
                Task task = new Task(name, description, category, priority, deadline);

                // Process reminders
                JSONArray remindersArray = (JSONArray) jo.get("reminders");
                for (Object remObj : remindersArray) {
                    JSONObject reminderJson = (JSONObject) remObj;
                    LocalDate reminderDate = LocalDate.parse((String) reminderJson.get("reminderDate"));

                    Reminder reminder = new Reminder(task, reminderDate);
                    task.addReminder(reminder);
                }

                task.changeStatus(status); // Add this after reminders are added to make sure to clear them if status is "Completed"

                // Add task to list
                tasks.add(task);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
