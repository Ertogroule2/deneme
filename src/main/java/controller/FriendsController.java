package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {
    @FXML private VBox friendsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: load friends into friendsList
    }

    @FXML private void handleAddFriend() {
        // TODO: show add friend dialog
    }
}