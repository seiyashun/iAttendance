package com.tangtuongco.chamcong.View;

import android.app.ProgressDialog;
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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.GioCong;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.TinhThoiGian;
import com.tangtuongco.chamcong.View.Fragments.SuaGioCongDialog;
import com.tangtuongco.chamcong.ViewHolder.GioCongViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class QuanLyLuongMotNguoi extends AppCompatActivity {
    Spinner spinerThang,spinnerNam;
    FancyButton btnThemGioCong;
    RecyclerView listLuong;
    TextView txtLuong,txtSoGioCong;
    ArrayList<String> listSpinner,listNam;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    ArrayList<ChucVu> listChucVu;
    Toolbar toolbar;
    FirebaseRecyclerOptions<GioCong> options;
    FirebaseRecyclerAdapter<GioCong, GioCongViewHolder> adapter;
    String manv;
    ProgressDialog progressDialog;
    NhanVien currentNv;
    ArrayList<String> listGioCong;
    double hesoluong;
    String ngaytxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_luong_mot_nguoi);
        anhxa();
        //Toolbar
        toolbar.setTitle("Lương");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

    }

    private void init() {
        listSpinner = new ArrayList<>();
        listSpinner.add("Tháng 1");
        listSpinner.add("Tháng 2");
        listSpinner.add("Tháng 3");
        listSpinner.add("Tháng 4");
        listSpinner.add("Tháng 5");
        listSpinner.add("Tháng 6");
        listSpinner.add("Tháng 7");
        listSpinner.add("Tháng 8");
        listSpinner.add("Tháng 9");
        listSpinner.add("Tháng 10");
        listSpinner.add("Tháng 11");
        listSpinner.add("Tháng 12");

        btnThemGioCong.setText("Thêm Giờ Công");
        btnThemGioCong.setBackgroundColor(Color.parseColor("#c59783"));
        btnThemGioCong.setFocusBackgroundColor(Color.parseColor("#c59783"));
        btnThemGioCong.setTextSize(20);
        btnThemGioCong.setRadius(7);
        btnThemGioCong.setIconPadding(0,30,0,0);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            manv=(String) b.get("id");


        }

        //Spiiner Nam
        //Spinner Nam
        getNam();
        ArrayAdapter<String> adapterNam = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listNam);
        adapterNam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNam.setAdapter(adapterNam);
        spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listLuong.setAdapter(null);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerThang.setAdapter(adapter);
        spinerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGioCong(position);




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void getNam()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int year1=year-1;
        listNam=new ArrayList<>();
        listNam.add(String.valueOf(year));
        listNam.add(String.valueOf(year1));
    }


    private void getGioCong(int position) {

        firebaseDatabase=FirebaseDatabase.getInstance();
        String nam= (String) spinnerNam.getSelectedItem();
        mData=firebaseDatabase.getReference().child("GioCong").child(manv).child(nam).child(String.valueOf(position));
        listLuong.setHasFixedSize(true);
        listLuong.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<GioCong>()
                .setQuery(mData, GioCong.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<GioCong, GioCongViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GioCongViewHolder holder, int position, @NonNull final GioCong model) {
                holder.txtGioVao.setText(model.getGioVao());
                holder.txtGioRa.setText(model.getGioRa());
                holder.txtNgay.setText(model.getNgay());
                try {
                    String gio = TinhThoiGian.GioRaTruGioVao(model.getGioVao(),model.getGioRa());
                    holder.txtGioThucTe.setText(gio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        registerForContextMenu(v);
                        savetxtNgay(model.getNgay());
                        return false;
                    }
                });




            }

            @NonNull
            @Override
            public GioCongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_giocong, parent, false);

                return new GioCongViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        listLuong.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listLuong.setAdapter(adapter);
    }

    private void savetxtNgay(String ngay) {
        ngaytxt=ngay+manv;

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Lựa chọn");
        getMenuInflater().inflate(R.menu.floatingmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionXoa:

                Toasty.warning(QuanLyLuongMotNguoi.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionSua:
                EditGioCong();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void EditGioCong() {
        SuaGioCongDialog suaGioCongDialog = new SuaGioCongDialog().newIn(ngaytxt);
        suaGioCongDialog.show(getSupportFragmentManager(), "Sửa Giờ Công");
    }

    private void anhxa() {
        spinnerNam=findViewById(R.id.spinnerNam1);
        toolbar=findViewById(R.id.toolbarQuanLyLuong1);
        spinerThang=findViewById(R.id.spinnerTheoDoi1);
        btnThemGioCong=findViewById(R.id.btnThemGioCong);
        listLuong=findViewById(R.id.listChamCong1);
    }
}
