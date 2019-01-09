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
import android.view.LayoutInflater;
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

public class QuanLyLuong extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView listQuanLyNhanVienLuong;
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
        setContentView(R.layout.activity_quan_ly_luong);
        anhxa();
        //Data
        progressDialog=new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        getChucVu();
    }

    private void init() {
        //Toolbar + ProgressDialog

        toolbar.setTitle("Danh Sách Nhân Viên");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_backv2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mData = firebaseDatabase.getReference().child("NhanVien");
        listQuanLyNhanVienLuong.setHasFixedSize(true);
        listQuanLyNhanVienLuong.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(QuanLyLuong.this, QuanLyLuongMotNguoi.class);

                        i.putExtra("id",model.getManv());
                        startActivity(i);
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
        listQuanLyNhanVienLuong.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listQuanLyNhanVienLuong.setAdapter(adapter);
        progressDialog.dismiss();



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



    private void anhxa() {
        toolbar = findViewById(R.id.toolbarQuanLyNhanVienLuong);
        listQuanLyNhanVienLuong = findViewById(R.id.listQuanLyNhanVienLuong);
    }
}
