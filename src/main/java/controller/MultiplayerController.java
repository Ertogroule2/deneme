package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerController implements Initializable {
    @FXML private VBox lobbiesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: load game lobbies
    }

    @FXML private void handleCreateLobby() {
        // TODO: open create lobby dialog
    }
}