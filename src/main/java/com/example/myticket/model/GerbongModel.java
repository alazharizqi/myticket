package com.example.myticket.model;

public class GerbongModel {

    public String id_gerbong;
    public String gerbong;

    public GerbongModel(String id_gerbong, String gerbong) {
        this.id_gerbong = id_gerbong;
        this.gerbong = gerbong;
    }

    public String getId_gerbong() {return id_gerbong;}

    public void setId_gerbong(String id_gerbong) {this.id_gerbong = id_gerbong;}

    public String getGerbong() {
        return gerbong;
    }

    public void setGerbong(String gerbong) {
        this.gerbong = gerbong;
    }

}
