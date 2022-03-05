package com.example.myticket;

import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.model.KeretaModel;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Tiket implements Initializable {

    KeretaModel keretaModel = null;

    @FXML
    private TextField cari_status;

    @FXML
    private TableView<KeretaModel> tiketTable;

    @FXML
    private TableColumn<KeretaModel, String> kereta;

    @FXML
    private TableColumn<KeretaModel, String> kelas;

    @FXML
    private TableColumn<KeretaModel, String> stasiun_asal;

    @FXML
    private TableColumn<KeretaModel, String> stasiun_tujuan;

    @FXML
    private TableColumn<KeretaModel, String> tgl_pergi;

    @FXML
    private TableColumn<KeretaModel, String> gerbong;

    @FXML
    private TableColumn<KeretaModel, String> kursi;

    @FXML
    private TableColumn<KeretaModel, String> harga;

    @FXML
    private TableColumn<KeretaModel, String> status;

    @FXML
    private TableColumn<KeretaModel, String> id_kereta;

    @FXML
    private TableColumn<KeretaModel, String> berangkat;

    @FXML
    private TableColumn<KeretaModel, String> tiba;


    ObservableList<KeretaModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

        id_kereta.setCellValueFactory(new PropertyValueFactory<>("id_kereta"));
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

        FilteredList<KeretaModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_status.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getStatus().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else {
                    return false;
                }
            }));
        });

        SortedList<KeretaModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tiketTable.comparatorProperty());

        Callback<TableColumn<KeretaModel, String>, TableCell<KeretaModel, String>> cellFactory = (TableColumn<KeretaModel, String> param) -> {
            final TableCell<KeretaModel, String> cell = new TableCell<KeretaModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        HBox updateIcon = new HBox(new Label("Ubah"));
                        HBox deleteIcon = new HBox(new Label("Hapus"));

                        updateIcon.setStyle(
                                "-fx-cursor: hand;"

                        );

                        deleteIcon.setStyle(
                                "-fx-cursor: hand;"

                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                keretaModel = tiketTable.getSelectionModel().getSelectedItem();
                                var query = "delete from tb_kereta where id_kereta = ?";
                                var connection = SqliteConnection.Connector();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, keretaModel.getId_kereta());
                                preparedStatement.execute();
                                preparedStatement.close();
                                resultSet.close();
                                refreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });

                        HBox hbox = new HBox(updateIcon, deleteIcon);
                        hbox.setStyle("-fx-alignment: center");
                        HBox.setMargin(deleteIcon,new Insets(2, 3, 0, 5));
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tiketTable.setItems(dataList);
        tiketTable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_kereta";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new KeretaModel(
                        resultSet.getString("id_kereta"),
                        resultSet.getString("kereta"),
                        resultSet.getString("kelas"),
                        resultSet.getString("stasiun_asal"),
                        resultSet.getString("stasiun_tujuan"),
                        resultSet.getString("tgl_pergi"),
                        resultSet.getString("berangkat"),
                        resultSet.getString("tiba"),
                        resultSet.getString("gerbong"),
                        resultSet.getString("kursi"),
                        resultSet.getString("status"),
                        resultSet.getString("harga")
                ));
                tiketTable.setItems(dataList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }




    @FXML
    public void openLink (ActionEvent event) throws IOException {
        Main logouT = new Main();
        logouT.changeScene("Login.fxml");
    }

    @FXML
    public void openlInk (ActionEvent event) throws IOException {
        Main tiKet = new Main();
        tiKet.changeScene("tiket.fxml");
    }


    @FXML
    public void openpesan (ActionEvent event) throws IOException {
        Main pesaN = new Main();
        pesaN.changeScene("JualTiket.fxml");
    }

    @FXML
    public void laporanpenjualan (ActionEvent event) throws IOException {
        Main pesaN = new Main();
        pesaN.changeScene("LaporanPenjualan.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table();
    }
}