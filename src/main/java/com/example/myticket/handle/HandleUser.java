package com.example.myticket.handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.UserModel;
import java.util.prefs.Preferences;

public class HandleUser {
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    public void setUpdateuser (UserModel user) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id_user", user.id_user);
        preferences.put("user", user.user);
        preferences.put("email", user.email);
        preferences.put("pass", user.pass);
        preferences.put("role", user.role);
    }

    public static UserModel getUpdateUser() {
        Preferences preferences = Preferences.userRoot();
        var id_user = preferences.get("id_user", "String");
        var user = preferences.get("user", "String");
        var email = preferences.get("email", "String");
        var pass = preferences.get("pass", "String");
        var role = preferences.get("role", "String");
        return new UserModel(id_user, user, email, pass, role);
    }

    public boolean checkValidation(String user, String id_user) {
        connection = SqliteConnection.Connector();
        String validate = "select * from tb_user where user = ? and id_user = ?";
        String search = "select * from tb_user where user = ?";
        try {
            preparedStatement = connection.prepareStatement(validate);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, id_user);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                return true;
            } else {
                resultSet.close();
                preparedStatement.close();
                var searching = connection.prepareStatement(search);
                ResultSet searched = null;
                searching.setString(1, user);
                searched = searching.executeQuery();
                if (searched.next()) {
                    searching.close();
                    searched.close();
                    return false;
                } else {
                    searching.close();
                    searched.close();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
