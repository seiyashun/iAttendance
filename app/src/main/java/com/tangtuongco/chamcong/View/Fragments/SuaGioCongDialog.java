package com.tangtuongco.chamcong.View.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.GioCong;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.CheckEditext;

import java.util.Calendar;

public class SuaGioCongDialog extends AppCompatDialogFragment {
    EditText edtNgay, edtGioVao, edtGioRa;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    String fulldate, ngay, thang, manv, nam;

    public static SuaGioCongDialog newIn(String ngaytxt) {
        SuaGioCongDialog fragment = new SuaGioCongDialog();
        Bundle bundle = new Bundle();
        bundle.putString("id", ngaytxt);
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        tachchuoi();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        int thangint = Integer.valueOf(thang);
        thangint = thangint - 1;
        String thangString;
        if (thangint < 10) {
            thangString = "0" + String.valueOf(thangint);
        } else {
            thangString = String.valueOf(thangint);
        }


        mData = firebaseDatabase.getReference().child("GioCong").child(manv).child(String.valueOf(nam)).child(thangString);
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    GioCong a = ds.getValue(GioCong.class);
                    if (a.getNgay().equals(fulldate)) {
                        edtGioRa.setText(a.getGioRa());
                        edtGioVao.setText(a.getGioVao());
                        edtNgay.setText(a.getNgay());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_edit_giocong, null);
        anhxa(view);
        edtNgay.setEnabled(false);
        builder.setView(view)
                .setTitle("Thay Đổi Giờ Công")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateGioCong();
                    }
                });
        return builder.create();

    }

    private void UpdateGioCong() {

        int year = Integer.valueOf(nam);

        String GioVao = edtGioVao.getText().toString();
        String GioRa = edtGioRa.getText().toString();
        String Ngay = edtNgay.getText().toString();
        if (GioVao.length() != 8 || GioRa.length() != 8) {
            Toast.makeText(getActivity(), "Xin nhập đúng định dạng thời gian hh:mm:ss", Toast.LENGTH_SHORT).show();
        } else {
            if (CheckEditext.isTime(GioVao) == false) {
                Toast.makeText(getActivity(), "Xin nhập đúng định dạng thời gian hh:mm:ss", Toast.LENGTH_SHORT).show();
            } else if (CheckEditext.isTime(GioRa) == false) {
                Toast.makeText(getActivity(), "Xin nhập đúng định dạng thời gian hh:mm:ss", Toast.LENGTH_SHORT).show();
            } else {
                firebaseDatabase = FirebaseDatabase.getInstance();
                int thangint = Integer.valueOf(thang);
                thangint = thangint - 1;
                String thangString;
                if (thangint < 10) {
                    thangString = "0" + String.valueOf(thangint);
                } else {
                    thangString = String.valueOf(thangint);
                }
                mData = firebaseDatabase.getReference().child("GioCong").child(manv).child(String.valueOf(year)).child(thangString).child(ngay);

                GioCong gioCong = new GioCong();
                gioCong.setClickIn(true);
                gioCong.setClickOut(true);
                gioCong.setGioRa(GioRa);
                gioCong.setGioVao(GioVao);
                gioCong.setNgay(Ngay);
                mData.setValue(gioCong);


            }
        }

    }


    private void anhxa(View view) {
        edtNgay = view.findViewById(R.id.edtNgayEditGioCong);
        edtGioRa = view.findViewById(R.id.edtGioRa);
        edtGioVao = view.findViewById(R.id.edtGioVao);
    }


    private void tachchuoi() {

        String ngayNV;
        ngayNV = getArguments().getString("id");


        ngay = ngayNV.substring(0, 2);


        nam = ngayNV.substring(6, 10);
        thang = ngayNV.substring(3, 5);
        manv = ngayNV.substring(10, 14);
        fulldate = ngayNV.substring(0, 10);


    }
}
