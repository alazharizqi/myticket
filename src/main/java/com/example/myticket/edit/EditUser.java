package com.example.myticket.edit;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditUser implements Initializable {

    PreparedStatement preparedStatement = null;
    Connection connection = null;

    @FXML
    private TextField user = new TextField();

    @FXML
    private ComboBox<String> role = new ComboBox<>();

    @FXML
    private TextField email = new TextField();

    @FXML
    private TextField pass = new TextField();

    ObservableList<String> roleList = FXCollections.observableArrayList("Kasir", "Admin");

    public void updateQuery() {
        connection = SqliteConnection.Connector();
        String query = "update tb_user set user = ?, email = ?, pass = ?, role = ? where id_user = ?";
        var id_user = HandleUser.getUpdateUser();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getText());
            preparedStatement.setString(2, email.getText());
            preparedStatement.setString(3, pass.getText());
            preparedStatement.setString(4, role.getValue());
            preparedStatement.setString(5, id_user.id_user);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submitData(ActionEvent event) {
        Main main = new Main();
        var id_user = HandleUser.getUpdateUser();
        HandleUser validation = new HandleUser();
        try {
            if (user.getText().isEmpty() || email.getText().isEmpty() || pass.getText().isEmpty() || role.getValue() == null) {
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                if (validation.checkValidation(user.getText(), id_user.id_user)) {
                    updateQuery();
                    main.changeScene("admin/AdminUser.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Username Sudah Terdaftar !");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addRole() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addRole();
        var data = HandleUser.getUpdateUser();
        user.setText(data.user);
        email.setText(data.email);
        pass.setText(data.pass);
        role.setValue(data.role);
    }
}
