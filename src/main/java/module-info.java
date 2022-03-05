module com.example.myticket {
    requires javafx.controls;
    requires com.jfoenix;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;
    requires java.prefs;
    requires jasperreports;
//    requires jasperreports;


    opens com.example.myticket to javafx.fxml;
    exports com.example.myticket;
    opens com.example.myticket.model to javafx.fxml;
    exports com.example.myticket.model;
    exports com.example.myticket.admin;
    opens com.example.myticket.admin to javafx.fxml;
    exports com.example.myticket.tambah;
    opens com.example.myticket.tambah to javafx.fxml;
    exports com.example.myticket.edit;
    opens com.example.myticket.edit to javafx.fxml;
}