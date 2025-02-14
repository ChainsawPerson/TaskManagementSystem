module com.taskmansys {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    
    opens com.taskmansys to javafx.fxml;
    exports com.taskmansys;
}
