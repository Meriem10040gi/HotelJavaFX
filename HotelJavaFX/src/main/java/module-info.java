module com.example.hoteljavafx {
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires static lombok;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.json;
    requires stripe.java;
    requires jdk.httpserver;
    requires jbcrypt;
    opens com.example.hoteljavafx.Controller to javafx.fxml;
    opens com.example.hoteljavafx to javafx.fxml;
    opens com.example.hoteljavafx.Utils to javafx.base;
    exports com.example.hoteljavafx;
}
