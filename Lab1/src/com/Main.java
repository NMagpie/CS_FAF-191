package com;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class Main extends Application{

    TableView<CustomItem> table = new TableView<>();

    static Label statusText = new Label("                ");

    TextField searchBar = new TextField();

    public static void main(String[] args) throws FileNotFoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane mainPane = new BorderPane();

        searchBar.setPromptText("Search...");

        table.setEditable(false);

        TableColumn<CustomItem, CheckBox> selectedColumn = new TableColumn<>("Selected");
        selectedColumn.setCellValueFactory(new PropertyValueFactory<>("selectedCB"));
        selectedColumn.setStyle("-fx-alignment: CENTER;");
        selectedColumn.setResizable(false);
        selectedColumn.setMaxWidth(100);
        table.getColumns().add(selectedColumn);

        TableColumn<CustomItem,String> regKeyColumn = new TableColumn<>("Reg Key");
        regKeyColumn.setCellValueFactory(new PropertyValueFactory<>("regKey"));
        regKeyColumn.setMinWidth(200);
        table.getColumns().add(regKeyColumn);

        TableColumn<CustomItem,String>  regItemColumn = new TableColumn<>("Reg Item");
        regItemColumn.setCellValueFactory(new PropertyValueFactory<>("regItem"));
        regItemColumn.setMinWidth(200);
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

        table.setMinWidth(900);

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
                {
                    String check = CoreApp.buildJSONbyObject();
                    if ((check!=null)&&(!check.equals("[]"))) {
                        bufferedWriter.write(CoreApp.buildJSONbyObject()); statusText.setText("File was saved successfully!"); }
                    else JOptionPane.showMessageDialog(null, "No policies are selected!");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        CheckBox selectAll = new CheckBox("Select All");

        selectAll.setTranslateY(3);
        selectAll.setTranslateX(10);

        selectAll.setOnAction(event -> {
            if (CoreApp.getCustomItems()!=null)
                    for (CustomItem customItem: CoreApp.getCustomItems())
                        if (selectAll.isSelected()) {customItem.setSelected(true); customItem.setSelectedCB();}
                        else {customItem.setSelected(false); customItem.setSelectedCB();}
        });

        hBox.getChildren().addAll(selectAll,browse,save,searchBar,statusText);

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
        stage.setMinWidth(900);
        stage.setResizable(false);
        stage.setMinHeight(500);
        stage.show();
    }

    public void updateTable() {

        table.getItems().clear();

        ObservableList<CustomItem> observableList = FXCollections.observableArrayList(CoreApp.getCustomItems());

        FilteredList<CustomItem> filteredItems = new FilteredList<>(observableList, b-> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(customItem -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customItem.getRegKey()!=null&&customItem.getRegKey().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (customItem.getRegItem()!=null&&customItem.getRegItem().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getType()!=null&&customItem.getType().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getValueData()!=null&&customItem.getValueData().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getValueType()!=null&&customItem.getValueType().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getDescription()!=null&&customItem.getDescription().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getInfo()!=null&&customItem.getInfo().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getReference()!=null&&customItem.getReference().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (customItem.getRegOption()!=null&&customItem.getRegOption().toLowerCase().contains(lowerCaseFilter)) { return true; }
                    else return false;

                    }
            );
                }
        );

        SortedList<CustomItem> sortedList = new SortedList<>(filteredItems);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

}