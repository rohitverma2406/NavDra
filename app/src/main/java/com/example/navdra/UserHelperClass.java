package com.example.navdra;
import android.net.Uri;

public class UserHelperClass {
    Uri imageuri;
    String name, phone, status, bra;
    public UserHelperClass(){}

    public UserHelperClass(Uri imageuri, String name, String phone, String status, String bra) {
        this.imageuri = imageuri;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.bra = bra;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBra() {
        return bra;
    }

    public void setBra(String bra) {
        this.bra = bra;
    }
}
