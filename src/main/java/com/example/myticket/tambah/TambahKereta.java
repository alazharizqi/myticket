package com.example.myticket.tambah;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;

import java.io.IOException;
import java.util.ResourceBundle;

public class TambahKereta implements Initializable {


    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public TextField kereta = new TextField();

    @FXML
    public ComboBox<String> kelas = new ComboBox<>();

    @FXML
    public ComboBox<String> stasiun_asal = new ComboBox<>();

    @FXML
    public ComboBox<String> stasiun_tujuan = new ComboBox<>();

    @FXML
    public DatePicker tgl_pergi = new DatePicker();

    @FXML
    public TextField berangkat = new TextField();

    @FXML
    public TextField tiba = new TextField();

    @FXML
    public ComboBox<String> gerbong = new ComboBox<>();

    @FXML
    public ComboBox<String> kursi = new ComboBox<>();

    @FXML
    public ComboBox<String> status = new ComboBox<>();

    @FXML
    public TextField harga = new TextField();

    ObservableList<String> kelasList = FXCollections.observableArrayList("Ekonomi", "Bisnis", "Eksekutif");

    ObservableList<String> statusList = FXCollections.observableArrayList("Tersedia", "Habis");

    ObservableList<String> gerbongItems = FXCollections.observableArrayList();

    ObservableList<String> kursiItems = FXCollections.observableArrayList();

    ObservableList<String> stasiun_asalItems = FXCollections.observableArrayList();

    ObservableList<String> stasiun_tujuanItems = FXCollections.observableArrayList();

    @FXML
    private void item() {

        kelas.setItems(kelasList);
        status.setItems(statusList);
    }


    public void stasiun_asalItems() {
        this.connection = SqliteConnection.Connector();
        String query = "select * from tb_stasiun";
        try {
            this.preparedStatement = this.connection.prepareStatement(query);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()) {
                this.stasiun_asalItems.add(this.resultSet.getString("namastasiun"));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        stasiun_asal.setItems(stasiun_asalItems);
    }

    public void stasiun_tujuanItems() {
        this.connection = SqliteConnection.Connector();
        String query = "select * from tb_stasiun";

        try {
            this.preparedStatement = this.connection.prepareStatement(query);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()) {
                this.stasiun_tujuanItems.add(this.resultSet.getString("namastasiun"));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        stasiun_tujuan.setItems(stasiun_tujuanItems);
    }

    public void gerbongItems() {
        this.connection = SqliteConnection.Connector();
        String query = "select * from tb_gerbong";

        try {
            this.preparedStatement = this.connection.prepareStatement(query);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()) {
                this.gerbongItems.add(this.resultSet.getString("gerbong"));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        gerbong.setItems(gerbongItems);
    }

    public void kursiItems() {
        this.connection = SqliteConnection.Connector();
        String query = "select * from tb_kursi";

        try {
            this.preparedStatement = this.connection.prepareStatement(query);
            this.resultSet = this.preparedStatement.executeQuery();

            while(this.resultSet.next()) {
                this.kursiItems.add(this.resultSet.getString("kursi"));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        kursi.setItems(kursiItems);
    }




    public void submitData (ActionEvent event) throws IOException {
        Main main = new Main();
        connection = SqliteConnection.Connector();
        String query = "insert into tb_kereta (kereta, kelas, stasiun_asal, stasiun_tujuan, tgl_pergi, berangkat, tiba, gerbong, kursi, status, harga) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String validate = "select * from tb_kereta where kereta = ? and tgl_pergi = ? and gerbong = ? and kursi = ? and status = ?";
        try {
            if(kereta.getText().isEmpty() || kelas.getValue() == null || stasiun_asal.getValue() == null || stasiun_tujuan.getValue() == null || tgl_pergi.getValue() == null || berangkat.getText().isEmpty() || tiba.getText().isEmpty() || gerbong.getValue() == null || kursi.getValue() == null || status.getValue() == null || harga.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                var validation = connection.prepareStatement(validate);
                validation.setString(1, kereta.getText());
                validation.setString(2, String.valueOf(tgl_pergi.getValue()));
                validation.setString(3, gerbong.getValue());
                validation.setString(4, kursi.getValue());
                validation.setString(5, status.getValue());
                resultSet = validation.executeQuery();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Data Kereta Sudah Terdaftar !");
                    alert.showAndWait();
                } else {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, kereta.getText());
                    preparedStatement.setString(2, kelas.getValue());
                    preparedStatement.setString(3, stasiun_asal.getValue());
                    preparedStatement.setString(4, stasiun_tujuan.getValue());
                    preparedStatement.setString(5, String.valueOf(tgl_pergi.getValue()));
                    preparedStatement.setString(6, berangkat.getText());
                    preparedStatement.setString(7, tiba.getText());
                    preparedStatement.setString(8, gerbong.getValue());
                    preparedStatement.setString(9, kursi.getValue());
                    preparedStatement.setString(10, status.getValue());
                    preparedStatement.setString(11, harga.getText());
                    preparedStatement.execute();
                    preparedStatement.close();
                    connection.close();
                    main.changeScene("admin/AdminKereta.fxml");
                }
                resultSet.close();
                validation.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @FXML
    public void openadmintiket (ActionEvent event) throws IOException {
        Main admintiket = new Main();
        admintiket.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void openadminuser (ActionEvent event) throws IOException {
        Main adminuser = new Main();
        adminuser.changeScene("admin/AdminUser.fxml");
    }

    @FXML
    public void logout (ActionEvent event) throws IOException {
        Main logout = new Main();
        logout.changeScene("Login.fxml");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.item();
        this.stasiun_asalItems();
        this.stasiun_tujuanItems();
        this.gerbongItems();
        this.kursiItems();
    }
}