module sbt {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires lombok;
    requires java.desktop;
    requires java.prefs;

    opens core to com.fasterxml.jackson.databind, java.prefs;
    opens customitem to com.fasterxml.jackson.databind;
    opens controllers to javafx.fxml;
    opens main to java.base, java.prefs, javafx.fxml;

    exports controllers;
    exports core.registryutils.registryitem;
    exports main;
    exports customitem;
}