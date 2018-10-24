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
   Date ngay;
   Integer giovao,giora,giothucte;

    public GioCong() {

    }

    public GioCong(Date ngay, Integer giovao, Integer giora, Integer giothucte) {

        this.ngay = ngay;
        this.giovao = giovao;
        this.giora = giora;
        this.giothucte = giothucte;
    }

    public Date getNgay() {

        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public Integer getGiovao() {
        return giovao;
    }

    public void setGiovao(Integer giovao) {
        this.giovao = giovao;
    }

    public Integer getGiora() {
        return giora;
    }

    public void setGiora(Integer giora) {
        this.giora = giora;
    }

    public Integer getGiothucte() {
        return giothucte;
    }

    public void setGiothucte(Integer giothucte) {
        this.giothucte = giothucte;
    }

    public void getlistGioCong()
    {





    }

}
