package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.PortfolioItem;
import db.PortfolioService;
import service.BinanceService;
import model.Session;

import java.util.List;

public class PortfolioController {
    @FXML private VBox portfolioContainer;
    @FXML private Label capitalLabel;
    @FXML private ImageView profileImage;

    private final PortfolioService   dbService      = new PortfolioService();
    private final BinanceService     binanceService = new BinanceService();
    private final String             pid            =
        Session.getInstance().getCurrentUser().getPortfolioId();

    @FXML
    public void initialize() {
        // load a static default avatar from resources
        profileImage.setImage(new Image(
            getClass().getResourceAsStream("/images/defaultuser.png")
        ));

        Timeline t = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> refresh()),
            new KeyFrame(Duration.seconds(10))
        );
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void refresh() {
        portfolioContainer.getChildren().clear();
        List<PortfolioItem> items = dbService.fetchPortfolioItems(pid);

        // find cash item
        double cash = items.stream()
                           .filter(i -> "CASH".equals(i.getSymbol()))
                           .mapToDouble(PortfolioItem::getQuantity)
                           .sum();
        capitalLabel.setText(String.format("$%,.2f", cash));

        for (PortfolioItem it : items) {
            if ("CASH".equals(it.getSymbol())) continue;

            double price = 0;
            try {
                price = binanceService.fetchPrice(it.getSymbol() + "USDT");
            } catch (Exception ex) { /* ignore */ }
            double value = it.getQuantity() * price;

            HBox row = new HBox(15);
            row.setStyle("-fx-background-color:gray; -fx-padding:10; -fx-background-radius:8;");

            ImageView icon = new ImageView(new Image(
                getClass().getResourceAsStream("/images/" + it.getSymbol() + ".png"),
                32,32,true,true
            ));

            Label name = new Label(it.getSymbol());
            name.setStyle("-fx-text-fill:white; -fx-font-size:16;");

            Label val = new Label(String.format("$%,.2f", value));
            val.setStyle("-fx-text-fill:white; -fx-font-size:16;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button buy  = new Button("Buy");
            Button sell = new Button("Sell");
            buy.setOnAction(e -> trade(it, true));
            sell.setOnAction(e -> trade(it, false));

            row.getChildren().addAll(icon, name, spacer, val, buy, sell);
            portfolioContainer.getChildren().add(row);
        }
    }

    private void trade(PortfolioItem it, boolean buy) {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText((buy ? "Buy " : "Sell ") + it.getSymbol());
        dlg.showAndWait().ifPresent(s -> {
            double amt = Double.parseDouble(s);
            double newQty = it.getQuantity() + (buy ? amt : -amt);
            if (newQty < 0) newQty = 0;
            dbService.upsertItem(pid, it.getSymbol(), newQty);
        });
    }

    @FXML private void goToProfile() {
        // … your existing nav code …
    }
}
