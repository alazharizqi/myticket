package com.example.myticket.handle;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.GerbongModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandleGerbong {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    public void setUpdategerbong (GerbongModel gerbong) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id_gerbong", gerbong.id_gerbong);
        preferences.put("gerbong", gerbong.gerbong);
    }

    public static GerbongModel getUpdateGerbong() {
        Preferences preferences = Preferences.userRoot();
        var id_gerbong = preferences.get("id_gerbong", "String");
        var gerbong = preferences.get("gerbong", "String");
        return new GerbongModel(id_gerbong, gerbong);
    }

    public boolean checkValidation(String gerbong, String id_gerbong) {
        connection = SqliteConnection.Connector();
        String validate = "select * from tb_gerbong where gerbong = ? and id_gerbong = ?";
        String search = "select * from tb_gerbong where gerbong = ?";
        try {
            preparedStatement = connection.prepareStatement(validate);
            preparedStatement.setString(1, gerbong);
            preparedStatement.setString(2, id_gerbong);
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
                searching.setString(1, gerbong);
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
