package com.tangtuongco.chamcong.View;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tangtuongco.chamcong.R;
import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

import mehdi.sakout.fancybuttons.FancyButton;
import ss.com.bannerslider.Slider;

public class QuanLyPanel extends AppCompatActivity {
    FadingTextView FTV;
    FancyButton btnTTNV,btnLuongNV,btnThongBao,btnXemLuong,btnQuanLyLichNhanVien;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_panel);
        anhxa();
        init();
    }

    private void init() {
        //Set toolbar
        //Toolbar
        toolbar.setTitle("Quản Lý");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Text thay doi
        String[] texts = {"Welcome to Coffee TEK","Manager Panel"};
        FTV.setTexts(texts);
        FTV.setTimeout(3, TimeUnit.SECONDS);
        //Setup fancy button
        btnLuongNV.setText("Quản Lý Giờ Công");
        btnLuongNV.setBackgroundColor(Color.parseColor("#3b5998"));
        btnLuongNV.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        btnLuongNV.setTextSize(20);
        btnLuongNV.setRadius(7);
        btnLuongNV.setIconResource(R.drawable.ic_salary);
        btnLuongNV.setIconPosition(FancyButton.POSITION_LEFT);
        btnLuongNV.setFontIconSize(30);
        btnLuongNV.setIconPadding(0,30,0,0);

        btnTTNV.setText("Quản Lý Nhân Viên");
        btnTTNV.setBackgroundColor(Color.parseColor("#FF3366"));
        btnTTNV.setFocusBackgroundColor(Color.parseColor("#161b23"));
        btnTTNV.setTextSize(20);
        btnTTNV.setRadius(7);
        btnTTNV.setIconPadding(0,30,0,0);
        btnTTNV.setIconResource(R.drawable.ic_web_management);
        btnTTNV.setIconPosition(FancyButton.POSITION_LEFT);
        btnTTNV.setFontIconSize(30);

        btnThongBao.setText("Quản Lý Thông Báo");
        btnThongBao.setBackgroundColor(Color.parseColor("#00CC00"));
        btnThongBao.setFocusBackgroundColor(Color.parseColor("#161b23"));
        btnThongBao.setTextSize(20);
        btnThongBao.setRadius(7);
        btnThongBao.setIconResource(R.drawable.ic_notification);
        btnThongBao.setIconPosition(FancyButton.POSITION_LEFT);
        btnThongBao.setFontIconSize(30);
        btnThongBao.setIconPadding(0,30,0,0);

        btnQuanLyLichNhanVien.setText("Quản Lý Lịch Nhân Viên");
        btnQuanLyLichNhanVien.setBackgroundColor(Color.parseColor("#545b66"));
        btnQuanLyLichNhanVien.setFocusBackgroundColor(Color.parseColor("#081d3d"));
        btnQuanLyLichNhanVien.setTextSize(20);
        btnQuanLyLichNhanVien.setRadius(7);
        btnQuanLyLichNhanVien.setIconResource(R.drawable.ic_time);
        btnQuanLyLichNhanVien.setIconPosition(FancyButton.POSITION_LEFT);
        btnQuanLyLichNhanVien.setFontIconSize(30);
        btnQuanLyLichNhanVien.setIconPadding(0,30,0,0);





        //Click
        btnTTNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyPanel.this, QuanLyThongTinNhanVien.class);
                startActivity(i);
            }
        });
        btnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyPanel.this, QuanLyThongBao.class);
                startActivity(i);

            }
        });
        btnLuongNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyPanel.this, QuanLyLuong.class);
                startActivity(i);
            }
        });
        btnQuanLyLichNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyPanel.this, LichNhanVienPanel.class);
                startActivity(i);
            }
        });





    }

    private void anhxa() {
        FTV=findViewById(R.id.txtTieuDeQuanLy);
        btnLuongNV=findViewById(R.id.btnQuanLyLuongNhanVien);
        btnThongBao=findViewById(R.id.btnQuanLyThongBao);
        btnTTNV=findViewById(R.id.btnQuanLyThongTinNhanVien);
        btnQuanLyLichNhanVien=findViewById(R.id.btnQuanLyLichNhanVien);


        toolbar=findViewById(R.id.toolbarQuanLyPanel);

    }
}
