module com.taskmansys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    
    opens com.taskmansys.gui to javafx.fxml;
    exports com.taskmansys;
    exports com.taskmansys.gui;
}
