package controller;

import db.ProfileService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Session;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ProfileController {
    @FXML private ImageView profileImageView;
    @FXML private Button changeAvatarButton;

    private final ProfileService profileService = new ProfileService();

    @FXML
    public void initialize() {
        loadAvatar();
        changeAvatarButton.setOnAction(e -> handleChangeAvatar());
    }

    private void loadAvatar() {
        byte[] bytes = profileService.getAvatar(
            Session.getInstance().getCurrentUser().getUsername()
        );
        if (bytes != null) {
            profileImageView.setImage(new Image(new ByteArrayInputStream(bytes)));
        }
    }

    private void handleChangeAvatar() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        File file = chooser.showOpenDialog(profileImageView.getScene().getWindow());
        if (file != null) {
            try {
                profileService.uploadAvatar(
                    Session.getInstance().getCurrentUser().getUsername(),
                    file.toPath()
                );
                loadAvatar();  // reload after upload
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
