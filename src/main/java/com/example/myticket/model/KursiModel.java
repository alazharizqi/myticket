package com.example.myticket.model;

public class KursiModel {

    public String id_kursi;
    public String kursi;

    public KursiModel(String id_kursi, String kursi) {
        this.id_kursi = id_kursi;
        this.kursi = kursi;
    }

    public String getId_kursi() {return id_kursi;}

    public void setId_kursi(String id_kursi) {this.id_kursi = id_kursi;}

    public String getKursi() {
        return kursi;
    }

    public void setKursi(String kursi) {
        this.kursi = kursi;
    }

}
