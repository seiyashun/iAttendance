package com.tangtuongco.chamcong.View.Fragments;


import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.tangtuongco.chamcong.Model.GioCong;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.Ulty.TinhThoiGian;
import com.tangtuongco.chamcong.ViewHolder.GioCongViewHolder;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheoDoiF extends Fragment {
    Spinner spinerThang;
    FancyButton btnTinhTong;
    RecyclerView listLuong;
    TextView txtLuong;
    ArrayList<String> listSpinner;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    ArrayList<ChucVu> listChucVu;
    FirebaseRecyclerOptions<GioCong> options;
    FirebaseRecyclerAdapter<GioCong, GioCongViewHolder> adapter;
    String email;
    ProgressDialog progressDialog;
    NhanVien currentNv;
    ArrayList<String> listGioCong;
    double hesoluong;

    public TheoDoiF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_theo_doi, container, false);
        anhxa(v);
        init();
        //Data
        firebaseDatabase=FirebaseDatabase.getInstance();
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getData();
        getChucVu();
        progressDialog = new ProgressDialog(getActivity());
        btnTinhTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinhtong();
            }
        });





        // Inflate the layout for this fragment
        return v;
    }

    private void tinhtong() {

        double money=0;
        for(int i=0;i<listGioCong.size();i++)
        {
           if(listGioCong.get(i).length()==9)
           {
               money=money+Double.valueOf(listGioCong.get(i).substring(0,3));
           }
           else
           {
               money=money+Double.valueOf(listGioCong.get(i).substring(0,2));
           }
        }
        String giatien;
        for(int i=0;i<listChucVu.size();i++)
        {
            if(listChucVu.get(i).getIdChucVu().equals(currentNv.getChucvu()))
            {
                hesoluong= listChucVu.get(i).getHesoluong();
                money=(money*hesoluong*currentNv.getMucluong())/1000;
            }
        }
        DecimalFormat format = new DecimalFormat("0.#");


        giatien= String.valueOf(format.format(money))+ "000 VND";
        txtLuong.setText(giatien);
        money=0;


    }

    private void getData() {
        mData=firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
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
    }

    private void getChucVu() {
        mData=firebaseDatabase.getReference().child("ChucVu");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saveChucVu(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getGioCong(int thang)
    {
        listGioCong=new ArrayList<String>();
        mData=firebaseDatabase.getReference().child("GioCong").child(currentNv.getManv()).child(String.valueOf(thang));
        listLuong.setHasFixedSize(true);
        listLuong.setLayoutManager(new LinearLayoutManager(getActivity()));

        options = new FirebaseRecyclerOptions.Builder<GioCong>()
                .setQuery(mData, GioCong.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<GioCong, GioCongViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GioCongViewHolder holder, int position, @NonNull GioCong model) {
                holder.txtGioVao.setText(model.getGioVao());
                holder.txtGioRa.setText(model.getGioRa());
                holder.txtNgay.setText(model.getNgay());
                try {
                    String gio = TinhThoiGian.GioRaTruGioVao(model.getGioVao(),model.getGioRa());
                    holder.txtGioThucTe.setText(gio);
                    listGioCong.add(gio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }

            @NonNull
            @Override
            public GioCongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_giocong, parent, false);

                return new GioCongViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        listLuong.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listLuong.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private void saveChucVu(DataSnapshot dataSnapshot) {
        listChucVu=new ArrayList<ChucVu>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            ChucVu a = ds.getValue(ChucVu.class);
           listChucVu.add(a);
        }
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerThang.setAdapter(adapter);
        spinerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                getGioCong(position);
                txtLuong.setText("");



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void anhxa(View v) {
        spinerThang=v.findViewById(R.id.spinnerTheoDoi);
        btnTinhTong=v.findViewById(R.id.btnTinhLuong);
        listLuong=v.findViewById(R.id.listChamCong);
        txtLuong=v.findViewById(R.id.txtTheoDoiSoTienLuong);
    }

}
