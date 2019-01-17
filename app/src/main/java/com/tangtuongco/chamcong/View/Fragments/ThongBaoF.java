package com.tangtuongco.chamcong.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.View.XemThongBao;
import com.tangtuongco.chamcong.ViewHolder.ThongBaoViewHolder;

public class ThongBaoF extends Fragment {
    RecyclerView listThongBao;
    DatabaseReference dataThongBao;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerOptions<ThongBao> options;
    FirebaseRecyclerAdapter<ThongBao,ThongBaoViewHolder> adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thong_bao,container,false);
        anhxa(view);
        firebaseDatabase=FirebaseDatabase.getInstance();

        init();

        return view;
    }

    private void init() {
        Query query=firebaseDatabase.getReference().child("ThongBao").orderByChild("notiDate").limitToFirst(5);


        listThongBao.setHasFixedSize(true);
        listThongBao.setLayoutManager(new LinearLayoutManager(getActivity()));
        options=new FirebaseRecyclerOptions.Builder<ThongBao>()
                .setQuery(query,ThongBao.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<ThongBao, ThongBaoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position, @NonNull final ThongBao model) {
                holder.txtTitle.setText(model.getNotiTitle());
                holder.txtDate.setText(FormatHelper.formatNgay(model.getNotiDate()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThongBao a = model;
                        Intent intent=new Intent(getActivity(), XemThongBao.class);
                        intent.putExtra("data",a);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_thong_bao,parent,false);
                return new ThongBaoViewHolder(view);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        listThongBao.setLayoutManager(linearLayoutManager);

        adapter.startListening();
        listThongBao.setAdapter(adapter);
    }

    private void anhxa(View view) {
        listThongBao=view.findViewById(R.id.lstThongBao);
    }
}
