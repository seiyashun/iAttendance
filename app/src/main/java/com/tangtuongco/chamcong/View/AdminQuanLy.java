package com.tangtuongco.chamcong.View;

import android.content.Intent;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.ViewHolder.QuanLyViewHolder;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdminQuanLy extends AppCompatActivity {
    RecyclerView listQuanLy;
    FancyButton btnThemQuanLy;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    Toolbar toolbar;
    FirebaseRecyclerOptions<NhanVien> options;
    FirebaseRecyclerAdapter<NhanVien,QuanLyViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_quan_ly);
        anhxa();
        //Toolbar
        toolbar.setTitle("Quản Lý");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        //Init
        init();
        //xuly
        xuly();
    }

    private void xuly() {
        btnThemQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(AdminQuanLy.this,DangKy.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        mData=firebaseDatabase.getReference().child("NhanVien");
        listQuanLy.setHasFixedSize(true);
        listQuanLy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        options = new FirebaseRecyclerOptions.Builder<NhanVien>()
                .setQuery(mData,NhanVien.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<NhanVien, QuanLyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull QuanLyViewHolder holder, int position, @NonNull NhanVien model) {
                holder.txtEmailQly.setText(model.getEmail());
                holder.txtTenQly.setText(model.getHoten());
            }

            @NonNull
            @Override
            public QuanLyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_capbac_quanly,parent,false);
                return  new QuanLyViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        listQuanLy.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listQuanLy.setAdapter(adapter);
    }

    private void anhxa() {
        listQuanLy=findViewById(R.id.listQuanLy);
        toolbar=findViewById(R.id.toolbarAdminQuanLy);
        btnThemQuanLy=findViewById(R.id.btnThemQuanLy);
    }
}
