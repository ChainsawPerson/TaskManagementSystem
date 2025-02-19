package com.taskmansys.gui.helpers.buttons;

import com.taskmansys.App;
import com.taskmansys.gui.Controller;
import com.taskmansys.gui.helpers.TaskBarChartHelper;
import com.taskmansys.gui.helpers.tables.CategoryTableViewHelper;
import com.taskmansys.model.Category;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoriesButton {
    public static void setupCategoriesButton(Controller controller, Button totalCategoriesButton, TableView<Category> categoryTableView, TextField searchTask, BarChart<String, Number> taskBarChart) {
        // Handler for Categories Button and Categories Functions:
        totalCategoriesButton.setOnAction(event -> CategoryTableViewHelper.populateCategoryTableView(categoryTableView, searchTask));

        // categoryTableView Functions (Edit/Delete)
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
                CategoryTableViewHelper.populateCategoryTableView(categoryTableView, searchTask);
                TaskBarChartHelper.showTaskBarChart(taskBarChart);
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
    }
}
