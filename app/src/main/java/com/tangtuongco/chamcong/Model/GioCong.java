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

public class GioCong {
    private String Ngay,GioVao,GioRa;
    private Boolean ClickIn,ClickOut;

    public GioCong() {
        ClickOut=false;

    }

    public GioCong(String ngay, String gioVao, String gioRa, Boolean clickIn, Boolean clickOut) {

        Ngay = ngay;
        GioVao = gioVao;
        GioRa = gioRa;
        ClickIn = clickIn;
        ClickOut = clickOut;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getGioVao() {
        return GioVao;
    }

    public void setGioVao(String gioVao) {
        GioVao = gioVao;
    }

    public String getGioRa() {
        return GioRa;
    }

    public void setGioRa(String gioRa) {
        GioRa = gioRa;
    }

    public Boolean getClickIn() {
        return ClickIn;
    }

    public void setClickIn(Boolean clickIn) {
        ClickIn = clickIn;
    }

    public Boolean getClickOut() {
        return ClickOut;
    }

    public void setClickOut(Boolean clickOut) {
        ClickOut = clickOut;
    }
}
