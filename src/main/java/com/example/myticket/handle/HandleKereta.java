package com.example.myticket.handle;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.KeretaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;

public class HandleKereta {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;

    public void setUpdatekereta (KeretaModel kereta) {
        Preferences preferences = Preferences.userRoot();
        preferences.put("id_kereta", kereta.id_kereta);
        preferences.put("kereta", kereta.kereta);
        preferences.put("kelas", kereta.kelas);
        preferences.put("stasiun_asal", kereta.stasiun_asal);
        preferences.put("stasiun_tujuan", kereta.stasiun_tujuan);
        preferences.put("tgl_pergi", kereta.tgl_pergi);
        preferences.put("berangkat", kereta.berangkat);
        preferences.put("tiba", kereta.tiba);
        preferences.put("gerbong", kereta.gerbong);
        preferences.put("kursi", kereta.kursi);
        preferences.put("status", kereta.status);
        preferences.put("harga", kereta.harga);
    }

    public static KeretaModel getUpdateKereta() {
        Preferences preferences = Preferences.userRoot();
        var id_kereta = preferences.get("id_kereta", "String");
        var kereta = preferences.get("kereta", "String");
        var kelas = preferences.get("kelas", "String");
        var stasiun_asal = preferences.get("stasiun_asal", "String");
        var stasiun_tujuan = preferences.get("stasiun_tujuan", "String");
        var tgl_pergi = preferences.get("tgl_pergi", "Date");
        var berangkat = preferences.get("berangkat", "String");
        var tiba = preferences.get("tiba", "String");
        var gerbong = preferences.get("gerbong", "String");
        var kursi = preferences.get("kursi", "String");
        var status = preferences.get("status", "String");
        var harga = preferences.get("harga", "String");
        return new KeretaModel(id_kereta, kereta, kelas, stasiun_asal, stasiun_tujuan, tgl_pergi, berangkat, tiba, gerbong, kursi, status, harga);
    }

    public boolean checkValidation(String id_kereta, String kereta) {
        connection = SqliteConnection.Connector();
        String validate = "select * from tb_kereta where id_kereta = ? and kereta = ?";
        String search = "select * from tb_kereta where id_kereta = ?";
        try {
            preparedStatement = connection.prepareStatement(validate);
            preparedStatement.setString(1, id_kereta);
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
                searching.setString(1, id_kereta);
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
