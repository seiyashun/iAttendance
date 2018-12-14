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
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.AdminPanel;

public class ThayDoiMatKhauAdminDialog extends AppCompatDialogFragment {
    EditText edtPass1,edtPass2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //Data

        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("Admin");






        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_edit_admin,null);
        anhxa(view);
        builder.setView(view)
                .setTitle("Thay Đổi Mật Khẩu")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!edtPass1.getText().toString().equals(edtPass2.getText().toString()))
                        {
                            Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds:dataSnapshot.getChildren())
                                    {
                                        String password=edtPass1.getText().toString();
                                        NhanVien a= ds.getValue(NhanVien.class);
                                        a.setPass(password);
                                        ds.getRef().setValue(a);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }




    private void anhxa(View view) {
        edtPass1=view.findViewById(R.id.edtPassAdmin1);
        edtPass2=view.findViewById(R.id.edtPassAdmin2);

    }

}
