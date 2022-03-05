package com.example.myticket.edit;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleStasiun;
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

public class EditStasiun implements Initializable {


    PreparedStatement preparedStatement = null;
    Connection connection = null;

    @FXML
    private TextField namastasiun = new TextField();

    public void updateQuery() {
        connection = SqliteConnection.Connector();
        String query = "update tb_stasiun set namastasiun = ? where idstasiun = ?";
        var idstasiun = HandleStasiun.getUpdateStasiun();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, namastasiun.getText());
            preparedStatement.setString(2, idstasiun.idstasiun);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submitData(ActionEvent event) {
        Main main = new Main();
        var idstasiun = HandleStasiun.getUpdateStasiun();
        HandleStasiun validation = new HandleStasiun();
        try {
            if (namastasiun.getText().isEmpty()) {
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                if (validation.checkValidation(namastasiun.getText(), idstasiun.idstasiun)) {
                    updateQuery();
                    main.changeScene("admin/AdminStasiun.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Stasiun Sudah Terdaftar !");
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
    public void batal (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminStasiun.fxml");
    }

    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void user (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("admin/AdminUser.fxml");
    }

    @FXML
    public void logout (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("Login.fxml");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var data = HandleStasiun.getUpdateStasiun();
        namastasiun.setText(data.namastasiun);
    }
}
