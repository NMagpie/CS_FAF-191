package user;

import controllers.Controller;
import controllers.ControllerUser;
import io.github.gleidson28.GNAvatarView;
import javafx.scene.control.Label;
import main.Main;

import java.io.FileNotFoundException;

public class User {

    private static ControllerUser user = Controller.getControllerUser();

    private static boolean loggedIn = false;

    private static String name;

    private static String email;

    private static String info;

    private static String accountType;

    private static String avatar;

    public static String getAvatar() {
        return avatar;
    }

    public static void setAvatar(String avatar) throws FileNotFoundException {
        User.avatar = avatar;
        user.setAvatar(avatar);
        Main.getController().setAvatar(avatar);
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        User.loggedIn = loggedIn;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
        user.setNameLabel(name);
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
        user.setEmailLabel(email);
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        User.info = info;
        user.setInfoLabel(info);
    }

    public static String getAccountType() {
        return accountType;
    }

    public static void setAccountType(String accountType) {
        User.accountType = accountType;
    }
}
