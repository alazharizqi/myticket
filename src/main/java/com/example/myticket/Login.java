package com.example.myticket;

import com.example.myticket.model.LoginModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public JFXButton submit;

    @FXML
    public Label status = new Label();

    @FXML
    public void openLink (ActionEvent event) throws IOException {
        Main logiN = new Main();
        logiN.changeScene("Tiket.fxml");
    }

    @FXML
    public void openadmintiket (ActionEvent event) throws IOException {
        Main admintiket = new Main();
        admintiket.changeScene("AdminKereta.fxml");
    }

    LoginModel loginmodel = new LoginModel();

    public void Userlogin(ActionEvent event) throws IOException {
        Main login = new Main();
        try {
          if(loginmodel.isLogin(username.getText(), password.getText())) {
              if(loginmodel.role(username.getText(), "Kasir")) {
                  login.changeScene("Tiket.fxml");
              } else {
                  login.changeScene("admin/AdminKereta.fxml");
              }
          } else {
              status.setText("Username atau Password salah !");
          }
        } catch (SQLException e) {
            status.setText("error");
            System.out.println(e.getCause());
        }
    }

}
