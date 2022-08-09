module com.iDebug.pickloose {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.bootstrapfx.core;
    requires org.controlsfx.controls;
    requires com.jfoenix;

    requires com.google.gson;
    requires java.desktop;
    requires java.sql;
    opens com.iDebug.pickloose to javafx.controls,com.google.gson,javafx.fxml,org.controlsfx.controls;
    opens com.iDebug.pickloose.network to com.google.gson;
    opens com.iDebug.pickloose.network.client to com.google.gson;

    exports com.iDebug.pickloose;
    exports com.iDebug.pickloose.fxcontroller;
    opens com.iDebug.pickloose.fxcontroller to com.google.gson, javafx.fxml;
    exports com.iDebug.pickloose.animation;
    opens com.iDebug.pickloose.animation to com.google.gson, javafx.controls, javafx.fxml, org.controlsfx.controls;
}