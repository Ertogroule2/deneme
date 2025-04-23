package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class InspectController implements Initializable {
    @FXML private Label symbolLabel;
    @FXML private LineChart<?, ?> priceChart;
    @FXML private TextField quantityField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: setup chart and load price data
    }

    @FXML private void handleBuy() {
        // TODO: buy logic
    }

    @FXML private void handleSell() {
        // TODO: sell logic
    }
}