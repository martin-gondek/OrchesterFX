module com.example.orchesterfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.orchesterfx to javafx.fxml;
    exports com.example.orchesterfx;
}