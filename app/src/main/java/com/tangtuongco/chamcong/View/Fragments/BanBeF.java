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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.NhanVien;

import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.ChatNhom;
import com.tangtuongco.chamcong.View.ChatRieng;
import com.tangtuongco.chamcong.ViewHolder.BanBeViewHolder;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

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
    CircleImageView imgAvaNhom;
    String email;
    NhanVien currentNv;




    public BanBeF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_ban_be, container, false);
        // Inflate the layout for this fragment

        anhxa(v);
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataNhanVien=firebaseDatabase.getReference().child("NhanVien");
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getData();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent a = new Intent(getActivity(), ChatNhom.class);
               startActivity(a);
            }
        });






        return v;

    }

    private void getData() {
        dataNhanVien = firebaseDatabase.getReference().child("NhanVien");
        dataNhanVien.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    NhanVien a = dataSnapshot1.getValue(NhanVien.class);
                    if(a.getEmail().equals(email))
                    {
                        saveNV(a);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveNV(NhanVien a) {
        currentNv=a;
        init();
    }


    private void init() {
        Glide.with(getActivity())
                .load("https://firebasestorage.googleapis.com/v0/b/chamcong-61010.appspot.com/o/coffeetek.png?alt=media&token=9a8a287a-a7a9-41fa-b776-b9ace3100020")
                .into(imgAvaNhom);
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
                        if(model.getManv().equals(currentNv.getManv()))
                        {
                            Toasty.warning(getActivity(), "Đây là bạn", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent i = new Intent(getActivity(), ChatRieng.class);
                            i.putExtra("receiver_id",model.getManv());
                            startActivity(i);
                        }

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
        imgAvaNhom=v.findViewById(R.id.avaChatNhom);
    }

}
