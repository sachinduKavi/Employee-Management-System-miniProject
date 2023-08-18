module com.example.emp_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;

    opens com.example.emp_system to javafx.fxml;
    exports com.example.emp_system;
}