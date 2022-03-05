package com.example.myticket.admin;

import com.example.myticket.Main;
import com.example.myticket.connection.SqliteConnection;
import com.example.myticket.handle.HandleUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.myticket.model.UserModel;
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

public class AdminUser implements Initializable {
    UserModel model = null;

    @FXML
    private TextField cari_data;

    @FXML
    private TableView<UserModel> usertable;

    @FXML
    private TableColumn<UserModel, String> id_user;

    @FXML
    private TableColumn<UserModel, String> user;

    @FXML
    private TableColumn<UserModel, String> role;

    @FXML
    private TableColumn<UserModel, String> email;

    @FXML
    private TableColumn<UserModel, String> pass;

    @FXML
    private TableColumn<UserModel, String> actionColumn;

    ObservableList<UserModel> dataList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    public void Table() {
        var connection = SqliteConnection.Connector();
        refreshTable();

        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        pass.setCellValueFactory(new PropertyValueFactory<>("pass"));

        FilteredList<UserModel> filteredList = new FilteredList<>(dataList, e -> true);

        cari_data.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((productUser -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                if (productUser.getUser().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }
                else if (productUser.getRole().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getEmail().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if (productUser.getPass().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                }
                else {
                    return false;
                }
            }));
        });

        SortedList<UserModel> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(usertable.comparatorProperty());

        Callback<TableColumn<UserModel, String>, TableCell<UserModel, String>> cellFoctory = (TableColumn<UserModel, String> param) -> {
            final TableCell<UserModel, String> cell = new TableCell<UserModel, String>() {
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
                                model = usertable.getSelectionModel().getSelectedItem();
                                var query = "delete from tb_user where id_user = ?";
                                var connection = SqliteConnection.Connector();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, model.getId_user());
                                preparedStatement.execute();
                                preparedStatement.close();
                                resultSet.close();
                                refreshTable();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                        });

                        updateIcon.setOnMouseClicked((MouseEvent event) -> {
                            model = usertable.getSelectionModel().getSelectedItem();
                            try {
                                Main main = new Main();
                                HandleUser handle = new HandleUser();
                                UserModel data = new UserModel(model.getId_user(), model.getUser(), model.getEmail(), model.getPass(), model.getRole());
                                handle.setUpdateuser(data);
                                main.changeScene("edit/EditUser.fxml");
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
        actionColumn.setCellFactory(cellFoctory);
        usertable.setItems(dataList);
        usertable.setItems(sortedList);
    }


    public void refreshTable() {
        try {
            dataList.clear();

            var query = "select * from tb_user";
            var connection = SqliteConnection.Connector();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dataList.add(new UserModel(
                        resultSet.getString("id_user"),
                        resultSet.getString("user"),
                        resultSet.getString("email"),
                        resultSet.getString("pass"),
                        resultSet.getString("role")
                ));
                usertable.setItems(dataList);
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
    public void openstasiun (ActionEvent event) throws IOException {
        Main login = new Main();
        login.changeScene("admin/AdminStasiun.fxml");
    }

    @FXML
    public void openadmintiket (ActionEvent event) throws IOException {
        Main admintiket = new Main();
        admintiket.changeScene("admin/AdminKereta.fxml");
    }

    @FXML
    public void openadmincreate (ActionEvent event) throws IOException {
        Main admincreate = new Main();
        admincreate.changeScene("tambah/TambahUser.fxml");
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
