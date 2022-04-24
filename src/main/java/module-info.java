module com.iDebug.pickloose {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    requires java.desktop;
    opens com.iDebug.pickloose to com.google.gson,javafx.fxml;
    opens com.iDebug.pickloose.network to com.google.gson;
    opens com.iDebug.pickloose.network.client to com.google.gson;

    exports com.iDebug.pickloose;
    exports com.iDebug.pickloose.fxcontroller;
    opens com.iDebug.pickloose.fxcontroller to com.google.gson, javafx.fxml;
}