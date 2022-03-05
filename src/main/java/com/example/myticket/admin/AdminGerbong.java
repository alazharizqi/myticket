package com.example.myticket.admin;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleGerbong;
import com.example.myticket.model.GerbongModel;
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

public class AdminGerbong implements Initializable {

    GerbongModel gerbongModel = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<GerbongModel> gerbongTable;

    @FXML
    private TableColumn<GerbongModel, String> id_gerbong;

    @FXML
    private TableColumn<GerbongModel, String> gerbong;

    @FXML
    private TableColumn<GerbongModel, String> actionColumn;

    ObservableList<GerbongModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;




    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

        id_gerbong.setCellValueFactory(new PropertyValueFactory<>("id_gerbong"));
        gerbong.setCellValueFactory(new PropertyValueFactory<>("gerbong"));

        FilteredList<GerbongModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_data.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getGerbong().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }

                else {
                    return false;
                }
            }));
        });

        SortedList<GerbongModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(gerbongTable.comparatorProperty());

        Callback<TableColumn<GerbongModel, String>, TableCell<GerbongModel, String>> cellFactory = (TableColumn<GerbongModel, String> param) -> {
            final TableCell<GerbongModel, String> cell = new TableCell<GerbongModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        HBox updateIcon = new HBox((new Label("Ubah")));
                        HBox deleteIcon = new HBox(new Label("Hapus"));

                        deleteIcon.setStyle(
                                "-fx-cursor: hand;"

                        );
                        updateIcon.setStyle(
                                "-fx-cursor: hand;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                gerbongModel = gerbongTable.getSelectionModel().getSelectedItem();
                                var query = "delete from tb_gerbong where id_gerbong = ?";
                                var connection = SqliteConnection.Connector();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, gerbongModel.getId_gerbong());
                                preparedStatement.execute();
                                preparedStatement.close();
                                resultSet.close();
                                refreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            gerbongModel = gerbongTable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandleGerbong handle = new HandleGerbong();
                                GerbongModel data = new GerbongModel(gerbongModel.getId_gerbong(), gerbongModel.getGerbong());
                                handle.setUpdategerbong(data);
                                main.changeScene("edit/EditGerbong.fxml");
                            } catch (IOException ex) {
                                System.out.println(ex.getCause());
                            }
                        });

                        HBox hbox = new HBox(updateIcon, deleteIcon);
                        hbox.setStyle("-fx-alignment: center");
                        HBox.setMargin(deleteIcon,new Insets(2, 3, 0, 5));
                        HBox.setMargin(updateIcon,new Insets(2, 3, 0, 5));
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColumn.setCellFactory(cellFactory);
        gerbongTable.setItems(dataList);
        gerbongTable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_gerbong";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new GerbongModel(
                        resultSet.getString("id_gerbong"),
                        resultSet.getString("gerbong")
                ));
                gerbongTable.setItems(dataList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }





    @FXML
    public void stasiun (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminStasiun.fxml");
    }

    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void kursi (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminKursi.fxml");
    }

    @FXML
    public void user (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminUser.fxml");
    }

    @FXML
    public void keluar (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("Login.fxml");
    }

    @FXML
    public void tambah (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("tambah/TambahGerbong.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table();
    }
}
