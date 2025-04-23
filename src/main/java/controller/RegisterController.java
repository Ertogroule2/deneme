package controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.MongoDBConnection;
import db.PortfolioService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import model.Session;
import org.bson.Document;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;

    private final PortfolioService portfolioService = new PortfolioService();

    @FXML
    public void handleRegister() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText();
        String c = confirmField.getText();
        if (!p.equals(c)) {
            new Alert(Alert.AlertType.ERROR, "Passwords don’t match").show();
            return;
        }

        MongoDatabase db = MongoDBConnection.getDatabase("investedDB");
        MongoCollection<Document> users = db.getCollection("users");
        if (users.find(new Document("username", u)).first() != null) {
            new Alert(Alert.AlertType.ERROR, "Username already taken").show();
            return;
        }

        // 1) make initial portfolio
        String pid = portfolioService.createInitialPortfolio(u, 100_000.0);

        // 2) insert user record with portfolioId
        Document userDoc = new Document("username", u)
            .append("password", p)
            .append("portfolioId", pid);
        users.insertOne(userDoc);

        // 3) session & move to main
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
    private void goToLogin() {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(login));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
