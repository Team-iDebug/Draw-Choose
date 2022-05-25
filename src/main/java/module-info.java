module com.example.idebug {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;

    opens com.example.idebug to javafx.fxml;
    exports com.example.idebug;
}