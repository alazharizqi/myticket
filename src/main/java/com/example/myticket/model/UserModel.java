package com.example.myticket.model;

public class UserModel {
    public String id_user;
    public String user;
    public String email;
    public String pass;
    public String role;

    public UserModel(String id_user, String user, String email, String pass, String role) {
        this.id_user = id_user;
        this.user = user;
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    public String getId_user() {return id_user;}

    public void setId_user(String id_user) {this.id_user = id_user;}

    public String getUser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPass() {return pass;}
    public void setPass(String pass) {this.pass = pass;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
}
