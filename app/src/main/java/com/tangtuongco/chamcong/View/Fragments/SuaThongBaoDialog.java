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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.SuaThongTin;

public class SuaThongBaoDialog extends AppCompatDialogFragment {
    private EditText edtTieuDe,edtMaThongBao,edtNoiDungThongBao;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    ThongBao thongbao;
    public static SuaThongBaoDialog newIn(String id)
    {
        SuaThongBaoDialog fragment= new SuaThongBaoDialog();
        Bundle bundle=new Bundle();
        bundle.putString("idthongbao",id);
        fragment.setArguments(bundle);
        return  fragment;

    }






    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //Data
        thongbao=new ThongBao();
        readData(new FirebaseCallBack() {
            @Override
            public void onCallBack(ThongBao a) {
                thongbao=a;
            }
        });
        Log.d("kiemtra",thongbao.getNotiId()+"");
        Log.d("kiemtra",getArguments().getString("idthongbao"));

        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("ThongBao");






        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_themthongbao,null);
        anhxa(view);
        builder.setView(view)
                .setTitle("Sửa thông báo ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thongbao.setNotiTitle(edtTieuDe.getText().toString());
                        thongbao.setNotiMess(edtNoiDungThongBao.getText().toString());
                        mData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren())
                                {
                                    ThongBao a = ds.getValue(ThongBao.class);
                                    if(a.getNotiId().equals(thongbao.getNotiId()))
                                    {
                                        ds.getRef().setValue(thongbao);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
        return builder.create();
    }

    private void readData(final FirebaseCallBack firebaseCallBack)
    {
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("ThongBao");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ThongBao a = dataSnapshot1.getValue(ThongBao.class);
                    {
                        if(a.getNotiId().toString().equals(getArguments().getString("idthongbao")))
                        {
                            edtNoiDungThongBao.setText(a.getNotiMess());
                            edtTieuDe.setText(a.getNotiTitle());
                            edtMaThongBao.setText(a.getNotiId());
                            edtMaThongBao.setEnabled(false);
                            firebaseCallBack.onCallBack(a);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallBack{
        void onCallBack(ThongBao a);
    }



    private void anhxa(View view) {
        edtMaThongBao=view.findViewById(R.id.edtMaThongBao);
        edtTieuDe=view.findViewById(R.id.edtTieuDeThongBao);
        edtNoiDungThongBao=view.findViewById(R.id.edtNoiDungThongBao);
    }
}
