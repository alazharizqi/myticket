package com.example.myticket.model;

public class KeretaModel {

    public String id_kereta;
    public String kereta;
    public String kelas;
    public String stasiun_asal;
    public String stasiun_tujuan;
    public String tgl_pergi;
    public String berangkat;
    public String tiba;
    public String gerbong;
    public String kursi;
    public String status;
    public String harga;

    public KeretaModel(String id_kereta, String kereta, String kelas, String stasiun_asal, String stasiun_tujuan, String tgl_pergi, String berangkat, String tiba, String gerbong, String kursi, String status, String harga) {
        this.id_kereta = id_kereta;
        this.kereta = kereta;
        this.kelas = kelas;
        this.stasiun_asal= stasiun_asal;
        this.stasiun_tujuan = stasiun_tujuan;
        this.tgl_pergi = tgl_pergi;
        this.berangkat = berangkat;
        this.tiba = tiba;
        this.gerbong = gerbong;
        this.kursi = kursi;
        this.status = status;
        this.harga = harga;
    }

    public String getId_kereta() {return id_kereta;}
    public void setId_kereta(String id_kereta) {this.id_kereta = id_kereta;}

    public String getKereta() {return kereta;}
    public void setKereta(String kereta) {this.kereta = kereta;}

    public String getKelas() {return kelas;}
    public void setKelas(String kelas) {this.kelas = kelas;}

    public String getStasiun_asal() {return stasiun_asal;}
    public void setStasiun_asal(String stasiun_asal) {this.stasiun_asal = stasiun_asal;}

    public String getStasiun_tujuan() {return stasiun_tujuan;}
    public void setStasiun_tujuan(String stasiun_tujuan) {this.stasiun_tujuan = stasiun_tujuan;}

    public String getTgl_pergi() {return tgl_pergi;}
    public void setTgl_pergi(String tgl_pergi) {this.tgl_pergi = tgl_pergi;}

    public String getBerangkat() {return berangkat;}
    public void setBerangkat(String berangkat) {this.berangkat = berangkat;}

    public String getTiba() {return tiba;}
    public void setTiba(String tiba) {this.tiba = tiba;}

    public String getGerbong() {return gerbong;}
    public void setGerbong(String gerbong) {this.gerbong = gerbong;}

    public String getKursi() {return kursi;}
    public void setKursi(String kursi) {this.kursi = kursi;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getHarga() {return harga;}
    public void setHarga(String harga) {this.harga = harga;}
}
