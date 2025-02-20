package com.taskmansys.gui;

import java.io.IOException;

import com.taskmansys.App;
import com.taskmansys.gui.helpers.SearchBar;
import com.taskmansys.gui.helpers.TaskBarChartHelper;
import com.taskmansys.gui.helpers.buttons.CategoriesButton;
import com.taskmansys.gui.helpers.buttons.CreateButton;
import com.taskmansys.gui.helpers.buttons.PrioritiesButton;
import com.taskmansys.gui.helpers.buttons.RemindersButton;
import com.taskmansys.gui.helpers.buttons.TasksButton;
import com.taskmansys.gui.helpers.tables.CategoryTableViewHelper;
import com.taskmansys.gui.helpers.tables.PriorityTableViewHelper;
import com.taskmansys.gui.helpers.tables.ReminderTableViewHelper;
import com.taskmansys.gui.helpers.tables.TaskTableViewHelper;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Reminder;
import com.taskmansys.model.Task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

    // Bar Chart
    @FXML
    private BarChart<String, Number> taskBarChart;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Actions
    @FXML
    private TextField searchTask;

    @FXML
    private Button totalTasksButton;

    @FXML
    private Button totalCategoriesButton;

    @FXML
    private Button totalPrioritiesButton;

    @FXML
    private Button totalRemindersButton;

    @FXML
    private MenuButton createButton;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tables
    // Task Table
    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, String> taskCategoryColumn;

    @FXML
    private TableColumn<Task, String> taskStatusColumn;

    @FXML
    private TableColumn<Task, String> taskPriorityColumn;

    @FXML
    private TableColumn<Task, String> taskDeadlineColumn;

    // Category Table
    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TableColumn<Category, Integer> categoryNoTasksColumn;

    // Priority Table
    @FXML
    private TableView<Priority> priorityTableView;

    @FXML
    private TableColumn<Priority, String> priorityNameColumn;

    @FXML
    private TableColumn<Priority, Integer> priorityNoTasksColumn;

    // Reminder Table
    @FXML
    private TableView<Reminder> reminderTableView;

    @FXML
    private TableColumn<Reminder, String> reminderTaskNameCol;

    @FXML
    private TableColumn<Reminder, String> reminderTaskDescCol;

    @FXML
    private TableColumn<Reminder, String> reminderTaskDeadlineCol;

    @FXML
    private TableColumn<Reminder, String> reminderDateCol;

    public void initialize() {
        //  SETUP
       
        // Set up the TableView columns

        // Task Table
        TaskTableViewHelper.setupTaskTableColumns(taskTableView, taskNameColumn, taskDescriptionColumn, taskCategoryColumn, taskStatusColumn, taskPriorityColumn, taskDeadlineColumn);

        // Category Table
        CategoryTableViewHelper.setupCategoryTableColumns(categoryTableView, categoryNameColumn, categoryNoTasksColumn);

        // Priority Table
        PriorityTableViewHelper.setupPriorityTableColumns(priorityTableView, priorityNameColumn, priorityNoTasksColumn);

        // Reminder Table
        ReminderTableViewHelper.setupReminderTableColumns(reminderTableView, reminderTaskNameCol, reminderTaskDescCol, reminderTaskDeadlineCol, reminderDateCol);

        // Set up the Search Bar
        SearchBar.setupSearchBar(searchTask, taskTableView);

        // Set up the button actions
        setupButtonActions();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //  INITIALIZE

        // Populate all TableView
        refresh();
        taskTableView.toFront();

        // Show the Task Bar Chart
        TaskBarChartHelper.showTaskBarChart(taskBarChart);
    }

    // Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                                            SETUP
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private void setupButtonActions() {
        // Set up the TableView actions
        TasksButton.setupTasksButton(this, totalTasksButton, taskTableView, searchTask, taskBarChart);
        CategoriesButton.setupCategoriesButton(this, totalCategoriesButton, categoryTableView, searchTask, taskBarChart);
        PrioritiesButton.setupPrioritiesButton(this, totalPrioritiesButton, priorityTableView, searchTask, taskBarChart);
        RemindersButton.setupRemindersButton(this, totalRemindersButton, reminderTableView, searchTask);

        // Set up the create button
        CreateButton.setupCreateButton(this, createButton);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                                           Actions
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void openTaskWindow(String title, Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("task.fxml"));
    
        Scene scene = new Scene(loader.load(), 420, 540);
        Stage stage = new Stage();
    
        // Get the controller
        TaskController controller = loader.getController();
        controller.setOriginalWindow(this);
    
        if (task != null) {
            // If the task is not null, edit the task
            controller.editTask(task);
        } else {
            controller.createTask();
        }
    
        stage.setTitle(title);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }
    
    public void openCreateWindow() throws IOException {
        openTaskWindow("Create Task", null);
    }
    
    public void openEditWindow(Task task) throws IOException {
        openTaskWindow("Edit", task);
    }
    public void refresh() {
        TaskTableViewHelper.populateTaskTableView(taskTableView, searchTask);
        CategoryTableViewHelper.populateCategoryTableView(categoryTableView, searchTask);
        PriorityTableViewHelper.populatePriorityTableView(priorityTableView, searchTask);
        ReminderTableViewHelper.populateReminderTableView(reminderTableView, searchTask);
        TaskBarChartHelper.showTaskBarChart(taskBarChart);
    }
}
