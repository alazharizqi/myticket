package com.example.myticket;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleKereta;
import com.example.myticket.model.TiketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class LaporanPenjualan implements Initializable {

    TiketModel tiketModel = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<TiketModel> tiketTable;

    @FXML
    private TableColumn<TiketModel, String> nama_penumpang;

    @FXML
    private TableColumn<TiketModel, String> nomor_telepon;

    @FXML
    private TableColumn<TiketModel, String> bio;

    @FXML
    private TableColumn<TiketModel, String> tipe_identitas;

    @FXML
    private TableColumn<TiketModel, String> nomor_identitas;

    @FXML
    private TableColumn<TiketModel, String> tgl_lahir;

    @FXML
    private TableColumn<TiketModel, String> alamat;

    @FXML
    private TableColumn<TiketModel, String> kode_kereta;

    @FXML
    private TableColumn<TiketModel, String> kereta;

    @FXML
    private TableColumn<TiketModel, String> kelas;

    @FXML
    private TableColumn<TiketModel, String> stasiun_asal;

    @FXML
    private TableColumn<TiketModel, String> stasiun_tujuan;

    @FXML
    private TableColumn<TiketModel, String> berangkat;

    @FXML
    private TableColumn<TiketModel, String> tiba;

    @FXML
    private TableColumn<TiketModel, String> tgl_pergi;

    @FXML
    private TableColumn<TiketModel, String> gerbong;

    @FXML
    private TableColumn<TiketModel, String> kursi;

    @FXML
    private TableColumn<TiketModel, String> harga;

    @FXML
    private TableColumn<TiketModel, String> status;


    ObservableList<TiketModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;




    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

//        PENUMPANG
        nama_penumpang.setCellValueFactory(new PropertyValueFactory<>("nama_lengkap"));
        nomor_telepon.setCellValueFactory(new PropertyValueFactory<>("nomor_telepon"));
        bio.setCellValueFactory(new PropertyValueFactory<>("bio"));
        tipe_identitas.setCellValueFactory(new PropertyValueFactory<>("tipe_identitas"));
        nomor_identitas.setCellValueFactory(new PropertyValueFactory<>("nomor_identitas"));
        tgl_lahir.setCellValueFactory(new PropertyValueFactory<>("tgl_lahir"));
        alamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

//        KERETA
        kode_kereta.setCellValueFactory(new PropertyValueFactory<>("kode_kereta"));
        kereta.setCellValueFactory(new PropertyValueFactory<>("kereta"));
        kelas.setCellValueFactory(new PropertyValueFactory<>("kelas"));
        stasiun_asal.setCellValueFactory(new PropertyValueFactory<>("stasiun_asal"));
        stasiun_tujuan.setCellValueFactory(new PropertyValueFactory<>("stasiun_tujuan"));
        tgl_pergi.setCellValueFactory(new PropertyValueFactory<>("tgl_pergi"));
        berangkat.setCellValueFactory(new PropertyValueFactory<>("berangkat"));
        tiba.setCellValueFactory(new PropertyValueFactory<>("tiba"));
        gerbong.setCellValueFactory(new PropertyValueFactory<>("gerbong"));
        kursi.setCellValueFactory(new PropertyValueFactory<>("kursi"));
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        FilteredList<TiketModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_data.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getKereta().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getKelas().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getKode_kereta().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getNama_lengkap().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getNomor_telepon().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getBio().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getTipe_identitas().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getNomor_identitas().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getTgl_lahir().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getAlamat().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getStasiun_asal().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getStasiun_tujuan().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getTgl_pergi().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getBerangkat().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getTiba().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }  else if (productUser.getKursi().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getGerbong().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getHarga().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else {
                    return false;
                }
            }));
        });

        SortedList<TiketModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tiketTable.comparatorProperty());

        tiketTable.setItems(dataList);
        tiketTable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_tiket";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new TiketModel(
                        resultSet.getString("nama_lengkap"),
                        resultSet.getString("nomor_telepon"),
                        resultSet.getString("bio"),
                        resultSet.getString("tipe_identitas"),
                        resultSet.getString("nomor_identitas"),
                        resultSet.getString("tgl_lahir"),
                        resultSet.getString("alamat"),
                        resultSet.getString("kode_kereta"),
                        resultSet.getString("kereta"),
                        resultSet.getString("kelas"),
                        resultSet.getString("stasiun_asal"),
                        resultSet.getString("stasiun_tujuan"),
                        resultSet.getString("tgl_pergi"),
                        resultSet.getString("berangkat"),
                        resultSet.getString("tiba"),
                        resultSet.getString("gerbong"),
                        resultSet.getString("kursi"),
                        resultSet.getString("harga"),
                        resultSet.getString("status")
                        ));
                tiketTable.setItems(dataList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main pesaN = new Main();
        pesaN.changeScene("Tiket.fxml");
    }

    @FXML
    public void logout (ActionEvent event) throws IOException {
        Main pesaN = new Main();
        pesaN.changeScene("Login.fxml");
    }

    @FXML
    public void cetak (ActionEvent event) throws  IOException {
        JasperPrint jasperPrint = null;
        Map param = new HashMap();

        try {
            jasperPrint = JasperFillManager.fillReport("report/LaporanPenjualan.jasper", param, SqliteConnection.Connector());
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Laporan Penjualan");
            viewer.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    this.Table();
    }
}
