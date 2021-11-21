package controllers;

import core.registryutils.Registry;
import core.registryutils.registryitem.Item;
import core.registryutils.registryitem.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerCheck {

    @FXML
    private BorderPane borderPane = new BorderPane();

    @FXML
    private ListView<Item> itemListView = new ListView<>();

    @FXML
    private Button processItemsButton = new Button();

    @FXML
    public void processItems() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Before processing Registry Items do you want to create backup of the registry? \nPress \"Cancel\" to refuse processing.", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DirectoryChooser dc = new DirectoryChooser();
            File file = dc.showDialog(null);

            Runtime.getRuntime().exec("reg export HKCR "+ file.getAbsolutePath() +"\\HKCR.Reg /y");
            Runtime.getRuntime().exec("reg export HKCU "+ file.getAbsolutePath() +"\\HKCU.Reg /y");
            Runtime.getRuntime().exec("reg export HKLM "+ file.getAbsolutePath() +"\\HKLM.Reg /y");
            Runtime.getRuntime().exec("reg export HKCU "+ file.getAbsolutePath() +"\\HKCU.Reg /y");
            Runtime.getRuntime().exec("reg export HKCC "+ file.getAbsolutePath() +"\\HKCC.Reg /y");

            Registry.processItems();
        }
        if (alert.getResult() == ButtonType.NO)
            Registry.processItems();
    }


    public void setItemListView(ArrayList<Item> itemListView) {

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(itemListView);

        this.itemListView.setItems(itemObservableList);

        this.itemListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if(item != null) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/viewItem.fxml"));

                    AnchorPane root = null;

                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

        str = item.getStatus().toString();

        str = str.replaceAll("() ([A-Z])", "$1 $2");

        label.setText(str);

        if (item.getStatus() == Status.Ok) label.setTextFill(Color.rgb(0, 229, 0)); else
            label.setTextFill(Color.rgb(235, 0, 0));
    }
}
