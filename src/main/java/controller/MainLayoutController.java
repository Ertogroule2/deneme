package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.css.Size;
import javafx.scene.Scene;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {
    @FXML private StackPane contentPane;
    @FXML private HBox navBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1) load initial view
        showPortfolio();

        // 2) once scene is available, bind navBar height to 25% of scene height
        navBar.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                navBar.prefHeightProperty()
                      .bind(newScene.heightProperty().multiply(0.25));
            }
        });
    }

    private void loadView(String fxml) {
        try {
            Node view = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            contentPane.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void showPortfolio()   { loadView("portfolio.fxml"); }
    @FXML private void showExplore()     { loadView("explore.fxml"); }
    @FXML private void showFriends()     { loadView("friends.fxml"); }
    @FXML private void showLeaderboards(){ loadView("leaderboards.fxml"); }
    @FXML private void showInbox()       { loadView("inbox.fxml"); }
    @FXML private void showMultiplayer() { loadView("multiplayer.fxml"); }
    @FXML private void showProfile()     { loadView("profile.fxml"); }
}
