package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.Ulty.Random;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    EditText edtId, edtPass, edtEmail, edtHoTen, edtSdt, edtChucVu, edtNgayvaolam,edtMucLuong;
    Button btn, btnNgayVaoLam;
    FirebaseAuth mAuth;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    int mYear, mMonth, mDay;
    Toolbar toolbar;
    ArrayList<ChucVu> SpinnerList;
    Spinner spinner;
    ArrayList<String> listMSNV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        anhxa();
        //toolbar
        toolbar.setTitle("Thêm mới");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        btnNgayVaoLam.setOnClickListener(this);
        btn.setOnClickListener(this);
        //Data
        SpinnerList = new ArrayList<ChucVu>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        getDSCV();
        getDSNV();


    }

    private void getDSNV() {
        mData = firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SaveListMSNV(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SaveListMSNV(DataSnapshot dataSnapshot) {
        listMSNV = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            listMSNV.add(ds.getValue(NhanVien.class).getManv());
        }
        GenMSNV();
    }

    private void addSpiner() {
        ArrayAdapter<ChucVu> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    private void getDSCV() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mData = firebaseDatabase.getReference().child("ChucVu");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SaveData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SaveData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            ChucVu a = ds.getValue(ChucVu.class);
            SpinnerList.add(a);
        }
        addSpiner();
    }

    private void GenMSNV() {
        int msnv = 1000;
        int max=0;
        max= Integer.parseInt(listMSNV.get(0));
//        msnv = listMSNV.size() + msnv + 1;
        //Tim Max
        for(int i=0;i<listMSNV.size();i++)
        {
          if(Integer.parseInt(listMSNV.get(i))>=max)
          {
              max=Integer.parseInt(listMSNV.get(i));

          }


        }
        max=max+1;

        edtId.setText(max + "");
        edtId.setEnabled(false);
        progressDialog.dismiss();

    }

    private void dangky() {
        final String id = edtId.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        final String hoten = edtHoTen.getText().toString();
        final String sdt = edtSdt.getText().toString();
        final String ngayvaolam = edtNgayvaolam.getText().toString();
        final ChucVu chucvu = (ChucVu) spinner.getSelectedItem();
        mData = firebaseDatabase.getReference().child("NhanVien");
        progressDialog.setMessage("Đăng ký...");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            NhanVien nv = new NhanVien();
                            nv.setManv(id);
                            nv.setEmail(email);
                            nv.setHoten(hoten);
                            nv.setAva("https://firebasestorage.googleapis.com/v0/b/chamcong-61010.appspot.com/o/user%5B1%5D.png?alt=media&token=0dd89d5e-e2b1-4854-a0e8-bb0ece88ddab");
                            nv.setSdt(sdt);
                            nv.setMucluong(Double.valueOf(edtMucLuong.getText().toString().trim()));
                            nv.setChucvu(chucvu.getIdChucVu());
                            try {
                                nv.setNgayvaolam(FormatHelper.formatstring(ngayvaolam));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mData.child(mAuth.getCurrentUser().getUid()).setValue(nv);
                            progressDialog.dismiss();
                            Toasty.success(DangKy.this, "Thành công", Toast.LENGTH_LONG, true).show();
                            finish();


                        } else {
                            progressDialog.dismiss();
                            Toasty.error(DangKy.this, "Thất bại", Toast.LENGTH_LONG, true).show();
                        }
                    }
                });


    }


    private void anhxa() {
        edtId = findViewById(R.id.edtID);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtHoTen = findViewById(R.id.edtHoTen);
        spinner = findViewById(R.id.spinnerChucVu);
        edtNgayvaolam = findViewById(R.id.edtNgayVaoLam);
        btnNgayVaoLam = findViewById(R.id.btnChonDate);
        edtSdt = findViewById(R.id.edtSDT);
        btn = findViewById(R.id.btnDK);
        edtMucLuong=findViewById(R.id.edtMucLuong);
        toolbar = findViewById(R.id.toolbarDangKy);
    }


    @Override
    public void onClick(View v) {
        if (v == btn) {
            dangky();
        } else if (v == btnNgayVaoLam) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            edtNgayvaolam.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

}
