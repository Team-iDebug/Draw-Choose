module com.example.idebug {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    opens com.example.idebug to com.google.gson,javafx.fxml;
    opens com.example.idebug.network to com.google.gson;

    exports com.example.idebug;
}