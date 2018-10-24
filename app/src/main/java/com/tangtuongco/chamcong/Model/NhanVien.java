package com.tangtuongco.chamcong.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NhanVien {
    private String manv,hoten,diachi,chucvu,email,sdt,pass,ava;
    private Date ngayvaolam;
    List<GioCong> listGioCong;
    //testtttt


    public NhanVien() {
//

    }

    public NhanVien(String manv, String hoten, String diachi, String chucvu, String email, String sdt, String pass, String ava, Date ngayvaolam, List<GioCong> listGioCong) {

        this.manv = manv;
        this.hoten = hoten;
        this.diachi = diachi;
        this.chucvu = chucvu;
        this.email = email;
        this.sdt = sdt;
        this.pass = pass;
        this.ava = ava;
        this.ngayvaolam = ngayvaolam;
        this.listGioCong = listGioCong;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
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

    public List<GioCong> getListGioCong() {
        return listGioCong;
    }

    public void setListGioCong(List<GioCong> listGioCong) {
        this.listGioCong = listGioCong;
    }




//    public void getDanhSachNhanVien()
//    {
//        final List<NhanVien> listNhanVien  = new ArrayList<>();
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                DataSnapshot dataSnapshotNhanVien = dataSnapshot.child("NhanVien");
//                for(DataSnapshot valueNhanVien:dataSnapshotNhanVien.getChildren())
//                {
//                    NhanVien a = valueNhanVien.getValue(NhanVien.class);
//
//                    listNhanVien.add(a);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
//
//
//
//    }



}
