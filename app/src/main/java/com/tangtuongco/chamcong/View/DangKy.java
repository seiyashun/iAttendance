package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    EditText edtId,edtPass,edtEmail,edtHoTen,edtSdt,edtChucVu,edtNgayvaolam;
    Button btn,btnNgayVaoLam;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    int mYear,mMonth,mDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth=FirebaseAuth.getInstance();
        anhxa();
        progressDialog =new ProgressDialog(this);
        btnNgayVaoLam.setOnClickListener(this);
        btn.setOnClickListener(this);




    }

    private void dangky() {
        final String id=edtId.getText().toString().trim();
        final String email=edtEmail.getText().toString().trim();
        final String pass=edtPass.getText().toString().trim();
        final String hoten=edtHoTen.getText().toString();
        final String sdt=edtSdt.getText().toString();
        final String chucvu=edtChucVu.getText().toString();
        final String ngayvaolam=edtNgayvaolam.getText().toString();



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
                            nv.setChucvu(chucvu);
                            try {
                                nv.setNgayvaolam(FormatHelper.formatstring(ngayvaolam));
                            } catch (Exception e) {
                                e.printStackTrace();
                           }
                            String Uid=mAuth.getCurrentUser().getUid();
                            DatabaseReference dataUser=FirebaseDatabase.getInstance().getReference().child("NhanVien").child(Uid);
                            dataUser.setValue(nv);
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
        edtChucVu=findViewById(R.id.edtChucVu);
        edtNgayvaolam=findViewById(R.id.edtNgayVaoLam);
        btnNgayVaoLam=findViewById(R.id.btnChonDate);
        edtSdt=findViewById(R.id.edtSDT);
        btn=findViewById(R.id.btnDK);
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
