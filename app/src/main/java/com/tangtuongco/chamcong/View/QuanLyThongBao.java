package com.tangtuongco.chamcong.View;

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
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.View.Fragments.ThemThongBaoDialog;
import com.tangtuongco.chamcong.ViewHolder.ThongBaoViewHolder;

import mehdi.sakout.fancybuttons.FancyButton;

public class QuanLyThongBao extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView listQuanLyThongBao;
    FancyButton btnThemThongBao;
    DatabaseReference dataThongBao;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerOptions<ThongBao> options;
    FirebaseRecyclerAdapter<ThongBao,ThongBaoViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thong_bao);

        anhxa();
        //toolbar
        toolbar.setTitle("Quản Lý Thông Báo");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataThongBao=firebaseDatabase.getReference().child("ThongBao");
        init();


    }

    private void openDialog() {
        ThemThongBaoDialog themThongBaoDialog = new ThemThongBaoDialog();
        themThongBaoDialog.show(getSupportFragmentManager(), "Thêm Thông Báo");
    }
    private void init() {
        listQuanLyThongBao.setHasFixedSize(true);
        listQuanLyThongBao.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        options=new FirebaseRecyclerOptions.Builder<ThongBao>()
                .setQuery(dataThongBao,ThongBao.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<ThongBao, ThongBaoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position, @NonNull ThongBao model) {
                holder.txtTitle.setText(model.getNotiTitle());
                holder.txtDate.setText(FormatHelper.formatNgay(model.getNotiDate()));
            }

            @NonNull
            @Override
            public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_thong_bao,parent,false);
                return new ThongBaoViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        listQuanLyThongBao.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listQuanLyThongBao.setAdapter(adapter);


        //Click
        btnThemThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void anhxa() {
        toolbar=findViewById(R.id.toolbarQuanLyThongBao);
        listQuanLyThongBao=findViewById(R.id.listQuanLyThongBao);
        btnThemThongBao=findViewById(R.id.btnThemThongBao);
    }
}
