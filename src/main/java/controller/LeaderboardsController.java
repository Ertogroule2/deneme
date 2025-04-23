package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {
    @FXML private ComboBox<String> sortComboBox;
    @FXML private VBox leaderboardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: populate sort options and leaderboardList
    }
}