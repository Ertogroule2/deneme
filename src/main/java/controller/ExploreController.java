package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import service.BinanceService;

import java.net.URL;
import java.util.ResourceBundle;

public class ExploreController implements Initializable {
    @FXML private Label btcPrice;
    @FXML private Label ethPrice;

    private final BinanceService binanceService = new BinanceService();
    private Timeline updater;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Schedule a price refresh every 10 seconds, starting immediately
        updater = new Timeline(
            new KeyFrame(Duration.ZERO, e -> refreshPrices()),
            new KeyFrame(Duration.seconds(10))
        );
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.play();

        System.out.println("btcPrice = " + btcPrice);
        System.out.println("ethPrice = " + ethPrice);

    }

    private void refreshPrices() {
        try {
            double btc = binanceService.fetchPrice("BTCUSDT");
            btcPrice.setText(String.format("$%.2f", btc));
        } catch (Exception ex) {
            btcPrice.setText("Error");
        }

        try {
            double eth = binanceService.fetchPrice("ETHUSDT");
            ethPrice.setText(String.format("$%.2f", eth));
        } catch (Exception ex) {
            ethPrice.setText("Error");
        }
    }
}
