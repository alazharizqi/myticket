package com.example.myticket;

import com.example.myticket.connection.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class JualTiket implements Initializable {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

//    PENUMPANG

    @FXML
    public TextField nama_lengkap = new TextField();

    @FXML
    public TextField nomor_telepon = new TextField();

    @FXML
    private ComboBox bio = new ComboBox();

    @FXML
    private ComboBox tipe_identitas = new ComboBox();

    @FXML
    public TextField nomor_identitas = new TextField();

    @FXML
    public DatePicker tgl_lahir = new DatePicker();

    @FXML
    public TextArea alamat = new TextArea();

//    KERETA

    @FXML
    private ComboBox id_kereta = new ComboBox();

    @FXML
    public TextField kereta = new TextField();

    @FXML
    public TextField kelas = new TextField();

    @FXML
    public TextField stasiun_asal = new TextField();

    @FXML
    public TextField stasiun_tujuan = new TextField();

    @FXML
    public TextField berangkat = new TextField();

    @FXML
    public TextField tiba = new TextField();

    @FXML
    public TextField tgl_pergi = new TextField();

    @FXML
    public TextField gerbong = new TextField();

    @FXML
    public TextField kursi = new TextField();

    @FXML
    public TextField harga = new TextField();

    @FXML
    public TextField status = new TextField();

    ObservableList<String> Tipe_IdentitasList = FXCollections.observableArrayList("KTP", "SIM", "Paspor");
    ObservableList<String> BioList = FXCollections.observableArrayList("Tuan", "Nyonya");
    ObservableList<String> kode_keretaItems = FXCollections.observableArrayList();

    public void setKode_Kereta(ActionEvent event) {
        String query = "select * from tb_kereta where id_kereta = ?";
        connection = SqliteConnection.Connector();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id_kereta.getValue().toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                kereta.setText(resultSet.getString("kereta"));
                kelas.setText(resultSet.getString("kelas"));
                stasiun_asal.setText(resultSet.getString("stasiun_asal"));
                stasiun_tujuan.setText(resultSet.getString("stasiun_tujuan"));
                berangkat.setText(resultSet.getString("berangkat"));
                tiba.setText(resultSet.getString("tiba"));
                tgl_pergi.setText(resultSet.getString("tgl_pergi"));
                gerbong.setText(resultSet.getString("gerbong"));
                kursi.setText(resultSet.getString("kursi"));
                harga.setText(resultSet.getString("harga"));
                status.setText(resultSet.getString("status"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setKode_KeretaItems() {
        String query = "select * from tb_kereta where status = ?";
        connection = SqliteConnection.Connector();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Tersedia");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                kode_keretaItems.add(resultSet.getString("id_kereta"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        id_kereta.setItems(kode_keretaItems);
    }


    public void submitData (ActionEvent event) throws IOException {

        JasperPrint jasperPrint = null;
        HashMap param = new HashMap();
        String p_id = id_kereta.getValue().toString();
        param.put("param_kodekereta",p_id);

        Main main = new Main();
        connection = SqliteConnection.Connector();
        String query = "insert into tb_tiket (nama_lengkap, nomor_telepon, bio, tipe_identitas, nomor_identitas, tgl_lahir, alamat, kode_kereta, kereta, kelas, stasiun_asal, stasiun_tujuan, berangkat, tiba, tgl_pergi, gerbong, kursi, harga, status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String update_status = "update tb_kereta set status = ? where id_kereta = ?";
        String validate = "select * from tb_kereta where kereta = ? and tgl_pergi = ? and gerbong = ? and kursi = ? and status = ?";
        try {
            if (nama_lengkap.getText().isEmpty() || nomor_telepon.getText().isEmpty() || bio.getValue() == null || tipe_identitas.getValue() == null || nomor_identitas.getText().isEmpty() || tgl_lahir.getValue() == null || alamat.getText().isEmpty() || id_kereta.getValue() == null || kereta.getText().isEmpty() || kelas.getText().isEmpty() || stasiun_asal.getText().isEmpty() || stasiun_tujuan.getText().isEmpty() || tgl_pergi.getText().isEmpty() || berangkat.getText().isEmpty() || tiba.getText().isEmpty() || gerbong.getText().isEmpty() || kursi.getText().isEmpty() || status.getText().isEmpty() || harga.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Form Tidak Boleh Kosong !");
                alert.showAndWait();
            } else {
                var validation = connection.prepareStatement(validate);
                validation.setString(1, kereta.getText());
                validation.setString(2, tgl_pergi.getText());
                validation.setString(3, gerbong.getText());
                validation.setString(4, kursi.getText());
                validation.setString(5, status.getText());
                resultSet = validation.executeQuery();
                validation.close();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Data Tiket Sudah Terdaftar !");
                    alert.showAndWait();
                } else {
                    var statusUpdate = connection.prepareStatement(update_status);
                    statusUpdate.setString(1, "Habis");
                    statusUpdate.setString(2, id_kereta.getValue().toString());
                    statusUpdate.executeUpdate();
                    statusUpdate.close();
                    main.changeScene("Tiket.fxml");
                }
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nama_lengkap.getText());
                preparedStatement.setString(2, nomor_telepon.getText());
                preparedStatement.setString(3, bio.getValue().toString());
                preparedStatement.setString(4, tipe_identitas.getValue().toString());
                preparedStatement.setString(5, nomor_identitas.getText());
                preparedStatement.setString(6, String.valueOf(tgl_lahir.getValue()));
                preparedStatement.setString(7, alamat.getText());
                preparedStatement.setString(8, id_kereta.getValue().toString());
                preparedStatement.setString(9, kereta.getText());
                preparedStatement.setString(10, kelas.getText());
                preparedStatement.setString(11, stasiun_asal.getText());
                preparedStatement.setString(12, stasiun_tujuan.getText());
                preparedStatement.setString(13, berangkat.getText());
                preparedStatement.setString(14, tiba.getText());
                preparedStatement.setString(15, tgl_pergi.getText());
                preparedStatement.setString(16, gerbong.getText());
                preparedStatement.setString(17, kursi.getText());
                preparedStatement.setString(18, harga.getText());
                preparedStatement.setString(19, status.getText());
                preparedStatement.execute();
                preparedStatement.close();
                connection.close();
                resultSet.close();

                try {
                    jasperPrint = JasperFillManager.fillReport("report/Struk.jasper", param, SqliteConnection.Connector());
                    JasperViewer viewer = new JasperViewer(jasperPrint, false);
                    viewer.setTitle("Struk Penjualan");
                    viewer.setVisible(true);
                } catch (JRException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    






    @FXML
    private void kumpulandata() {
        tipe_identitas.setItems(Tipe_IdentitasList);
        bio.setItems(BioList);

    }

    @FXML
    public void logout (ActionEvent event) throws IOException {
        Main logouT = new Main();
        logouT.changeScene("Login.fxml");
    }

    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main beranda = new Main();
        beranda.changeScene("Tiket.fxml");
    }

    @FXML
    public void laporanpenjualan (ActionEvent event) throws IOException {
        Main beranda = new Main();
        beranda.changeScene("LaporanPenjualan.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setKode_KeretaItems();
        this.kumpulandata();
    }
}
