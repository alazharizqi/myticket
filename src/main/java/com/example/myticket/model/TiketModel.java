package com.example.myticket.model;

public class TiketModel {

//    PENUMPANG
    public String nama_lengkap;
    public String nomor_telepon;
    public String bio;
    public String tipe_identitas;
    public String nomor_identitas;
    public String tgl_lahir;
    public String alamat;


//    KERETA
    public String kode_kereta;
    public String kereta;
    public String kelas;
    public String stasiun_asal;
    public String stasiun_tujuan;
    public String tgl_pergi;
    public String berangkat;
    public String tiba;
    public String gerbong;
    public String kursi;
    public String harga;
    public String status;

    public TiketModel(String nama_lengkap, String nomor_telepon, String bio, String tipe_identitas, String nomor_identitas, String tgl_lahir, String alamat, String kode_kereta, String kereta, String kelas, String stasiun_asal, String stasiun_tujuan, String tgl_pergi, String berangkat, String tiba, String gerbong, String kursi, String harga, String status) {
//        PENUMPANG
        this.nama_lengkap = nama_lengkap;
        this.nomor_telepon = nomor_telepon;
        this.bio = bio;
        this.tipe_identitas = tipe_identitas;
        this.nomor_identitas = nomor_identitas;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;

//        KERETA
        this.kode_kereta = kode_kereta;
        this.kereta = kereta;
        this.kelas = kelas;
        this.stasiun_asal= stasiun_asal;
        this.stasiun_tujuan = stasiun_tujuan;
        this.tgl_pergi = tgl_pergi;
        this.berangkat = berangkat;
        this.tiba = tiba;
        this.gerbong = gerbong;
        this.kursi = kursi;
        this.harga = harga;
        this.status = status;
    }

//    PENUMPANG
    public String getNama_lengkap() {return nama_lengkap;}
    public void setNama_lengkap(String nama_lengkap) {this.nama_lengkap = nama_lengkap;}

    public String getNomor_telepon() {return nomor_telepon;}
    public void setNomor_telepon(String nomor_telepon) {this.nomor_telepon = nomor_telepon;}

    public String getBio() {return bio;}
    public void setBio(String bio) {this.bio = bio;}

    public String getTipe_identitas() {return tipe_identitas;}
    public void setTipe_identitas(String tipe_identitas) {this.tipe_identitas = tipe_identitas;}

    public String getNomor_identitas() {return nomor_identitas;}
    public void setNomor_identitas(String nomor_identitas) {this.nomor_identitas = nomor_identitas;}

    public String getTgl_lahir() {return tgl_lahir;}
    public void setTgl_lahir(String tgl_lahir) {this.tgl_lahir = tgl_lahir;}

    public String getAlamat() {return alamat;}
    public void setAlamat(String alamat) {this.alamat = alamat;}

//    KERETA
    public String getKode_kereta() {return kode_kereta;}
    public void setKode_kereta(String kode_kereta) {this.kode_kereta = kode_kereta;}

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

    public String getHarga() {return harga;}
    public void setHarga(String harga) {this.harga = harga;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

}
