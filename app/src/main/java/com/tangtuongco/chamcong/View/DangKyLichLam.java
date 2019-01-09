package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.LichLam;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.CheckEditext;
import com.tangtuongco.chamcong.ViewHolder.GioCongViewHolder;
import com.tangtuongco.chamcong.ViewHolder.LichLamViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class DangKyLichLam extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView listLichLam;
    FancyButton btnLuuLichLam;
    EditText edtNgayLam;
    Spinner spinnerCaLam;
    TextView txtHuongDan;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    FirebaseRecyclerOptions<LichLam> options;
    FirebaseRecyclerAdapter<LichLam, LichLamViewHolder> adapter;
    String MaNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_lich_lam);
        firebaseDatabase=FirebaseDatabase.getInstance();
        anhxa();
        init();
        getLichLam();

    }

    private void getLichLam() {
        Calendar now = Calendar.getInstance();
        int month=(now.get(Calendar.MONTH)+1);
        String thang=(month<10?"0":"")+month;

        mData=firebaseDatabase.getReference().child("LichLam").child("LichTheoThang").child(MaNV).child(thang);
        listLichLam.setHasFixedSize(true);
        listLichLam.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        options = new FirebaseRecyclerOptions.Builder<LichLam>()
                .setQuery(mData, LichLam.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<LichLam, LichLamViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull LichLamViewHolder holder, int position, @NonNull LichLam model) {
                holder.txtNgay.setText(model.getNgayLam());
                holder.txtCa.setText(model.getCaLam());
                Log.d("kiemtra",model.getCaLam());

            }

            @NonNull
            @Override
            public LichLamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lichlam, parent, false);

                return new LichLamViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        listLichLam.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listLichLam.setAdapter(adapter);



    }

    private void init() {
        //Get MaNV;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            MaNV=bundle.getString("MaNV");
        }


        //Toolbar
        toolbar.setTitle("Đăng Ký Lịch Làm");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Textview
        txtHuongDan.setText("*Nhấp vào ô Ngày Làm để chọn ngày làm \n" +
                " *Chọn ca làm việc\n" +
                "    +A:08:00->15:00\n" +
                "    +B:15:00->23:00\n" +
                "    +C:12:00->18:00\n" +
                "    +D:09:00->15:00\n" +
                "    +E:18:00->23:00\n" +
                "*Sau đó bấm nút Lưu");
        edtNgayLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        //Button
        btnLuuLichLam.setText("Lưu");
        btnLuuLichLam.setBackgroundColor(Color.parseColor("#3b5998"));
        btnLuuLichLam.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        btnLuuLichLam.setTextSize(15);
        btnLuuLichLam.setRadius(5);
        btnLuuLichLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKyLich();
            }
        });


        //Spinner
        String[] arraySpinner = new String[]{"A", "B", "C", "D", "E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCaLam.setAdapter(adapter);

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
                        edtNgayLam.setText(ngay + "/" + (thang));


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        edtNgayLam.setFocusable(false);

}


    private void DangKyLich() {
        String ngaylam = edtNgayLam.getText().toString().trim();
        LichLam a = new LichLam();
        a.setCaLam(spinnerCaLam.getSelectedItem().toString());
        a.setMaNV(MaNV);
        a.setNgayLam(ngaylam);
        ngaylam=ngaylam.replace("/","");
        String ngay=ngaylam.substring(0,2);
        String thang=ngaylam.substring(2,4);

        mData=firebaseDatabase.getReference().child("LichLam").child("LichDangKy").child(thang).child(ngay).child(MaNV);
        mData.setValue(a);
        addLichTheoThang(a,ngaylam);
        //Log.d("kiemtra",spinnerCaLam.getSelectedItem().toString()+"");



    }

    private void addLichTheoThang(LichLam a, String ngaylam) {
        String ngay=ngaylam.substring(0,2);
        String thang=ngaylam.substring(2,4);
        mData=firebaseDatabase.getReference().child("LichLam").child("LichTheoThang").child(MaNV).child(thang).child(ngay);
        mData.setValue(a);
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarDangKyLichLam);
        listLichLam = findViewById(R.id.listLichLamDangKy);
        btnLuuLichLam = findViewById(R.id.btnLuuLichLam);
        txtHuongDan = findViewById(R.id.txtHuongDan);
        edtNgayLam = findViewById(R.id.edtNgayLam);
        spinnerCaLam = findViewById(R.id.spinnerLichLam);
    }

}
