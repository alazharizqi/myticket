package com.example.myticket.edit;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleKursi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditKursi implements Initializable {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    @FXML
    public TextField kursi = new TextField();

    public void updateQuery() {
        connection = SqliteConnection.Connector();
        String query = "update tb_kursi set kursi = ? where id_kursi = ?";
        var id_kursi = HandleKursi.getUpdateKursi();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, kursi.getText());
            preparedStatement.setString(2, id_kursi.id_kursi);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submitData(ActionEvent event) {
        Main main = new Main();
        var id_kursi = HandleKursi.getUpdateKursi();
        HandleKursi validation = new HandleKursi();
        try {
            if (kursi.getText().isEmpty()) {
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                if (validation.checkValidation(kursi.getText(), id_kursi.id_kursi)) {
                    updateQuery();
                    main.changeScene("admin/AdminKursi.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Kursi Sudah Terdaftar !");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void stasiun (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminStasiun.fxml");
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
        logout.changeScene("admin/AdminKursi.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var data = HandleKursi.getUpdateKursi();
        kursi.setText(data.kursi);
    }
}
