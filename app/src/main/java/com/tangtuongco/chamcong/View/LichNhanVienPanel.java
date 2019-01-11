package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.LichLam;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.LichDangKyViewHolder;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class LichNhanVienPanel extends AppCompatActivity {
    FadingTextView FTV;
    Toolbar toolbar;
    FancyButton btnLuu, btnReset;
    TextView txtNgay;
    RecyclerView listNhanVienDangKy, listQuanLyXepLich;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    FirebaseRecyclerOptions<LichLam> options;
    FirebaseRecyclerAdapter<LichLam, LichDangKyViewHolder> adapter;
    List<LichLam> listSapXep = new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_nhan_vien_panel);
        anhxa();
        init();
    }

    private void readData(final FirebaseCallBack firebaseCallBack) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("NhanVien");
        final List<NhanVien> list = new ArrayList<>();
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NhanVien a = ds.getValue(NhanVien.class);

                    list.add(a);
                    firebaseCallBack.onCallBack(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallBack {
        void onCallBack(List<NhanVien> listNhanVien);
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
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getApplicationContext(), "Đã lưu lịch", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnReset.setText("Reset");
        btnReset.setBackgroundColor(Color.parseColor("#4286f4"));
        btnReset.setFocusBackgroundColor(Color.parseColor("#03070f"));
        btnReset.setTextSize(15);
        btnReset.setRadius(5);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaLichChinhThucQuanLy();
                XoaLichChinhThucNhanVien();

            }
        });

        //Textview
        String[] texts = {"<--Chọn", "<--Ngày"};
        FTV.setTexts(texts);
        FTV.setTimeout(3, TimeUnit.SECONDS);


        final Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        txtNgay.setText(mDay + "/" + mMonth + 1);
        txtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });


    }

    private void XoaLichChinhThucNhanVien() {
        String fullngay = txtNgay.getText().toString();
        fullngay = fullngay.replace("/", "");
        final String ngay = fullngay.substring(0, 2);
        final String thang = fullngay.substring(2, 4);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThucNhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {





                    ds.getRef().child(thang).child(ngay).setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void XoaLichChinhThucQuanLy() {
        String fullngay = txtNgay.getText().toString();
        fullngay = fullngay.replace("/", "");
        String ngay = fullngay.substring(0, 2);
        String thang = fullngay.substring(2, 4);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThuc").child(thang).child(ngay);
        mData.setValue(null);
    }

    private void ChonNgay() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String ngay, thang;
                        monthOfYear = monthOfYear + 1;
                        ngay = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                        thang = (monthOfYear < 10 ? "0" : "") + monthOfYear;
                        txtNgay.setText(ngay + "/" + (thang));
                        //Load List
                        readData(new FirebaseCallBack() {
                            @Override
                            public void onCallBack(final List<NhanVien> listNhanVien) {
                                String ngayfull = txtNgay.getText().toString();
                                ngayfull = ngayfull.replace("/", "");
                                String ngay = ngayfull.substring(0, 2);
                                String thang = ngayfull.substring(2, 4);
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                mData = firebaseDatabase.getReference().child("LichLam").child("LichDangKy").child(thang).child(ngay);
                                Query query = mData.orderByChild("caLam");
                                listNhanVienDangKy.setHasFixedSize(true);
                                listNhanVienDangKy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                options = new FirebaseRecyclerOptions.Builder<LichLam>()
                                        .setQuery(query, LichLam.class)
                                        .build();
                                adapter = new FirebaseRecyclerAdapter<LichLam, LichDangKyViewHolder>(options) {
                                    @Override
                                    protected void onBindViewHolder(@NonNull LichDangKyViewHolder holder, int position, @NonNull final LichLam model) {
                                        holder.txtCa.setText(model.getCaLam());
                                        for (int i = 0; i < listNhanVien.size(); i++) {
                                            if (listNhanVien.get(i).getManv().equals(model.getMaNV())) {
                                                holder.txtTen.setText(listNhanVien.get(i).getHoten());
                                            }
                                        }
                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                addListSapXepQuanLy(model);
                                            }
                                        });
                                    }

                                    @NonNull
                                    @Override
                                    public LichDangKyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nhan_vien_dang_ky,
                                                parent, false);

                                        return new LichDangKyViewHolder(view);
                                    }
                                };
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                listNhanVienDangKy.setLayoutManager(gridLayoutManager);
                                adapter.startListening();
                                listNhanVienDangKy.setAdapter(adapter);
                            }
                        });
                        readData(new FirebaseCallBack() {
                            @Override
                            public void onCallBack(final List<NhanVien> listNhanVien) {
                                String ngayfull = txtNgay.getText().toString();
                                ngayfull = ngayfull.replace("/", "");
                                String ngay = ngayfull.substring(0, 2);
                                String thang = ngayfull.substring(2, 4);
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThuc").child(thang).child(ngay);
                                Query query = mData.orderByChild("caLam");
                                listQuanLyXepLich.setHasFixedSize(true);
                                listQuanLyXepLich.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                options = new FirebaseRecyclerOptions.Builder<LichLam>()
                                        .setQuery(query, LichLam.class)
                                        .build();
                                adapter = new FirebaseRecyclerAdapter<LichLam, LichDangKyViewHolder>(options) {
                                    @Override
                                    protected void onBindViewHolder(@NonNull LichDangKyViewHolder holder, int position, @NonNull final LichLam model) {
                                        holder.txtCa.setText(model.getCaLam());
                                        for (int i = 0; i < listNhanVien.size(); i++) {
                                            if (listNhanVien.get(i).getManv().equals(model.getMaNV())) {
                                                holder.txtTen.setText(listNhanVien.get(i).getHoten());
                                            }
                                        }
                                    }

                                    @NonNull
                                    @Override
                                    public LichDangKyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nhan_vien_dang_ky,
                                                parent, false);

                                        return new LichDangKyViewHolder(view);
                                    }
                                };
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                listQuanLyXepLich.setLayoutManager(gridLayoutManager);
                                adapter.startListening();
                                listQuanLyXepLich.setAdapter(adapter);

                            }
                        });


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void addListSapXepQuanLy(final LichLam model) {
//        int count=0;
//        if(listSapXep.size()==0)
//        {
//            listSapXep.add(model);
//            firebaseDatabase=FirebaseDatabase.getInstance();
//            String fullngay=model.getNgayLam();
//            fullngay=fullngay.replace("/","");
//            String ngay=fullngay.substring(0,2);
//            String thang=fullngay.substring(2,4);
//            mData=firebaseDatabase.getReference().child("LichLam").child("LichChinhThuc").child(thang).child(ngay)
//                    .child(model.getMaNV());
//            mData.setValue(model);
//        }
//        else
//        {
//            for(int i=0;i<listSapXep.size();i++)
//            {
//                if(listSapXep.get(i).equals(model))
//                {
//                    Toasty.info(this,"Đã có nhân viên này trong danh sách", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                else
//                {
//                    count++;
//                }
//            }
//            if(count==listSapXep.size())
//            {
//                listSapXep.add(model);
//                firebaseDatabase=FirebaseDatabase.getInstance();
//                String fullngay=model.getNgayLam();
//                fullngay=fullngay.replace("/","");
//                String ngay=fullngay.substring(0,2);
//                String thang=fullngay.substring(2,4);
//                mData=firebaseDatabase.getReference().child("LichLam").child("LichChinhThuc").child(thang).child(ngay)
//                        .child(model.getMaNV());
//                mData.setValue(model);
//            }
//
//        }

        String fullngay = model.getNgayLam();
        fullngay = fullngay.replace("/", "");
        String ngay = fullngay.substring(0, 2);
        String thang = fullngay.substring(2, 4);
        firebaseDatabase = FirebaseDatabase.getInstance();

        mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThuc").child(thang).child(ngay);

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LichLam a = ds.getValue(LichLam.class);
                    if (a.getMaNV().equals(model.getMaNV())) {
                        break;
                    } else {
                        count++;
                    }
                }
                if (count == dataSnapshot.getChildrenCount()) {
                    mData.child(model.getMaNV()).setValue(model);
                    addLichLamTheoNhanVien(model);

                } else {
                    Toasty.info(getApplicationContext(), "Đã có nhân viên này trong danh sách", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //LoadListDangKy


    }

    private void addLichLamTheoNhanVien(LichLam model) {
        String ngayfull = txtNgay.getText().toString();
        ngayfull = ngayfull.replace("/", "");
        String ngay = ngayfull.substring(0, 2);
        String thang = ngayfull.substring(2, 4);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThucNhanVien").child(model.getMaNV()).child(thang).child(ngay);
        mData.setValue(model);
    }


    private void anhxa() {
        FTV = findViewById(R.id.txtClickHere);
        toolbar = findViewById(R.id.toolbarLichNhanVienPanel);
        btnLuu = findViewById(R.id.btnLuuLichLamNhanVienDangKy);
        btnReset = findViewById(R.id.btnResetLichLamNhanVien);
        txtNgay = findViewById(R.id.txtNgayNhanVienDangKy);
        listNhanVienDangKy = findViewById(R.id.listNhanVienDangKy);
        listQuanLyXepLich = findViewById(R.id.listQuanLySapXep);
    }
}
