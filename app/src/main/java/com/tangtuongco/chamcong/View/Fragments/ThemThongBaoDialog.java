package com.tangtuongco.chamcong.View.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class ThemThongBaoDialog extends AppCompatDialogFragment {
    private EditText edtTieuDe,edtMaThongBao,edtNoiDungThongBao;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("ThongBao");





        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_themthongbao,null);
        anhxa(view);
        edtMaThongBao.setText(genMA());
        edtMaThongBao.setEnabled(false);
        builder.setView(view)
                .setTitle("Thêm Thông Báo")
                .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThongBao a = new ThongBao();
                        a.setNotiDate(Calendar.getInstance().getTime());
                        a.setNotiMess(edtNoiDungThongBao.getText().toString());
                        a.setNotiTitle(edtTieuDe.getText().toString());
                        a.setNotiUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        a.setNotiId(genMA());
                        mData.push().setValue(a);
                    }
                });
        return builder.create();
    }
    private String genMA()
    {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        return datetime;
    }
    private void anhxa(View view) {
        edtMaThongBao=view.findViewById(R.id.edtMaThongBao);
        edtNoiDungThongBao=view.findViewById(R.id.edtNoiDungThongBao);
        edtTieuDe=view.findViewById(R.id.edtTieuDeThongBao);
    }

}
