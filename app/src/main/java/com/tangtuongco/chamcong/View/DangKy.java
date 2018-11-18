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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    EditText edtId,edtPass,edtEmail,edtHoTen,edtSdt,edtChucVu,edtNgayvaolam;
    Button btn,btnNgayVaoLam;
    FirebaseAuth mAuth;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    int mYear,mMonth,mDay;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth=FirebaseAuth.getInstance();
        anhxa();
        //toolbar
        toolbar.setTitle("Thêm Quản Lý");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressDialog =new ProgressDialog(this);
        btnNgayVaoLam.setOnClickListener(this);
        btn.setOnClickListener(this);
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        edtChucVu.setText("QL");













    }





    private void dangky() {
        final String id=edtId.getText().toString().trim();
        final String email=edtEmail.getText().toString().trim();
        final String pass=edtPass.getText().toString().trim();
        final String hoten=edtHoTen.getText().toString();
        final String sdt=edtSdt.getText().toString();
        final String chucuv=edtChucVu.getText().toString();
        final String ngayvaolam=edtNgayvaolam.getText().toString();
        mData=firebaseDatabase.getReference().child("NhanVien");


        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            NhanVien nv=new NhanVien();
                            nv.setManv(id);
                            nv.setEmail(email);
                            nv.setHoten(hoten);
                            nv.setAva("1");
                            nv.setSdt(sdt);
                            nv.setChucvu(chucuv);
                            try {
                                nv.setNgayvaolam(FormatHelper.formatstring(ngayvaolam));
                            } catch (Exception e) {
                                e.printStackTrace();
                           }
                            mData.push().setValue(nv);
                            Toasty.success(DangKy.this,"Thành công",Toast.LENGTH_LONG,true).show();


                        }
                        else
                        {
                            Toasty.error(DangKy.this,"Thất bại",Toast.LENGTH_LONG,true).show();
                        }
                    }
                });



    }


    private void anhxa() {
        edtId=findViewById(R.id.edtID);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        edtHoTen=findViewById(R.id.edtHoTen);
        edtChucVu=findViewById(R.id.edtTenChucVuDangKy);
        edtNgayvaolam=findViewById(R.id.edtNgayVaoLam);
        btnNgayVaoLam=findViewById(R.id.btnChonDate);
        edtSdt=findViewById(R.id.edtSDT);
        btn=findViewById(R.id.btnDK);

        toolbar=findViewById(R.id.toolbarDangKy);
    }


    @Override
    public void onClick(View v) {
        if(v == btn)
        {
            dangky();
        }
        else if(v==btnNgayVaoLam)
        {

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
