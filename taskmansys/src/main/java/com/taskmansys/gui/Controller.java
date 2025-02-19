package com.taskmansys.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import com.taskmansys.App;
import com.taskmansys.model.Category;
import com.taskmansys.model.Priority;
import com.taskmansys.model.Reminder;
import com.taskmansys.model.Task;
import com.taskmansys.model.TaskList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SETUP
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Set up the TableView columns
        // Task Table
        setupTaskTableColumns();

        // Category Table
        setupCategoryTableColumns();

        // Priority Table
        setupPriorityTableColumns();

        // Reminder Table
        setupReminderTableColumns();

        // Set up the Search Bar
        setupSearchBar();

        // Set up the button actions
        setupButtonActions();

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INITIALIZE
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // populate the Task TableView
        populateTaskTableView();

        // Show the Task Bar Chart
        showTaskBarChart();
    }

    // Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                                              SETUP
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Setup the TableView columns
    // Task Table
    private void setupTaskTableColumns() {
        taskNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        taskDescriptionColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        taskCategoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        taskStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        taskPriorityColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriorityName()));
        taskDeadlineColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDeadline().toString()));
    }

    // Categories Table
    private void setupCategoryTableColumns() {
        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryNoTasksColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTasks().size()).asObject());
    }

    // Priorities Table
    private void setupPriorityTableColumns() {
        priorityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priorityNoTasksColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTasks().size()).asObject());
    }

    // Reminder Table
    private void setupReminderTableColumns() {
        reminderTaskNameCol
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask().getName()));
        reminderTaskDescCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTask().getDescription()));
        reminderTaskDeadlineCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTask().getDeadline().toString()));
        reminderDateCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getReminderDate().toString()));
    }

    private void setupSearchBar() {
        searchTask.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the search field is empty, show all tasks
            if (newValue.isEmpty()) {
                taskTableView.getItems().clear();
                taskTableView.getItems().addAll(TaskList.tasks);
            } else {
                // Filter tasks based on multiple criteria (case-insensitive)
                taskTableView.getItems().clear();
                taskTableView.getItems().addAll(TaskList.tasks.stream()
                        .filter(task -> task.getName().toLowerCase().contains(newValue.toLowerCase())
                                || task.getDescription().toLowerCase().contains(newValue.toLowerCase())
                                || task.getCategory().toLowerCase().contains(newValue.toLowerCase())
                                || task.getPriorityName().toLowerCase().contains(newValue.toLowerCase())
                                || task.getDeadline().toString().contains(newValue)) // Handling LocalDate.toString()
                                                                                     // format
                        .collect(Collectors.toList()));
            }

            // Refresh the table view
            taskTableView.refresh();
        });
    }

    private void setupButtonActions() {
        // Tabs
        totalTasksButton.setOnAction(event -> populateTaskTableView());
        totalCategoriesButton.setOnAction(event -> populateCategoryTableView());
        totalPrioritiesButton.setOnAction(event -> populatePriorityTableView());
        totalRemindersButton.setOnAction(event -> populateReminderTableView());

        // Set up the TableView actions
        // taskTableView
        ContextMenu taskContextMenu = new ContextMenu();

        MenuItem editTask = new MenuItem("Edit Task");
        MenuItem deleteTask = new MenuItem("Delete Task");

        editTask.setOnAction(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Edit Task: " + selectedTask.getName());
                try {
                    openEditWindow(selectedTask);
                } catch (Exception e) {
                    System.err.println("Could not open window: " + e.getMessage());
                }
            }
        });
        deleteTask.setOnAction(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Delete Task: " + selectedTask.getName());
                App.deleteTask(selectedTask);
                populateTaskTableView();
                showTaskBarChart();
            }
        });

        taskContextMenu.getItems().addAll(editTask, deleteTask);

        taskTableView.setOnMouseClicked(eh -> {
            Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                System.out.println("Selected Task: " + selectedTask.getName());
                taskContextMenu.show(taskTableView, eh.getScreenX(), eh.getScreenY());
            }
        });

        // categoryTableView
        ContextMenu categContextMenu = new ContextMenu();

        MenuItem editCategory = new MenuItem("Edit Category");
        MenuItem deleteCategory = new MenuItem("Delete Category");

        editCategory.setOnAction(eh -> {
            Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                System.out.println("Edit category: " + selectedCategory.getName());
            }
        });
        deleteCategory.setOnAction(eh -> {
            Category selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                System.out.println("Delete category: " + selectedCategory.getName());
                App.deleteCategory(selectedCategory);
                populateCategoryTableView();
                showTaskBarChart();
            }
        });

        categContextMenu.getItems().addAll(editCategory, deleteCategory);

        categoryTableView.setOnMouseClicked(eh -> {
            Category selectedcategory = categoryTableView.getSelectionModel().getSelectedItem();
            if (selectedcategory != null) {
                System.out.println("Selected category: " + selectedcategory.getName());
                categContextMenu.show(categoryTableView, eh.getScreenX(), eh.getScreenY());
            }
        });

        // priorityTableView
        ContextMenu prioContextMenu = new ContextMenu();

        MenuItem editPriority = new MenuItem("Edit Priority");
        MenuItem deletePriority = new MenuItem("Delete Priority");

        editPriority.setOnAction(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Edit Priority: " + selectedPriority.getName());
            }
        });
        deletePriority.setOnAction(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Delete Priority: " + selectedPriority.getName());
                if (selectedPriority.getName().equals("Default")) {
                    selectedPriority.deleteDefaultPriority();
                } else {
                    App.deletePriority(selectedPriority);
                }
                populatePriorityTableView();
                showTaskBarChart();
            }
        });

        prioContextMenu.getItems().addAll(editPriority, deletePriority);

        priorityTableView.setOnMouseClicked(eh -> {
            Priority selectedPriority = priorityTableView.getSelectionModel().getSelectedItem();
            if (selectedPriority != null) {
                System.out.println("Selected Priority: " + selectedPriority.getName());
                prioContextMenu.show(priorityTableView, eh.getScreenX(), eh.getScreenY());
            }
        });

        // reminderTableView
        ContextMenu reminderContextMenu = new ContextMenu();

        MenuItem editReminder = new MenuItem("Edit Reminder");
        MenuItem deleteReminder = new MenuItem("Delete Reminder");

        editReminder.setOnAction(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                System.out.println("Edit Reminder: " + selectedReminder.getReminderDate().toString());
            }
        });
        deleteReminder.setOnAction(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                selectedReminder.deleteReminder();
                populateReminderTableView();
            }
        });

        reminderContextMenu.getItems().addAll(editReminder, deleteReminder);

        reminderTableView.setOnMouseClicked(eh -> {
            Reminder selectedReminder = reminderTableView.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                reminderContextMenu.show(reminderTableView, eh.getScreenX(), eh.getScreenY());
            }
        });
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                                           Actions
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Search Bar
    public void showSearchBar() {
        searchTask.setVisible(true);
    }

    public void hideSearchBar() {
        searchTask.setVisible(false);
    }

    public void populateTaskTableView() {
        taskTableView.getItems().clear();
        taskTableView.getItems().addAll(TaskList.tasks);
        taskTableView.refresh();
        taskTableView.toFront();
        showSearchBar();
    }

    public void populateCategoryTableView() {
        categoryTableView.getItems().clear();
        categoryTableView.getItems().addAll(App.getCategories());
        categoryTableView.refresh();
        categoryTableView.toFront();
        hideSearchBar();
    }

    public void populatePriorityTableView() {
        priorityTableView.getItems().clear();
        priorityTableView.getItems().addAll(App.getPriorities());
        priorityTableView.refresh();
        priorityTableView.toFront();
        hideSearchBar();
    }

    public void populateReminderTableView() {
        reminderTableView.getItems().clear();
        for (Task task : TaskList.tasks) {
            for (Reminder reminder : task.getReminders()) {
                reminderTableView.getItems().add(reminder);
            }
        }
        reminderTableView.refresh();
        reminderTableView.toFront();
        hideSearchBar();
    }

    public void showTaskBarChart() {
        // Clear chart
        taskBarChart.getData().clear();

        // Set up the chart
        CategoryAxis xAxis = (CategoryAxis) taskBarChart.getXAxis();
        xAxis.setLabel("Task Categories");

        NumberAxis yAxis = (NumberAxis) taskBarChart.getYAxis();
        yAxis.setLabel("Number of Tasks");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tasks Statuses");

        long totalTasks = TaskList.tasks.size();
        long completedTasks = TaskList.tasks.stream().filter(task -> "Completed".equals(task.getStatus()))
                .toArray().length;
        long delayedTasks = TaskList.tasks.stream().filter(task -> "Delayed".equals(task.getStatus())).toArray().length;
        long tasksDueWeek = TaskList.tasks.stream()
                .filter(task -> task.getDeadline().isBefore(task.getDeadline().plusDays(7))
                        && task.getDeadline().isAfter(LocalDate.now()))
                .toArray().length;

        series.getData().add(new XYChart.Data<>("Total Tasks", totalTasks));
        series.getData().add(new XYChart.Data<>("Completed Tasks", completedTasks));
        series.getData().add(new XYChart.Data<>("Delayed Tasks", delayedTasks));
        series.getData().add(new XYChart.Data<>("Tasks Due in a Week", tasksDueWeek));

        taskBarChart.getData().add(series);

    }

    public void openEditWindow(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("task.fxml"));

        Scene scene = new Scene(loader.load(), 420, 540);
        Stage stage = new Stage();

        // Get the controller
        TaskController controller = loader.getController();
        controller.editTask(task);
        controller.setOriginalWindow(this);

        stage.setTitle("Edit");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public void refresh() {
        taskTableView.refresh();
        categoryTableView.refresh();
        priorityTableView.refresh();
        reminderTableView.refresh();
    }
}
