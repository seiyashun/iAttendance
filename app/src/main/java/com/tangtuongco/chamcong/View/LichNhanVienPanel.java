package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.tangtuongco.chamcong.R;
import com.tomer.fadingtextview.FadingTextView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import mehdi.sakout.fancybuttons.FancyButton;

public class LichNhanVienPanel extends AppCompatActivity {
    FadingTextView FTV;
    Toolbar toolbar;
    FancyButton btnLuu, btnReset;
    TextView txtNgay;
    RecyclerView listNhanVienDangKy,listQuanLyXepLich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_nhan_vien_panel);
        anhxa();
        init();
    }

    private void init() {




        //Toolbar
        toolbar.setTitle("Quản Lý Lịch Làm");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Button
        btnLuu.setText("Lưu");
        btnLuu.setBackgroundColor(Color.parseColor("#4286f4"));
        btnLuu.setFocusBackgroundColor(Color.parseColor("#03070f"));
        btnLuu.setTextSize(15);
        btnLuu.setRadius(5);

        btnReset.setText("Reset");
        btnReset.setBackgroundColor(Color.parseColor("#4286f4"));
        btnReset.setFocusBackgroundColor(Color.parseColor("#03070f"));
        btnReset.setTextSize(15);
        btnReset.setRadius(5);

        //Textview
        String[] texts = {"<--Chọn","<--Ngày"};
        FTV.setTexts(texts);
        FTV.setTimeout(3, TimeUnit.SECONDS);


        final Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        txtNgay.setText(mDay+"/"+mMonth+1);
        txtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });



    }

    private void ChonNgay() {
        final Calendar c = Calendar.getInstance();
        int mYear =c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);




        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String ngay,thang;
                        monthOfYear=monthOfYear+1;
                        ngay=(dayOfMonth<10?"0":"")+dayOfMonth;
                        thang=(monthOfYear<10?"0":"")+monthOfYear;
                        txtNgay.setText(ngay + "/" + (thang));


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void anhxa() {
        FTV=findViewById(R.id.txtClickHere);
        toolbar = findViewById(R.id.toolbarLichNhanVienPanel);
        btnLuu = findViewById(R.id.btnLuuLichLamNhanVienDangKy);
        btnReset = findViewById(R.id.btnResetLichLamNhanVien);
        txtNgay = findViewById(R.id.txtNgayNhanVienDangKy);
        listNhanVienDangKy=findViewById(R.id.listNhanVienDangKy);
        listQuanLyXepLich=findViewById(R.id.listQuanLySapXep);
    }
}
