package com.tangtuongco.chamcong.View;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;

public class XemThongBao extends AppCompatActivity {
    TextView txtTieuDe,txtNguoiGui,txtNgayGui,txtNoiDung;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        anhxa();
        //toolbar
        toolbar.setTitle("Thông Báo");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //init
        init();



    }

    private void init() {
        ThongBao thongBao= (ThongBao) getIntent().getSerializableExtra("data");
        txtNgayGui.setText(FormatHelper.formatNgay(thongBao.getNotiDate()));
        txtNguoiGui.setText(thongBao.getNotiUser());
        txtTieuDe.setText(thongBao.getNotiTitle());
        txtNoiDung.setText(thongBao.getNotiMess());




    }

    private void anhxa() {
        toolbar=findViewById(R.id.toolbarThongBao);
        txtNgayGui=findViewById(R.id.txtNgayGui);
        txtNguoiGui=findViewById(R.id.txtNguoiGui);
        txtTieuDe=findViewById(R.id.txtTieuDe);
        txtNoiDung=findViewById(R.id.txtNoiDung);
    }
}
