package com.example.myticket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HelloController {

    @FXML
    public void openLink  (ActionEvent event) throws IOException {
        Main landing = new Main();
        landing.changeScene ("Login.fxml");
    }
}