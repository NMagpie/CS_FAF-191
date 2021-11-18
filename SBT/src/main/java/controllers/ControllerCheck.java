package controllers;

import core.registryutils.registryitem.Item;
import core.registryutils.registryitem.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;

import java.util.ArrayList;

public class ControllerCheck {

    @FXML
    BorderPane borderPane = new BorderPane();

    @FXML
    ListView<Item> itemListView = new ListView<>();

    public void setItemListView(ArrayList<Item> itemListView) {

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(itemListView);

        this.itemListView.setItems(itemObservableList);

        this.itemListView.setCellFactory(param -> new ListCell<>() {
            @SneakyThrows
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/viewItem.fxml"));

                    AnchorPane root;

                    root = loader.load();

                    setRootData(root, item);

                    setGraphic(root);
                }
            }
        });
    }

    private void setRootData(AnchorPane root, Item item) {
        Label label = (Label) root.getChildren().get(1);

        label.setText(item.getPath());

        label = (Label) root.getChildren().get(3);

        label.setText(item.getCustomItem().getRegItem());

        label = (Label) root.getChildren().get(5);

        String str = item.getCurrentValue();

        if (str == null) str = "null";

        label.setText(str);

        label = (Label) root.getChildren().get(7);

        label.setText(item.getCustomItem().getValueData());

        label = (Label) root.getChildren().get(9);

        label.setText(item.getStatus().toString());

        if (item.getStatus() == Status.Ok) label.setTextFill(Color.rgb(0, 229, 0)); else
            label.setTextFill(Color.rgb(235, 0, 0));
    }
}
