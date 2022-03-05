package com.example.myticket.handle;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.StasiunModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandleStasiun {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    public void setUpdatestasiun (StasiunModel namastasiun) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("idstasiun", namastasiun.idstasiun);
        preferences.put("namastasiun", namastasiun.namastasiun);
    }

    public static StasiunModel getUpdateStasiun() {
        Preferences preferences = Preferences.userRoot();
        var idstasiun = preferences.get("idstasiun", "String");
        var namastasiun = preferences.get("namastasiun", "String");
        return new StasiunModel(idstasiun, namastasiun);
    }

    public boolean checkValidation(String namastasiun, String idstasiun) {
        connection = SqliteConnection.Connector();
        String validate = "select * from tb_stasiun where namastasiun = ? and idstasiun = ?";
        String search = "select * from tb_stasiun where namastasiun = ?";
        try {
            preparedStatement = connection.prepareStatement(validate);
            preparedStatement.setString(1, namastasiun);
            preparedStatement.setString(2, idstasiun);
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
                searching.setString(1, namastasiun);
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
