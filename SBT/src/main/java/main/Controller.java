package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Core;
import customitem.CustomItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    TableView<CustomItem> table = new TableView<>();

    @FXML
    Label statusText = new Label();

    @FXML
    TextField searchBar = new TextField();

    @FXML
    Button browse = new Button();
    @FXML
    Button save = new Button();
    @FXML
    CheckBox selectAll = new CheckBox();

    public void setStatusText(String string) {
        this.statusText.setText(string);
    }

    @FXML
    protected void browseFile() throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audit or JSON files", "*.audit", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            statusText.setText("File was imported successfully!");

            if (file.getName().matches(".*\\.audit$"))
                Core.parseFileObject(file);
            else Core.parseJSONFile(file);

            updateTable();

            statusText.setText("File was parsed successfully!");

        }
    }

    @FXML
    protected void saveFile() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<CustomItem> items = Core.selectedArrayList();

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No policies are selected!");
            return;
        }

        FileChooser fileChooserSave = new FileChooser();
        fileChooserSave.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File fileSave = fileChooserSave.showSaveDialog(null);

        if (fileSave != null) {

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileSave, Core.selectedArrayList());

            statusText.setText("File was saved successfully!");
        }
    }

    @FXML
    protected void selectAllAction() {
        if (Core.getCustomItems() != null)
            for (CustomItem customItem : Core.getCustomItems())
                if (selectAll.isSelected()) {
                    customItem.setSelected(true);
                    customItem.setSelectedCB();
                } else {
                    customItem.setSelected(false);
                    customItem.setSelectedCB();
                }
    }

    private void updateTable() {

        ObservableList<CustomItem> observableList = FXCollections.observableArrayList(Core.getCustomItems());

        FilteredList<CustomItem> filteredItems = new FilteredList<>(observableList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredItems.setPredicate(customItem -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }

                            String lowerCaseFilter = newValue.toLowerCase();

                            if (customItem.getRegKey() != null && customItem.getRegKey().toLowerCase().contains(lowerCaseFilter))
                                return true;
                            else if (customItem.getRegItem() != null && customItem.getRegItem().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getType() != null && customItem.getType().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getValueData() != null && customItem.getValueData().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getValueType() != null && customItem.getValueType().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getDescription() != null && customItem.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getInfo() != null && customItem.getInfo().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (customItem.getReference() != null && customItem.getReference().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else
                                return customItem.getRegOption() != null && customItem.getRegOption().toLowerCase().contains(lowerCaseFilter);

                        }
                )
        );

        SortedList<CustomItem> sortedList = new SortedList<>(filteredItems);

        //table.setItems(sortedList);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

}