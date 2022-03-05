package com.example.myticket.admin;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleKursi;
import com.example.myticket.model.KursiModel;
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

public class AdminKursi implements Initializable {

    KursiModel kursiModel = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<KursiModel> kursiTable;

    @FXML
    private TableColumn<KursiModel, String> id_kursi;

    @FXML
    private TableColumn<KursiModel, String> kursi;

    @FXML
    private TableColumn<KursiModel, String> actionColumn;

    ObservableList<KursiModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;




    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

        id_kursi.setCellValueFactory(new PropertyValueFactory<>("id_kursi"));
        kursi.setCellValueFactory(new PropertyValueFactory<>("kursi"));

        FilteredList<KursiModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_data.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getKursi().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }
                else {
                    return false;
                }
            }));
        });

        SortedList<KursiModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(kursiTable.comparatorProperty());

        Callback<TableColumn<KursiModel, String>, TableCell<KursiModel, String>> cellFactory = (TableColumn<KursiModel, String> param) -> {
            final TableCell<KursiModel, String> cell = new TableCell<KursiModel, String>() {
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
                                kursiModel = kursiTable.getSelectionModel().getSelectedItem();
                                var query = "delete from tb_kursi where id_kursi = ?";
                                var connection = SqliteConnection.Connector();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, kursiModel.getId_kursi());
                                preparedStatement.execute();
                                preparedStatement.close();
                                resultSet.close();
                                refreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            kursiModel = kursiTable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandleKursi handle = new HandleKursi();
                                KursiModel data = new KursiModel(kursiModel.getId_kursi(), kursiModel.getKursi());
                                handle.setUpdatekursi(data);
                                main.changeScene("edit/EditKursi.fxml");
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
        kursiTable.setItems(dataList);
        kursiTable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_kursi";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new KursiModel(
                        resultSet.getString("id_kursi"),
                        resultSet.getString("kursi")
                ));
                kursiTable.setItems(dataList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }





    @FXML
    public void tiket (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminKereta.fxml");
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
        admincreate.changeScene("tambah/TambahKursi.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Table();
    }

}
