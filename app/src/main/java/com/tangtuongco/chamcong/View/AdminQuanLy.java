package com.tangtuongco.chamcong.View;

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
import java.util.Currency;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class AdminQuanLy extends AppCompatActivity {
    RecyclerView listQuanLy;
    FancyButton btnThemQuanLy;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;
    Toolbar toolbar;
    FirebaseRecyclerOptions<NhanVien> options;
    FirebaseRecyclerAdapter<NhanVien, QuanLyViewHolder> adapter;
    ArrayList<ChucVu> arrayListChucVu;
    String emailNv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_quan_ly);
        anhxa();
        arrayListChucVu=new ArrayList<ChucVu>();
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        getChucVu();
        //Init
        init();
        //xuly
        xuly();

    }

    private void getChucVu() {
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

    }

    private void xuly() {
        btnThemQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminQuanLy.this, DangKy.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        emailNv=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mData = firebaseDatabase.getReference().child("NhanVien");
        listQuanLy.setHasFixedSize(true);
        listQuanLy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        listQuanLy.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        listQuanLy.setAdapter(adapter);
    }

    private void saveMaNV(String s) {
        emailNv = s;
    }


    private void anhxa() {
        listQuanLy = findViewById(R.id.listQuanLy);
        toolbar = findViewById(R.id.toolbarAdminQuanLy);
        btnThemQuanLy = findViewById(R.id.btnThemQuanLy);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Lựa chọn");
        getMenuInflater().inflate(R.menu.floatingmenuquanly, menu);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionXoaQL:

                deleteUSer();
                return true;
            case R.id.optionSuaQL:
                editUser();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    private void editUser() {
        mData = firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("kiemtra", emailNv);
                    NhanVien a = ds.getValue(NhanVien.class);
                    if (a.email.equals(emailNv)) {
                        if (a.getChucvu().equals("QL")) {
                            Toasty.warning(AdminQuanLy.this, "Đã là quản lý", Toast.LENGTH_SHORT).show();
                        } else {
                            a.setChucvu("QL");
                            ds.getRef().setValue(a);
                            Toasty.success(AdminQuanLy.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        Toasty.normal(AdminQuanLy.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
