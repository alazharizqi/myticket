package com.example.myticket.edit;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleGerbong;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditGerbong implements Initializable {

    PreparedStatement preparedStatement = null;
    Connection connection = null;

    @FXML
    private TextField gerbong = new TextField();

    public void updateQuery() {
        connection = SqliteConnection.Connector();
        String query = "update tb_gerbong set gerbong = ? where id_gerbong = ?";
        var id_gerbong = HandleGerbong.getUpdateGerbong();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gerbong.getText());
            preparedStatement.setString(2, id_gerbong.id_gerbong);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submitData(ActionEvent event) {
        Main main = new Main();
        var id_gerbong = HandleGerbong.getUpdateGerbong();
        HandleGerbong validation = new HandleGerbong();
        try {
            if (gerbong.getText().isEmpty()) {
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                if (validation.checkValidation(gerbong.getText(), id_gerbong.id_gerbong)) {
                    updateQuery();
                    main.changeScene("admin/AdminGerbong.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Gerbong Sudah Terdaftar !");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    @FXML
    public void stasiun (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminStasiun.fxml");
    }

    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void gerbong (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminGerbong.fxml");
    }

    @FXML
    public void kursi (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminKursi.fxml");
    }

    @FXML
    public void user (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminUser.fxml");
    }

    @FXML
    public void keluar (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("Login.fxml");
    }

    @FXML
    public void batal (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminGerbong.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var data = HandleGerbong.getUpdateGerbong();
        gerbong.setText(data.gerbong);
    }
}
