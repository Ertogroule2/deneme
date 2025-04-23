package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestController {
    @FXML private Label testLabel;

    public void initialize() {
        System.out.println("Injected: " + testLabel);
    }
}
