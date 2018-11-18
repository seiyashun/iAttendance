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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.R;

public class SuaChucVuDialog extends AppCompatDialogFragment {

    private EditText edtTenCV,edtMaCV,edtHeSoLuong;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    public static SuaChucVuDialog newIn(String id)
    {
        SuaChucVuDialog fragment= new SuaChucVuDialog();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return  fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("ChucVu");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ChucVu a = dataSnapshot1.getValue(ChucVu.class);
                    {
                        if(a.getIdChucVu().equals(getArguments().getString("id")))
                        {
                            edtMaCV.setFocusable(false);
                            edtTenCV.setText(a.getTenChucVu());
                            edtHeSoLuong.setText(String.valueOf(a.getHesoluong()));
                            edtMaCV.setText(a.getIdChucVu());

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_add_chucvu,null);
        anhxa(view);
        builder.setView(view)
                .setTitle("Sửa chức vụ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
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
