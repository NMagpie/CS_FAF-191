package com;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
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

    TableView<CustomItem> table = new TableView<>();

    static Label statusText = new Label("                ");

    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane mainPane = new BorderPane();

        TableColumn<CustomItem, CheckBox> selectedColumn = new TableColumn<>("Selected");
        selectedColumn.setCellValueFactory(new PropertyValueFactory<>("selectedCB"));
        table.getColumns().add(selectedColumn);

        TableColumn<CustomItem,String> regKeyColumn = new TableColumn<>("Reg Key");
        regKeyColumn.setCellValueFactory(new PropertyValueFactory<>("regKey"));
        table.getColumns().add(regKeyColumn);

        TableColumn<CustomItem,String>  regItemColumn = new TableColumn<>("Reg Item");
        regItemColumn.setCellValueFactory(new PropertyValueFactory<>("regItem"));
        table.getColumns().add(regItemColumn);

        TableColumn<CustomItem,String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        table.getColumns().add(typeColumn);

        TableColumn<CustomItem,String> regOptionColumn = new TableColumn<>("Reg option");
        regOptionColumn.setCellValueFactory(new PropertyValueFactory<>("regOption"));
        table.getColumns().add(regOptionColumn);

        TableColumn<CustomItem,String> valueTypeColumn = new TableColumn<>("Value Type");
        valueTypeColumn.setCellValueFactory(new PropertyValueFactory<>("valueType"));
        table.getColumns().add(valueTypeColumn);

        TableColumn<CustomItem,String> valueDataColumn = new TableColumn<>("Value Data");
        valueDataColumn.setCellValueFactory(new PropertyValueFactory<>("valueData"));
        table.getColumns().add(valueDataColumn);

        TableColumn<CustomItem,String> referenceColumn = new TableColumn<>("Reference");
        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        table.getColumns().add(referenceColumn);

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

                if (file != null) {
                    statusText.setText("File was imported successfully!");

                    try {
                        if (file.getName().matches(".*[.]audit"))
                        CoreApp.parseFileObject(file);
                        else CoreApp.parseJSONFile(file);

                        updateTable();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    statusText.setText("File was parsed successfully!");

                }
            });

            Button save = new Button("Save...");
        save.setMinWidth(200);
        save.setOnAction(event -> {
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

        CheckBox selectAll = new CheckBox("Select All");

        selectAll.setOnAction(event -> {
            if (CoreApp.getCustomItems()!=null)
                    for (CustomItem customItem: CoreApp.getCustomItems())
                        if (selectAll.isSelected()) {customItem.setSelected(true); customItem.setSelectedCB();}
                        else {customItem.setSelected(false); customItem.setSelectedCB();}
        });

        hBox.getChildren().addAll(selectAll,browse,save,statusText);

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

        table.getItems().clear();

        ObservableList<CustomItem> observableList = FXCollections.observableArrayList(CoreApp.getCustomItems());

        table.setItems(observableList);
    }

}