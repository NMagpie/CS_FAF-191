package controllers;

import javafx.fxml.FXML;

import io.github.gleidson28.GNAvatarView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import main.Main;
import user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ControllerUser {

    @FXML
    GNAvatarView avatar = new GNAvatarView();

    @FXML
    Label nameLabel = new Label();

    @FXML
    Label emailLabel = new Label();

    @FXML
    Label infoLabel = new Label();

    @FXML
    Button logOutButton = new Button();

    public GNAvatarView getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar.setImage(new Image(avatar));
    }

    public void setNameLabel(String name) {
        this.nameLabel.setText(name);
    }

    public void setEmailLabel(String email) {
        this.emailLabel.setText(email);
    }

    public void setInfoLabel(String info) {
        this.infoLabel.setText(info);
    }

    @FXML
    public void logOut() throws FileNotFoundException {
        User.setLoggedIn(false);
        User.setAccountType(null);
        User.setName(null);
        User.setEmail(null);
        User.setInfo(null);
        String defaultAvatar = "src/main/resources/main/default-img.jpg";
        Main.getController().setAvatar(defaultAvatar);
        avatar.setImage(new Image(new FileInputStream(defaultAvatar)));
        Controller.closeUser();
    }

    @FXML
    public void hideWindow() {
        Controller.closeUser();
    }

}
