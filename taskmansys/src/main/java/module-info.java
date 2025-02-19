module com.taskmansys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires transitive javafx.graphics;
    
    opens com.taskmansys.gui to javafx.fxml;
    opens com.taskmansys to javafx.fxml;
    
    exports com.taskmansys;
    exports com.taskmansys.gui;
    exports com.taskmansys.model;
}
