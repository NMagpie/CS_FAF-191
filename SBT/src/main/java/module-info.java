module sbt {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires lombok;
    requires java.desktop;

    opens main to javafx.fxml;
    opens core to com.fasterxml.jackson.databind;
    opens customitem to com.fasterxml.jackson.databind, javafx.base;

    exports main;
}