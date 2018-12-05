package com.tangtuongco.chamcong.View;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.CheckEditext;
import com.tangtuongco.chamcong.Ulty.FormatHelper;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class SuaThongTin extends AppCompatActivity {
    EditText edtMaNv,edtHoTen,edtSDT,edtMucLuong,edtEmail,edtNgayVaoLam,edtChucVu;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    String email;
    Toolbar toolbar;
    FancyButton btnSave;
    NhanVien nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
        anhxa();
        init();


    }

    private void init() {
        //Toolbar
        toolbar.setTitle("Thông Tin Cá Nhân");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Data
        mAuth=FirebaseAuth.getInstance();
        email=mAuth.getCurrentUser().getEmail();
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("NhanVien");
        data();
        //Disable
        edtChucVu.setEnabled(false);
        edtNgayVaoLam.setEnabled(false);
        edtMucLuong.setEnabled(false);
        edtEmail.setEnabled(false);
        edtMaNv.setEnabled(false);
        //Button
        btnSave.setText("Lưu Thông Tin");
        btnSave.setBackgroundColor(Color.parseColor("#c59783"));
        btnSave.setFocusBackgroundColor(Color.parseColor("#b98068"));
        btnSave.setTextSize(20);
        btnSave.setRadius(7);
        btnSave.setIconPosition(FancyButton.POSITION_LEFT);
        btnSave.setFontIconSize(30);
        nv= new NhanVien();
        readData(new FirebaseCallBack() {
            @Override
            public void onCallBack(NhanVien a) {
                nv=a;
            }
        });
        //Click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nv.setSdt(edtSDT.getText().toString().trim());
                nv.setHoten(edtHoTen.getText().toString().trim());
                if(CheckEditext.isEmpty(nv.getSdt())==true || CheckEditext.isEmpty(nv.getHoten())==true )
                {
                    Toasty.warning(getApplicationContext(), "Xin nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mData.child(mAuth.getCurrentUser().getUid()).setValue(nv);
                    Toast.makeText(SuaThongTin.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });


    }

    private void data() {
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NhanVien a = ds.getValue(NhanVien.class);
                    {
                        if(a.getEmail().equals(email))
                        {

                            edtMaNv.setText(a.getManv());
                            edtEmail.setText(a.getEmail());
                            edtMucLuong.setText(String.valueOf(a.getMucluong()));
                            edtNgayVaoLam.setText(FormatHelper.formatNgay(a.getNgayvaolam()));
                            edtChucVu.setText(a.getChucvu());
                            edtSDT.setText(a.getSdt());
                            edtHoTen.setText(a.getHoten());

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readData(final FirebaseCallBack firebaseCallBack)
    {
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NhanVien a = ds.getValue(NhanVien.class);
                    {
                        if(a.getEmail().equals(email))
                        {

                            edtMaNv.setText(a.getManv());
                            edtEmail.setText(a.getEmail());
                            edtMucLuong.setText(String.valueOf(a.getMucluong()));
                            edtNgayVaoLam.setText(FormatHelper.formatNgay(a.getNgayvaolam()));
                            edtChucVu.setText(a.getChucvu());
                            edtSDT.setText(a.getSdt());
                            edtHoTen.setText(a.getHoten());
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
        void onCallBack(NhanVien a);
    }



    private void anhxa() {
        toolbar=findViewById(R.id.toolbarSuaThongTinCaNhan);
        edtMaNv=findViewById(R.id.edtMaNhanVienCaNhan);
        edtHoTen=findViewById(R.id.edtHoVaTenCaNhan);
        edtSDT=findViewById(R.id.edtSDTCaNhan);
        edtMucLuong=findViewById(R.id.edtMucLuongCaNhan);
        edtEmail=findViewById(R.id.edtEmailCaNhan);
        edtNgayVaoLam=findViewById(R.id.edtNgayVaoLamCaNhan);
        edtChucVu=findViewById(R.id.edtChucVuCaNhan);
        btnSave=findViewById(R.id.btnSaveThongTin);
    }
}
