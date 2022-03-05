package com.example.myticket.admin;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleStasiun;
import com.example.myticket.model.StasiunModel;
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

public class AdminStasiun implements Initializable {
    StasiunModel stasiunModel = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<StasiunModel> stasiunTable;

    @FXML
    private TableColumn<StasiunModel, String> idstasiun;

    @FXML
    private TableColumn<StasiunModel, String> namastasiun;

    @FXML
    private TableColumn<StasiunModel, String> actionColumn;

    ObservableList<StasiunModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;




    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

        idstasiun.setCellValueFactory(new PropertyValueFactory<>("idstasiun"));
        namastasiun.setCellValueFactory(new PropertyValueFactory<>("namastasiun"));

        FilteredList<StasiunModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_data.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getNamastasiun().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }

                else {
                    return false;
                }
            }));
        });

        SortedList<StasiunModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(stasiunTable.comparatorProperty());

        Callback<TableColumn<StasiunModel, String>, TableCell<StasiunModel, String>> cellFactory = (TableColumn<StasiunModel, String> param) -> {
            final TableCell<StasiunModel, String> cell = new TableCell<StasiunModel, String>() {
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
                                stasiunModel = stasiunTable.getSelectionModel().getSelectedItem();
                                var query = "delete from tb_stasiun where idstasiun = ?";
                                var connection = SqliteConnection.Connector();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, stasiunModel.getIdstasiun());
                                preparedStatement.execute();
                                preparedStatement.close();
                                resultSet.close();
                                refreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            stasiunModel = stasiunTable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandleStasiun handle = new HandleStasiun();
                                StasiunModel data = new StasiunModel(stasiunModel.getIdstasiun(), stasiunModel.getNamastasiun());
                                handle.setUpdatestasiun(data);
                                main.changeScene("edit/EditStasiun.fxml");
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
        stasiunTable.setItems(dataList);
        stasiunTable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_stasiun";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new StasiunModel(
                        resultSet.getString("idstasiun"),
                        resultSet.getString("namastasiun")
                ));
                stasiunTable.setItems(dataList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }






    @FXML
    public void openlogin (ActionEvent event) throws IOException {
        Main login = new Main();
        login.changeScene("Login.fxml");
    }

    @FXML
    public void openadmintiket (ActionEvent event) throws IOException {
        Main admintiket = new Main();
        admintiket.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void tambah (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("tambah/TambahStasiun.fxml");
    }

    @FXML
    public void openadminuser (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("admin/AdminUser.fxml");
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
