package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField countryField;
    @FXML private TextArea descriptionField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: preload current profile info
    }

    @FXML private void handleSave() {
        // TODO: save profile changes
    }

    @FXML private void handleCancel() {
        // TODO: return to profile view
    }
}