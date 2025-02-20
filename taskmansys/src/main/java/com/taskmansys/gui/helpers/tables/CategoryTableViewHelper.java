package com.taskmansys.gui.helpers.tables;

import com.taskmansys.App;
import com.taskmansys.gui.helpers.SearchBar;
import com.taskmansys.model.Category;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoryTableViewHelper {
    // Categories Table
    public static void setupCategoryTableColumns(TableView<Category> categoryTableView, TableColumn<Category, String> categoryNameColumn, TableColumn<Category, Integer> categoryNoTasksColumn) {
        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryNoTasksColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTasks().size()).asObject());
    }

    public static void populateCategoryTableView(TableView<Category> categoryTableView, TextField searchTask) {
        categoryTableView.getItems().clear();
        categoryTableView.getItems().addAll(App.getCategories());
        categoryTableView.refresh();
        SearchBar.hideSearchBar(searchTask);
    }
}
