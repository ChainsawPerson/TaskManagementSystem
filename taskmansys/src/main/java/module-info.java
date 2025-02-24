open module com.taskmansys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires transitive javafx.graphics;
    
    exports com.taskmansys;
    exports com.taskmansys.gui;
    exports com.taskmansys.model;
}
