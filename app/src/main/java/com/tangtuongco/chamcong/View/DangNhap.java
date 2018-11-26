package com.tangtuongco.chamcong.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Service.StartService;

import es.dmoral.toasty.Toasty;

public class DangNhap extends AppCompatActivity {
    Button btnDangNhap;
    private FirebaseAuth mAuth;
    EditText edtID, edtPass;
    ProgressDialog progressDialog;
    DatabaseReference mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference().child("Admin");
        progressDialog = new ProgressDialog(this);
        anhxa();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đăng nhập...");
                progressDialog.show();
                dangnhap();
            }
        });


    }

    private void dangnhap() {


        final String id, pass;
        id = edtID.getText().toString().trim();
        pass = edtPass.getText().toString().trim();
        if (id.equals("admin")) {
            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NhanVien a = dataSnapshot1.getValue(NhanVien.class);
                        if (a.getPass().equals(pass)) {
                            progressDialog.dismiss();
                            Intent i = new Intent(DangNhap.this, AdminPanel.class);
                            startActivity(i);
                            Toasty.success(DangNhap.this, "Welcome back, Admin!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            mAuth.signInWithEmailAndPassword(id, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Intent i = new Intent(DangNhap.this, MainActivity.class);
                                //Toasty.success(DangNhap.this, "Welcome " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(i);





                            } else {
                                progressDialog.dismiss();
                                Toasty.error(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }


    private void anhxa() {
        edtPass = findViewById(R.id.edtPassDN);
        edtID = findViewById(R.id.edtEmailDN);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }


}
