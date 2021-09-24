package com;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class Main extends Application{

    String path;

    TableView<CustomItem> table = new TableView<>();

    static Label statusText = new Label("                ");

    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane mainPane = new BorderPane();

        table.setMinWidth(700);

        table.setTranslateY(15);

        HBox hBox = new HBox();
        hBox.setLayoutY(40);
        hBox.setLayoutX(40);
        hBox.setSpacing(20);

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

                        updateTable();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    statusText.setText("File was parsed successfully!");

                } else {
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

        hBox.getChildren().addAll(browse,save,statusText);

        browse.setMaxWidth(Double.MAX_VALUE);
        browse.setMaxHeight(Double.MAX_VALUE);

        save.setMaxWidth(Double.MAX_VALUE);
        save.setMaxHeight(Double.MAX_VALUE);

        statusText.setMaxWidth(Double.MAX_VALUE);
        statusText.setMaxHeight(Double.MAX_VALUE);

        mainPane.setTop(hBox);

        mainPane.setCenter(table);

        Group group = new Group(mainPane);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("SBT App");
        stage.setMinWidth(700);
        stage.setResizable(false);
        stage.setMinHeight(500);
        stage.show();
    }

    public void updateTable() {
        ObservableList<CustomItem> observableList = FXCollections.observableArrayList(CoreApp.getCustomItems());
        table = new TableView<>(observableList);

        TableColumn<CustomItem,String> regKeyColumn = new TableColumn<>("Reg Key");
        regKeyColumn.setCellValueFactory(new PropertyValueFactory<>("regKey"));
        table.getColumns().add(regKeyColumn);

        table.refresh();

        table.setItems(observableList);

    }

}