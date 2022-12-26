package controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import oauthlib.OAuthAuthenticator;
import oauthlib.OAuthGithubAuthenticator;
import oauthlib.OAuthGoogleAuthenticator;
import org.json.JSONObject;
import user.User;

import java.io.FileNotFoundException;

public class ControllerLogin {

    @FXML
    Button loginGoogleButton = new Button();

    @FXML
    Button loginGithubButton = new Button();

    @FXML
    public void loginGoogle() throws FileNotFoundException {

        String gClientId = "###";
        String gRedir = "https://www.google.com";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email";
        String gSecret = "###";
        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin();

        if (auth.hasFinishedSuccessfully()) {
        JSONObject object = auth.getAccessedJsonData();

        User.setLoggedIn(true);

        User.setName(object.getString("name"));

        User.setEmail(object.getString("email"));

        User.setAccountType("Google");

        User.setAvatar(object.getString("picture"));

        String info = "Lang: " + object.getString("locale") + "\n" +
                      "Verified: " + object.getBoolean("verified_email");

        User.setInfo(info);
        }
    }

    @FXML
    public void loginGithub() throws FileNotFoundException {

        String fClientId = "###";
        String fRedir = "https://www.google.com";
        String fFields = "user:email,read:user";
        String fSecret = "###";
        OAuthAuthenticator auth = new OAuthGithubAuthenticator(fClientId, fRedir, fSecret, fFields);
        auth.startLogin();

        if (auth.hasFinishedSuccessfully()) {
            JSONObject object = auth.getAccessedJsonData();

            User.setLoggedIn(true);

            User.setName(object.getString("username"));

            User.setEmail(object.getString("email"));

            User.setAccountType("GitHub");

            User.setAvatar(object.getString("picture"));

            String info = "Lang: " + object.getString("locale") + "\n" +
                    "Verified: " + object.getBoolean("verified_email");

            User.setInfo(info);
        }
    }

    @FXML
    public void hideWindow() {
        Controller.closeUser();
    }

}
