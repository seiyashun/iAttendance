package com.tangtuongco.chamcong.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import com.tangtuongco.chamcong.Ulty.CheckEditext;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class DangNhap extends AppCompatActivity {
    FancyButton btnDangNhap;
    private FirebaseAuth mAuth;
    EditText edtID, edtPass;
    ProgressDialog progressDialog;
    DatabaseReference mData;
    TextView txtQenMatKhau;
    CheckBox checkbox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference().child("Admin");
        progressDialog = new ProgressDialog(this);
        anhxa();
        //Data

        btnDangNhap.setText("Đăng Nhập");
        btnDangNhap.setBackgroundColor(Color.parseColor("#c59783"));
        btnDangNhap.setFocusBackgroundColor(Color.parseColor("#c59783"));
        btnDangNhap.setTextSize(20);
        btnDangNhap.setRadius(15);
        btnDangNhap.setIconPosition(FancyButton.POSITION_LEFT);
        btnDangNhap.setFontIconSize(30);


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đăng nhập...");
                progressDialog.show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtID.getWindowToken(), 0);
                String username,password;
                username = edtID.getText().toString();
                password = edtPass.getText().toString();

                if (checkbox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
                dangnhap();
            }
        });

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtID.setText(loginPreferences.getString("username", ""));
            edtPass.setText(loginPreferences.getString("password", ""));
            checkbox.setChecked(true);
        }


        txtQenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                String email = edtID.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toasty.warning(getApplication(), "Xin nhập địa chỉ Email", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.sendPasswordResetEmail(email)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DangNhap.this, "Đã gửi email khôi phục mật khẩu", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DangNhap.this, "Sai địa chỉ email", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();


                            }
                        });
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
                        } else {

                            progressDialog.dismiss();
                            Toasty.error(DangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (CheckEditext.isEmail(id) == false) {
            progressDialog.dismiss();
            Toasty.warning(this, "Xin nhập email", Toast.LENGTH_SHORT).show();
        } else if (CheckEditext.isEmpty(pass) == true) {

            progressDialog.dismiss();
            Toasty.warning(this, "Xin nhập mật khẩu", Toast.LENGTH_SHORT).show();
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
                                Toasty.error(DangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }




    }


    private void anhxa() {
        edtPass = findViewById(R.id.edtPassDN);
        edtID = findViewById(R.id.edtEmailDN);
        txtQenMatKhau = findViewById(R.id.txtQuenMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        checkbox=findViewById(R.id.saveLoginCheckBox);
    }


}
