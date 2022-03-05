package com.example.myticket.tambah;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

import java.io.IOException;
import java.util.Locale;

public class TambahUser {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    public TextField user = new TextField();

    @FXML
    public ComboBox<String> role = new ComboBox<>();

    @FXML
    public TextField email = new TextField();

    @FXML
    public PasswordField pass = new PasswordField();

    ObservableList<String> roleList = FXCollections.observableArrayList("Kasir", "Admin");

    public void addItems() {role.setItems(roleList);}

    public void submitData (ActionEvent event) throws IOException {
        Main main = new Main();
        connection = SqliteConnection.Connector();

        String query = "insert into tb_user (user, email, pass, role) values (?, ?, ?, ?)";
        String validate = "select * from tb_user where user = ?";
        try {
            if(user.getText().isEmpty() || email.getText().isEmpty() || role.getValue() == null || pass.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                var validation = connection.prepareStatement(validate);
                validation.setString(1, user.getText());
                resultSet = validation.executeQuery();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Username yang anda masukkan telah terdaftar !");
                    alert.showAndWait();
                } else {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, user.getText());
                    preparedStatement.setString(2, email.getText());
                    preparedStatement.setString(3, pass.getText());
                    preparedStatement.setString(4, role.getValue());
                    preparedStatement.execute();
                    preparedStatement.close();
                    connection.close();
                    main.changeScene("admin/AdminUser.fxml");
                }
                resultSet.close();
                validation.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void initialize() {


        role.setItems(roleList);

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

}
