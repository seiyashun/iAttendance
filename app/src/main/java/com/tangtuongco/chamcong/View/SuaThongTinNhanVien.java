package com.tangtuongco.chamcong.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class SuaThongTinNhanVien extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText edtMaNv,edtHoTen,edtSDT,edtMucLuong,edtEmail,edtNgayVaoLam,edtChucVu;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    String email;
    FancyButton btnSave;
    int mYear, mMonth, mDay;
    Spinner spinner;
    Button btnChonNgay;
    NhanVien nv;
    ArrayList<ChucVu> SpinnerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin_nhan_vien);
        anhxa();
        SpinnerList = new ArrayList<ChucVu>();
        init();

    }

    private void init() {
        //GetEmail
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            email =(String) b.get("email");
            Log.d("kiemtra",email);

        }

        //Toolbar
        toolbar.setTitle("Thông Tin Nhân Viên");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Button
        btnSave.setText("Lưu Thông Tin");
        btnSave.setBackgroundColor(Color.parseColor("#c59783"));
        btnSave.setFocusBackgroundColor(Color.parseColor("#b98068"));
        btnSave.setTextSize(20);
        btnSave.setRadius(7);
        btnSave.setIconPosition(FancyButton.POSITION_LEFT);
        btnSave.setFontIconSize(30);


        getDSCV();
        btnChonNgay.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        nv=new NhanVien();
        readData(new FirebaseCallBack() {
            @Override
            public void onCallBack(NhanVien a) {
                nv=a;
                edtMaNv.setEnabled(false);
                edtEmail.setEnabled(false);
                edtSDT.setText(nv.getSdt());
                edtNgayVaoLam.setText(FormatHelper.formatNgay(nv.getNgayvaolam()));
                edtMaNv.setText(nv.getManv());
                edtMucLuong.setText(String.valueOf(nv.getMucluong()));
                edtEmail.setText(nv.getEmail());
                edtHoTen.setText(nv.getHoten());

            }
        });





    }
    private void readData(final FirebaseCallBack firebaseCallBack)
    {
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("NhanVien");

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NhanVien a = ds.getValue(NhanVien.class);
                    {
                        if(a.getEmail().equals(email))
                        {
                            firebaseCallBack.onCallBack(a);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private interface FirebaseCallBack{
        void onCallBack(NhanVien a);
    }
    private void getDSCV() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("ChucVu");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SaveData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SaveData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            ChucVu a = ds.getValue(ChucVu.class);
            SpinnerList.add(a);
        }
        addSpiner();
    }
    private void addSpiner() {
        ArrayAdapter<ChucVu> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            SaveThongTin();
        } else if (v == btnChonNgay) {

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

                            edtNgayVaoLam.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void anhxa() {
        edtMaNv = findViewById(R.id.edtMaNhanVienSua);
        edtEmail = findViewById(R.id.edtEmailSua);
        edtHoTen = findViewById(R.id.edtHoVaTenSua);
        spinner = findViewById(R.id.spinnerChucVuSua);
        edtNgayVaoLam = findViewById(R.id.edtNgayVaoLamSua);
        btnChonNgay = findViewById(R.id.btnChonDateSua);
        edtSDT = findViewById(R.id.edtSDTSua);
        btnSave = findViewById(R.id.btnSaveThongTinSua);
        edtMucLuong=findViewById(R.id.edtMucLuongSua);
        toolbar = findViewById(R.id.toolbarSuaThongTinNhanVien);
    }



    private void SaveThongTin() {

        nv.setEmail(edtEmail.getText().toString());
        nv.setHoten(edtHoTen.getText().toString());
        nv.setSdt(edtSDT.getText().toString());
        ChucVu chucVu= (ChucVu) spinner.getSelectedItem();
        nv.setChucvu(chucVu.getIdChucVu());
        nv.setMucluong(Double.valueOf(edtMucLuong.getText().toString().trim()));
        try {
            nv.setNgayvaolam(FormatHelper.formatstring(edtNgayVaoLam.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        firebaseDatabase=FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NhanVien sadf= ds.getValue(NhanVien.class);
                    if(sadf.getEmail().equals(nv.getEmail()))
                    {
                        ds.getRef().setValue(nv);
                        Toast.makeText(SuaThongTinNhanVien.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
