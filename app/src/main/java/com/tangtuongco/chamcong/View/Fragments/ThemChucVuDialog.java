package com.tangtuongco.chamcong.View.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.R;

import es.dmoral.toasty.Toasty;

public class ThemChucVuDialog extends AppCompatDialogFragment {
    private EditText edtTenCV,edtMaCV,edtHeSoLuong;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("ChucVu");



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_add_chucvu,null);
        anhxa(view);
        builder.setView(view)
                .setTitle("Thêm chức vụ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenchucvu=edtTenCV.getText().toString();
                        String macv=edtMaCV.getText().toString();
                        String hesoluong=edtHeSoLuong.getText().toString();
                        ChucVu a = new ChucVu();
                        a.setTenChucVu(tenchucvu);
                        a.setIdChucVu(macv);
                        a.setHesoluong(Double.valueOf(hesoluong));
                        mData.child(macv).setValue(a);
                    }
                });
        return builder.create();
    }

    private void anhxa(View view) {
        edtTenCV=view.findViewById(R.id.edtTenChucVu);
        edtHeSoLuong=view.findViewById(R.id.edtHeSoLuong);
        edtMaCV=view.findViewById(R.id.edtMaChucVu);
    }


}
