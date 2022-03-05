package com.example.myticket.model;

public class StasiunModel {

    public String idstasiun;
    public String namastasiun;

    public StasiunModel(String idstasiun, String namastasiun) {
        this.idstasiun = idstasiun;
        this.namastasiun = namastasiun;
    }

    public String getIdstasiun() {return idstasiun;}

    public void setIdstasiun(String idstasiun) {this.idstasiun = idstasiun;}

    public String getNamastasiun() {
        return namastasiun;
    }

    public void setNamastasiun(String namastasiun) {
        this.namastasiun = namastasiun;
    }

}
