package com.taskmansys.gui.helpers;

import java.time.LocalDate;

import com.taskmansys.model.TaskList;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TaskBarChartHelper {
    public static void showTaskBarChart(BarChart<String, Number> taskBarChart) {
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
}
