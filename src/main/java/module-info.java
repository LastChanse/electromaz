module com.example.electromaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.datatransfer;
    requires java.desktop;
    requires itextpdf;

    opens com.example.electromaz to javafx.fxml;
    exports com.example.electromaz;
    exports com.example.electromaz.Models;
}