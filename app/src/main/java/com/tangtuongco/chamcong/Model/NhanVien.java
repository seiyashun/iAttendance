package com.tangtuongco.chamcong.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class NhanVien {
    public String manv,hoten,chucvu,email,sdt,pass,ava;
    public Date ngayvaolam;
    double mucluong;
    List<GioCong> listGioCong;

    public NhanVien() {
    }

    public String getManv() {

        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public Date getNgayvaolam() {
        return ngayvaolam;
    }

    public void setNgayvaolam(Date ngayvaolam) {
        this.ngayvaolam = ngayvaolam;
    }

    public double getMucluong() {
        return mucluong;
    }

    public void setMucluong(double mucluong) {
        this.mucluong = mucluong;
    }

    public List<GioCong> getListGioCong() {
        return listGioCong;
    }

    public void setListGioCong(List<GioCong> listGioCong) {
        this.listGioCong = listGioCong;
    }

    public NhanVien(String manv, String hoten, String chucvu, String email, String sdt, String pass, String ava, Date ngayvaolam, double mucluong, List<GioCong> listGioCong) {

        this.manv = manv;
        this.hoten = hoten;
        this.chucvu = chucvu;
        this.email = email;
        this.sdt = sdt;
        this.pass = pass;
        this.ava = ava;
        this.ngayvaolam = ngayvaolam;
        this.mucluong = mucluong;
        this.listGioCong = listGioCong;
    }
}
