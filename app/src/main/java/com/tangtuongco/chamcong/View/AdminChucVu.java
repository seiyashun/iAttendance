package com.tangtuongco.chamcong.View;

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
import android.widget.Button;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.Model.ThongBao;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.View.Fragments.SuaChucVuDialog;
import com.tangtuongco.chamcong.View.Fragments.ThemChucVuDialog;
import com.tangtuongco.chamcong.ViewHolder.ChucVuViewHolder;
import com.tangtuongco.chamcong.ViewHolder.ThongBaoViewHolder;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class AdminChucVu extends AppCompatActivity {
    RecyclerView listChucVu;
    FancyButton btnThemChucVu;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerOptions<ChucVu> options;
    FirebaseRecyclerAdapter<ChucVu, ChucVuViewHolder> adapter;
    Toolbar toolbar;
    String maCV;
    NhanVien nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chuc_vu);
        anhxa();
        //Toolbar
        toolbar.setTitle("Chức Vụ");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Data

        //Init
        init();
        //Add Chuc Vu
        btnThemChucVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        ThemChucVuDialog themChucVuDialog = new ThemChucVuDialog();
        themChucVuDialog.show(getSupportFragmentManager(), "Thêm Chức Vụ");
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("ChucVu");
        listChucVu.setHasFixedSize(true);
        listChucVu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        options = new FirebaseRecyclerOptions.Builder<ChucVu>()
                .setQuery(mData, ChucVu.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<ChucVu, ChucVuViewHolder>(options) {


            @NonNull
            @Override
            public ChucVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chuc_vu_admin, parent, false);
                return new ChucVuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ChucVuViewHolder holder, int position, @NonNull ChucVu model) {
                holder.txtID.setText(model.getIdChucVu());
                holder.txtHeSoLuong.setText(String.valueOf(model.getHesoluong()));
                holder.txtName.setText(model.getTenChucVu());

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        registerForContextMenu(v);
                        saveMaCV(holder.txtID.getText().toString());
                        return false;
                    }
                });

            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        listChucVu.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listChucVu.setAdapter(adapter);

    }

    private void saveMaCV(String idChucVu) {
        maCV = idChucVu;
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
                kiemtraUserTonTai();

                return true;
            case R.id.optionSua:
                EditChucVu();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    private void EditChucVu() {
        SuaChucVuDialog suaChucVuDialog = new SuaChucVuDialog().newIn(maCV);
        suaChucVuDialog.show(getSupportFragmentManager(), "Sửa Chức Vụ");
    }

    private void anhxa() {
        listChucVu = findViewById(R.id.listChucVu);
        btnThemChucVu = findViewById(R.id.btnThemChucVu);
        toolbar = findViewById(R.id.toolbarAdminChucVu);
    }

    private void deleteUserData(String maCV) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("ChucVu");
        mData.child(maCV).removeValue();
        Toasty.warning(AdminChucVu.this, "Xoá thành công", Toast.LENGTH_SHORT).show();




    }
    private void kiemtraUserTonTai()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference().child("NhanVien");
        final int[] count = {0};

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    NhanVien a= ds.getValue(NhanVien.class);
                    {
                        if(a.getChucvu().equals(maCV))
                        {
                            Toasty.warning(getApplicationContext(), "Tồn tại nhân viên!!!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {
                            count[0]++;
                        }
                        Log.d("kiemtra", count[0] +" count");
                        Log.d("kiemtra",dataSnapshot.getChildrenCount()+"ds");
                        if(count[0] ==dataSnapshot.getChildrenCount())
                        {
                            Boolean test=false;
                            Log.d("kiemtra", String.valueOf((test=count[0]==ds.getChildrenCount())));
                            deleteUserData(maCV);
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}
