package controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoDBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import model.Session;
import org.bson.Document;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleLogin() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText();

        MongoDatabase db = MongoDBConnection.getDatabase("investedDB");
        MongoCollection<Document> users = db.getCollection("users");
        Document found = users.find(new Document("username", u)
                                    .append("password", p))
                              .first();
        if (found == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials").show();
            return;
        }

        // read their portfolioId
        String pid = found.getString("portfolioId");

        // session & go main
        User user = new User(u, p, pid);
        Session.getInstance().setCurrentUser(user);

        try {
            Parent main = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
            Stage st = (Stage) usernameField.getScene().getWindow();
            st.setScene(new Scene(main));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        try {
            // Load the register view
            Parent registerRoot = FXMLLoader.load(
                getClass().getResource("/view/register.fxml")
            );
            // Get current window (stage) and set the new scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
            stage.setTitle("InvestEd — Register");
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally show an alert here
        }
    }
}
