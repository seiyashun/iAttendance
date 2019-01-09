package com.tangtuongco.chamcong.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.Fragments.ThayDoiMatKhauAdminDialog;

public class AdminPanel extends AppCompatActivity {
    TextView txtTenCN1, txtTenCN2, txtMoTa1, txtMoTa2;
    ImageView imgCN1, imgCN2,imgCN3;
    LinearLayout linear1, linear2,linear3;
    Toolbar toolbar;
    DatabaseReference mData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        anhxa();
        //Toolbar
        toolbar.setTitle("Admin");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //ProcressDialog
        progressDialog = new ProgressDialog(this);

        setImg();
        setEvent();
    }

    private void setEvent() {
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Intent i = new Intent(AdminPanel.this, AdminQuanLy.class);
                startActivity(i);
                progressDialog.dismiss();

            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Intent i = new Intent(AdminPanel.this, AdminChucVu.class);
                startActivity(i);
                progressDialog.dismiss();
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiMatKhau();
            }
        });
    }

    private void DoiMatKhau() {
        ThayDoiMatKhauAdminDialog thayDoiMatKhauAdminDialog = new ThayDoiMatKhauAdminDialog();
        thayDoiMatKhauAdminDialog.show(getSupportFragmentManager(), "Thay đổi mật khẩu");
    }


    private void setImg() {
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/chamcong-61010.appspot.com/o/staff_manage%5B1%5D.png?alt=media&token=398a40d0-d11c-43a9-9c20-9190691646a8")
                .into(imgCN1);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/chamcong-61010.appspot.com/o/SSH-Keys.png?alt=media&token=bf426906-456f-4537-8ff3-518f8074d58d")
                .into(imgCN2);
        Glide.with(this)
                .load("http://www.westerdijkinstitute.nl/images/lock.jpg")
                .into(imgCN3);

    }

    private void anhxa() {
        txtTenCN1 = findViewById(R.id.txtTenChucNang);
        txtTenCN2 = findViewById(R.id.txtTenChucNang2);
        txtMoTa1 = findViewById(R.id.txtMoTaChucNang);
        txtMoTa2 = findViewById(R.id.txtMoTaChucNang2);
        imgCN1 = findViewById(R.id.imgChucNang);
        imgCN2 = findViewById(R.id.imgChucNang2);
        imgCN3=findViewById(R.id.imgChucNang3);
        linear1 = findViewById(R.id.linearNV);
        linear2 = findViewById(R.id.linearCV);
        linear3=findViewById(R.id.linearAdmin);
        toolbar = findViewById(R.id.toolbarAdminMain);
    }
}
