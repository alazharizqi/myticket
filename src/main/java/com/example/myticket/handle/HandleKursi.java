package com.example.myticket.handle;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.KursiModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandleKursi {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    public void setUpdatekursi (KursiModel kursi) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id_kursi", kursi.id_kursi);
        preferences.put("kursi", kursi.kursi);
    }

    public static KursiModel getUpdateKursi() {
        Preferences preferences = Preferences.userRoot();
        var id_kursi = preferences.get("id_kursi", "String");
        var kursi = preferences.get("kursi", "String");
        return new KursiModel(id_kursi, kursi);
    }

    public boolean checkValidation(String kursi, String id_kursi) {
        connection = SqliteConnection.Connector();
        String validate = "select * from tb_kursi where kursi = ? and id_kursi = ?";
        String search = "select * from tb_kursi where kursi = ?";
        try {
            preparedStatement = connection.prepareStatement(validate);
            preparedStatement.setString(1, kursi);
            preparedStatement.setString(2, id_kursi);
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
                searching.setString(1, kursi);
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
