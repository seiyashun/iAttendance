package com.tangtuongco.chamcong.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tangtuongco.chamcong.Model.NhanVien;

import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.ChatNhom;
import com.tangtuongco.chamcong.View.ChatRieng;
import com.tangtuongco.chamcong.ViewHolder.BanBeViewHolder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanBeF extends Fragment {
    RecyclerView listViewBanBe;
    DatabaseReference dataNhanVien;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerOptions<NhanVien> options;
    FirebaseRecyclerAdapter<NhanVien,BanBeViewHolder> adapter;
    LinearLayout linearLayout;




    public BanBeF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ban_be, container, false);
        // Inflate the layout for this fragment

        anhxa(v);
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataNhanVien=firebaseDatabase.getReference().child("NhanVien");
        init();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent a = new Intent(getActivity(), ChatNhom.class);
               startActivity(a);
            }
        });






        return v;

    }



    private void init() {
        listViewBanBe.setHasFixedSize(true);
        listViewBanBe.setLayoutManager(new LinearLayoutManager(getActivity()));

        options=new FirebaseRecyclerOptions.Builder<NhanVien>()
                .setQuery(dataNhanVien,NhanVien.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<NhanVien, BanBeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BanBeViewHolder holder, int position, @NonNull final NhanVien model) {
                holder.txtTenBanBe.setText(model.getHoten());
                holder.txtEmailBanBe.setText(model.getEmail());
                Glide.with(getActivity())
                        .load(model.getAva())
                        .into(holder.imaAvaBanBe);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), ChatRieng.class);
                        i.putExtra("receiver_id",model.getManv());
                        startActivity(i);
                    }
                });

            }

            @NonNull
            @Override
            public BanBeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ban_be,parent,false);

                return new BanBeViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        listViewBanBe.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listViewBanBe.setAdapter(adapter);


    }



    @Override
    public void onStart() {
        if(adapter!=null)
        {
            adapter.startListening();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if(adapter!=null)
        {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        if(adapter!=null)
        {
            adapter.startListening();
        }
        super.onResume();
    }

    private void anhxa(View v) {
        listViewBanBe=v.findViewById(R.id.listDanhSachBanBe);
        linearLayout=v.findViewById(R.id.LinearNhom);
    }

}
