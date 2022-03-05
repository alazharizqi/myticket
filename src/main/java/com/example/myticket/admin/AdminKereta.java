package com.example.myticket.admin;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleKereta;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminKereta implements Initializable {
    KeretaModel keretaModel = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<KeretaModel> tiketTable;

    @FXML
    private TableColumn<KeretaModel, String> id_kereta;

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
    private TableColumn<KeretaModel, String> berangkat;

    @FXML
    private TableColumn<KeretaModel, String> tiba;

    @FXML
    private TableColumn<KeretaModel, String> gerbong;

    @FXML
    private TableColumn<KeretaModel, String> kursi;

    @FXML
    private TableColumn<KeretaModel, String> harga;

    @FXML
    private TableColumn<KeretaModel, String> status;

    @FXML
    private TableColumn<KeretaModel, String> actionColumn;




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
                } else if (productUser.getStatus().toLowerCase().indexOf(keyword) > -1) {
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

//                        HBox updateIcon = new HBox(new Label("Ubah"));
                        HBox deleteIcon = new HBox(new Label("Hapus"));

//                        updateIcon.setStyle(
//                                "-fx-cursor: hand;"
//
//                        );

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

//                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
//                            keretaModel = tiketTable.getSelectionModel().getSelectedItem();
//                            try {
//                                Main main = new Main();
//                                HandleKereta handle = new HandleKereta();
//                                KeretaModel data = new KeretaModel(keretaModel.getId_kereta(), keretaModel.getKereta(), keretaModel.getKelas(), keretaModel.getStasiun_asal(), keretaModel.getStasiun_tujuan(), keretaModel.getTgl_pergi(), keretaModel.getBerangkat(), keretaModel.getTiba(), keretaModel.getGerbong(), keretaModel.getKursi(), keretaModel.getStatus(), keretaModel.getHarga());
//                                handle.setUpdatekereta(data);
//                                main.changeScene("edit/EditKereta.fxml");
//                            } catch (IOException ex) {
//                                System.out.println(ex.getCause());
//                            }
//                        });

                        HBox hbox = new HBox(deleteIcon);
                        hbox.setStyle("-fx-alignment: center");
                        HBox.setMargin(deleteIcon,new Insets(2, 3, 0, 5));
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColumn.setCellFactory(cellFactory);
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
    public void openadminuser (ActionEvent event) throws IOException {
        Main adminuser = new Main();
        adminuser.changeScene("admin/AdminUser.fxml");
    }

    @FXML
    public void openlogin (ActionEvent event) throws IOException {
        Main login = new Main();
        login.changeScene("Login.fxml");
    }

    @FXML
    public void openadmincreate (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("tambah/TambahKereta.fxml");
    }

    @FXML
    public void stasiun (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminStasiun.fxml");
    }

    @FXML
    public void gerbong (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminGerbong.fxml");
    }

    @FXML
    public void kursi (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminKursi.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table();
    }
}
