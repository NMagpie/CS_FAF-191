package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));

    public static Controller getController() {
        return fxmlLoader.getController();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("STB");
        stage.setScene(scene);
        stage.show();
    }

}