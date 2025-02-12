module com.taskmansys {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    
    opens com.taskmansys to javafx.fxml;
    exports com.taskmansys;
}
