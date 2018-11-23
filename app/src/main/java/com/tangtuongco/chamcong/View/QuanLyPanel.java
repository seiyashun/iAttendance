package com.tangtuongco.chamcong.View;

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
    FancyButton btnTTNV,btnLuongNV,btnThongBao,btnXemLuong;
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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
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
        btnLuongNV.setText("Quản Lý Lương Nhân Viên");
        btnLuongNV.setBackgroundColor(Color.parseColor("#3b5998"));
        btnLuongNV.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        btnLuongNV.setTextSize(20);
        btnLuongNV.setRadius(7);
        btnLuongNV.setIconResource(R.drawable.ic_salary);
        btnLuongNV.setIconPosition(FancyButton.POSITION_LEFT);
        btnLuongNV.setFontIconSize(30);

        btnTTNV.setText("Quản Lý Thông Tin Nhân Viên");
        btnTTNV.setBackgroundColor(Color.parseColor("#FF3366"));
        btnTTNV.setFocusBackgroundColor(Color.parseColor("#FF3399"));
        btnTTNV.setTextSize(20);
        btnTTNV.setRadius(7);
        btnTTNV.setIconResource(R.drawable.ic_web_management);
        btnTTNV.setIconPosition(FancyButton.POSITION_LEFT);
        btnTTNV.setFontIconSize(30);

        btnThongBao.setText("Quản Lý Thông Báo");
        btnThongBao.setBackgroundColor(Color.parseColor("#00CC00"));
        btnThongBao.setFocusBackgroundColor(Color.parseColor("#00DD00"));
        btnThongBao.setTextSize(20);
        btnThongBao.setRadius(7);
        btnThongBao.setIconResource(R.drawable.ic_notification);
        btnThongBao.setIconPosition(FancyButton.POSITION_LEFT);
        btnThongBao.setFontIconSize(30);

        btnXemLuong.setText("Kiểm Tra Lương");
        btnXemLuong.setBackgroundColor(Color.parseColor("#ECAB53"));
        btnXemLuong.setFocusBackgroundColor(Color.parseColor("#DDC488"));
        btnXemLuong.setTextSize(20);
        btnXemLuong.setRadius(7);
        btnXemLuong.setIconResource(R.drawable.ic_money);
        btnXemLuong.setIconPosition(FancyButton.POSITION_LEFT);
        btnXemLuong.setFontIconSize(30);





    }

    private void anhxa() {
        FTV=findViewById(R.id.txtTieuDeQuanLy);
        btnLuongNV=findViewById(R.id.btnQuanLyLuongNhanVien);
        btnThongBao=findViewById(R.id.btnQuanLyThongBao);
        btnTTNV=findViewById(R.id.btnQuanLyThongTinNhanVien);
        btnXemLuong=findViewById(R.id.btnXemTienLuong);

        toolbar=findViewById(R.id.toolbarQuanLyPanel);

    }
}
