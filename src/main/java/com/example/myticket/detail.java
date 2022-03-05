package com.example.myticket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class detail {

    @FXML
    public void openpesan (ActionEvent event) throws IOException {
        Main pesan = new Main();
        pesan.changeScene("Tiket.fxml");
    }

    @FXML
    public void opendetail (ActionEvent event) throws IOException {
        Main detail = new Main();
        detail.changeScene("tiket.fxml");
    }

    @FXML
    public void openlgout (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("logout.fxml");
    }

}
