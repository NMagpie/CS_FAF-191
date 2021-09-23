package com;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class Main extends Application{

    String path;
    static Label statusText = new Label();

    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        GridPane gPane = new GridPane();

        Button browse = new Button("Browse...");
        browse.setMinWidth(200);
        browse.setOnAction(eventBrowse -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audit or JSON files", "*.audit", "*.json")/*, new FileChooser.ExtensionFilter("JSON files","*.json")*/);
                File file = fileChooser.showOpenDialog(null);
                statusText.setText("File was imported successfully!");

                if (file != null) {
                    path = file.getAbsolutePath();

                    try {
                        if (file.getName().matches(".*[.]audit"))
                        CoreApp.parseFileObject(file);
                        else CoreApp.parseJSONFile(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    statusText.setText("File was parsed successfully!");

                } else {
                    //System.out.println("File is not found!");
                    statusText.setText("File is not found!");
                }
            });

            Button save = new Button("Save...");
        save.setMinWidth(200);
        save.setOnAction(SaveEvent -> {
                FileChooser fileChooserSave = new FileChooser();
                fileChooserSave.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files","*.json"));
                File fileSave = fileChooserSave.showSaveDialog(null);

                if (fileSave != null) {
                    try (BufferedWriter bufferedWriter= Files.newBufferedWriter(fileSave.toPath(), StandardCharsets.UTF_8))
                    { bufferedWriter.write(CoreApp.buildJSONbyObject()); } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                statusText.setText("File was saved successfully!");
        });

        gPane.add(browse,0,0);

        browse.setMaxWidth(Double.MAX_VALUE);
        browse.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(browse, Priority.ALWAYS);
        GridPane.setVgrow(browse, Priority.ALWAYS);
        GridPane.setMargin(browse, new Insets(10));

        gPane.add(save,1,0);

        save.setMaxWidth(Double.MAX_VALUE);
        save.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(save, Priority.ALWAYS);
        GridPane.setVgrow(save, Priority.ALWAYS);
        GridPane.setMargin(save, new Insets(10));

        gPane.add(statusText,0,1);
        statusText.setMaxWidth(Double.MAX_VALUE);
        statusText.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(statusText, Priority.ALWAYS);
        GridPane.setVgrow(statusText, Priority.ALWAYS);
        GridPane.setMargin(statusText, new Insets(10));

        Group group = new Group(gPane);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("SBT App");
        stage.setMinWidth(450);
        stage.setResizable(false);
        stage.setMinHeight(100);
        stage.show();
    }

}