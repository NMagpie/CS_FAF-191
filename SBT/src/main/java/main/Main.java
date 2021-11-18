package main;

import controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main extends Application {
    private static final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));

    private static final FXMLLoader fxmlLoaderCheck = new FXMLLoader(Main.class.getResource("viewCheck.fxml"));

    public static FXMLLoader getFxmlLoaderCheck() {
        return fxmlLoaderCheck;
    }

    public static Controller getController() {
        return fxmlLoader.getController();
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
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