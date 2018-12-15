package com.tangtuongco.chamcong.View;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.QuanLyViewHolder;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class QuanLyThongTinNhanVien extends AppCompatActivity {
    RecyclerView listNhanVien;
    Toolbar toolbar;
    FancyButton btnThem;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerOptions<NhanVien> options;
    FirebaseRecyclerAdapter<NhanVien, QuanLyViewHolder> adapter;
    ArrayList<ChucVu> arrayListChucVu;
    String emailNv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thong_tin_nhan_vien);

        anhxa();
        arrayListChucVu=new ArrayList<ChucVu>();
        //toolbar
        //Toolbar
        progressDialog=new ProgressDialog(this);
        toolbar.setTitle("Quản Lý Nhân Viên");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //data

        firebaseDatabase = FirebaseDatabase.getInstance();
        getChucVu();
        //Init

        //xuly
        xuly();

    }
    private void xuly() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuanLyThongTinNhanVien.this, DangKy.class);
                startActivity(i);
            }
        });
    }

    private void anhxa() {
        toolbar=findViewById(R.id.toolbarQuanLyNhanVien);
        listNhanVien = findViewById(R.id.listQuanLyNhanVien);
        btnThem = findViewById(R.id.btnThemNhanVien);
    }

    private void getChucVu() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
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
        arrayListChucVu = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            ChucVu a = ds.getValue(ChucVu.class);
            arrayListChucVu.add(a);
        }
        init();

    }


    private void init() {
        mData = firebaseDatabase.getReference().child("NhanVien");
        listNhanVien.setHasFixedSize(true);
        listNhanVien.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        options = new FirebaseRecyclerOptions.Builder<NhanVien>()
                .setQuery(mData, NhanVien.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<NhanVien, QuanLyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final QuanLyViewHolder holder, int position, @NonNull final NhanVien model) {
                holder.txtEmailQly.setText(model.getEmail());
                holder.txtTenQly.setText(model.getHoten());
                for (int i = 0; i < arrayListChucVu.size(); i++) {
                    if (arrayListChucVu.get(i).getIdChucVu().equals(model.getChucvu())) {
                        holder.txtChucVu.setText(arrayListChucVu.get(i).getTenChucVu());
                    }
                }
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
//                        if(model.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
//                        {
//                            Toasty.warning(QuanLyThongTinNhanVien.this, "Đây là bạn!!!", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }
//                        else
//                        {
//                            saveMaNV(holder.txtEmailQly.getText().toString());
//                            registerForContextMenu(v);
//                            return false;
//                        }
                        saveMaNV(holder.txtEmailQly.getText().toString());
                        registerForContextMenu(v);
                        return false;
                    }
                });

            }

            @NonNull
            @Override
            public QuanLyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_capbac_quanly, parent, false);
                return new QuanLyViewHolder(view);
            }

        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        listNhanVien.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listNhanVien.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private void saveMaNV(String s) {
        emailNv = s;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Lựa chọn");
        getMenuInflater().inflate(R.menu.floatingmenuquanlynhanvien, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionXoaNV:

                deleteUSer();
                return true;
            case R.id.optionSuaNV:
                editUser();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    private void editUser() {
        Intent i = new Intent(QuanLyThongTinNhanVien.this, SuaThongTinNhanVien.class);

        i.putExtra("email",emailNv);
        startActivity(i);


    }


    private void deleteUSer() {

        mData = firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("kiemtra", emailNv);
                    NhanVien a = ds.getValue(NhanVien.class);
                    if (a.email.equals(emailNv)) {
                        ds.getRef().removeValue();
                        Toasty.normal(QuanLyThongTinNhanVien.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
