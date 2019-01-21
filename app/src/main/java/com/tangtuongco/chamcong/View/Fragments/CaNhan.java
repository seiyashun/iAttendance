package com.tangtuongco.chamcong.View.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tangtuongco.chamcong.Model.LichLam;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.Model.Upload;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.View.DangKyLichLam;
import com.tangtuongco.chamcong.View.QuanLyPanel;
import com.tangtuongco.chamcong.View.SuaThongTin;
import com.tangtuongco.chamcong.ViewHolder.LichLamCaNhanViewHolder;
import com.tangtuongco.chamcong.ViewHolder.LichLamViewHolder;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhan extends Fragment {

    CircleImageView imgAva;
    FancyButton btnQuanLy, btnSuaThongTin, btnDoiMatKhau, btnDangKyLichLam;
    TextView txtid, txtname, txtnameBu, txtchucvu, txtmucluong, txtngayvaolam, txtsdt, txtemail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    StorageReference mStora;
    NhanVien currentUser;
    ProgressDialog progressDialog;
    Uri filepatch;
    int PICK_IMAGE_REQUEST = 1;
    private Activity mActivity;
    RecyclerView listLichLamCaNhan;
    FirebaseRecyclerOptions<LichLam> options;
    FirebaseRecyclerAdapter<LichLam, LichLamCaNhanViewHolder> adapter;


    public CaNhan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        anhxa(v);
        init();
        //Data
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStora = FirebaseStorage.getInstance().getReference("avatar");
        progressDialog = new ProgressDialog(getActivity());
        btnQuanLy.setVisibility(View.GONE);
        btnDangKyLichLam.setVisibility(View.GONE);
        LoadData();
        //Floading
        imgAva.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                registerForContextMenu(v);
                return false;
            }
        });
        LoadLichLam();



        return v;
    }

    private void LoadLichLam() {
        final Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH) + 1;
        final String thang = (mMonth < 10 ? "0" : "") + mMonth;


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData = firebaseDatabase.getReference().child("LichLam").child("LichChinhThucNhanVien").child(currentUser.getManv())
                        .child(thang);
                listLichLamCaNhan.setHasFixedSize(true);
                listLichLamCaNhan.setLayoutManager(new LinearLayoutManager(getActivity()));

                options = new FirebaseRecyclerOptions.Builder<LichLam>()
                        .setQuery(mData, LichLam.class)
                        .build();
                adapter = new FirebaseRecyclerAdapter<LichLam, LichLamCaNhanViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LichLamCaNhanViewHolder holder, int position, @NonNull final LichLam model) {
                        holder.txtNgay.setText(model.getNgayLam());
                        holder.txtCa.setText(model.getCaLam());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShowNoiDung(model.getCaLam());
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public LichLamCaNhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lich_lam_ca_nhan,
                                parent, false);

                        return new LichLamCaNhanViewHolder(view);
                    }
                };
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                        false);
                listLichLamCaNhan.setLayoutManager(layoutManager);
                adapter.startListening();
                listLichLamCaNhan.setAdapter(adapter);


            }
        }, 500);
    }

    private void ShowNoiDung(String caLam) {
        if(caLam.equals("A"))
        {
            Toast.makeText(mActivity, "08:00->15:00", Toast.LENGTH_SHORT).show();
        }
        else if(caLam.equals("B"))
        {
            Toast.makeText(mActivity, "15:00->23:00", Toast.LENGTH_SHORT).show();
        }
        else if(caLam.equals("C"))
        {
            Toast.makeText(mActivity, "12:00->18:00", Toast.LENGTH_SHORT).show();
        }
        else if(caLam.equals("D"))
        {
            Toast.makeText(mActivity, "09:00->15:00", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mActivity, "18:00->23:00", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        //Setup fancy button
        btnQuanLy.setText("Bảng\nQuản Lý");
        btnQuanLy.setBackgroundColor(Color.parseColor("#c59783"));
        btnQuanLy.setFocusBackgroundColor(Color.parseColor("#b98068"));
        btnQuanLy.setTextSize(13);
        btnQuanLy.setRadius(7);
        btnQuanLy.setIconResource(R.drawable.ic_discussion);
        btnQuanLy.setIconPosition(FancyButton.POSITION_TOP);
        btnQuanLy.setFontIconSize(30);

        btnDoiMatKhau.setText("Thay Đổi\nMật Khẩu");
        btnDoiMatKhau.setBackgroundColor(Color.parseColor("#83c597"));
        btnDoiMatKhau.setFocusBackgroundColor(Color.parseColor("#2ba6f2"));
        btnDoiMatKhau.setTextSize(13);
        btnDoiMatKhau.setRadius(7);
        btnDoiMatKhau.setIconResource(R.drawable.ic_trumcuoi);
        btnDoiMatKhau.setIconPosition(FancyButton.POSITION_TOP);
        btnDoiMatKhau.setFontIconSize(30);

        btnDangKyLichLam.setText("Đăng Ký\nLịch Làm");
        btnDangKyLichLam.setBackgroundColor(Color.parseColor("#c41db6"));
        btnDangKyLichLam.setFocusBackgroundColor(Color.parseColor("#c59783"));
        btnDangKyLichLam.setTextSize(13);
        btnDangKyLichLam.setRadius(7);
        btnDangKyLichLam.setIconResource(R.drawable.ic_time);
        btnDangKyLichLam.setIconPosition(FancyButton.POSITION_TOP);
        btnDangKyLichLam.setFontIconSize(30);

        btnSuaThongTin.setText("Sửa Thông Tin\nCá Nhân");
        btnSuaThongTin.setBackgroundColor(Color.parseColor("#3b5998"));
        btnSuaThongTin.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        btnSuaThongTin.setTextSize(13);
        btnSuaThongTin.setRadius(7);
        btnSuaThongTin.setIconResource(R.drawable.ic_content);
        btnSuaThongTin.setIconPosition(FancyButton.POSITION_TOP);
        btnSuaThongTin.setFontIconSize(30);

        btnDangKyLichLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DangKyLichLam.class);
                Bundle bundle = new Bundle();
                bundle.putString("MaNV", currentUser.getManv());
                Log.d("kiemtra", currentUser.getManv());
                i.putExtras(bundle);
                startActivity(i);


            }
        });

        btnQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuanLyPanel.class);
                startActivity(i);
            }
        });
        btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SuaThongTin.class);
                startActivity(i);
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiMatKhau();
            }
        });

    }

    private void DoiMatKhau() {
        ThayDoiMatKhauUserDialog thayDoiMatKhauUserDialog = new ThayDoiMatKhauUserDialog();
        thayDoiMatKhauUserDialog.show(getActivity().getSupportFragmentManager(), "Thay đổi mật khẩu");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionChonHinh:
                chooseImage();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.floatingmenuavatar, menu);


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepatch = data.getData();
            Glide.with(getActivity())
                    .load(filepatch)
                    .into(imgAva);
        }

        uploadFile();

    }


    private void LoadData() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final String email = mAuth.getCurrentUser().getEmail();
        mData = firebaseDatabase.getReference().child("NhanVien");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NhanVien a = ds.getValue(NhanVien.class);
                    if (a.getEmail().equals(email)) {
                        saveData(a);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(valueEventListener);

    }


    private void saveData(NhanVien a) {
        if (getActivity() != null) {
            currentUser = new NhanVien();
            currentUser = a;
            //set data
            txtnameBu.setText(currentUser.getHoten());
            txtname.setText(currentUser.getHoten());
            txtsdt.setText(currentUser.getSdt());
            txtngayvaolam.setText(FormatHelper.formatNgay(currentUser.getNgayvaolam()));
            txtemail.setText(a.getEmail());
            txtmucluong.setText(String.valueOf(a.getMucluong()));
            txtid.setText(a.getManv());
            Glide.with(getActivity())
                    .load(a.getAva())
                    .into(imgAva);
            txtchucvu.setText(a.getChucvu());
            progressDialog.dismiss();
            if (a.getChucvu().equals("QL")) {
                btnQuanLy.setVisibility(View.VISIBLE);
            } else {
                btnDangKyLichLam.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    private void anhxa(View v) {
        imgAva = v.findViewById(R.id.imgAvaCaNhan);
        btnQuanLy = v.findViewById(R.id.btnQuanLyPanel);
        btnSuaThongTin = v.findViewById(R.id.btnSuaThongTin);
        btnDoiMatKhau = v.findViewById(R.id.btnDoiMatKhau);
        btnDangKyLichLam = v.findViewById(R.id.btnDangKyLichLam);
        txtchucvu = v.findViewById(R.id.txtCaNhanChucVu);
        txtemail = v.findViewById(R.id.txtCaNhanEmail);
        txtid = v.findViewById(R.id.txtCaNhanID);
        txtmucluong = v.findViewById(R.id.txtCaNhanMucLuong);
        txtngayvaolam = v.findViewById(R.id.txtCaNhanNgayVaoLam);
        txtsdt = v.findViewById(R.id.txtCaNhanSDT);
        txtname = v.findViewById(R.id.txtCaNhanName);
        txtnameBu = v.findViewById(R.id.txtNameCaNhan);
        listLichLamCaNhan = v.findViewById(R.id.listLichLamCaNhan);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (filepatch != null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            final StorageReference fileReference = mStora.child(System.currentTimeMillis() + "." + getFileExtension(filepatch));

            UploadTask uploadTask = fileReference.putFile(filepatch);
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        changeAva(downloadUri);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        } else {

            Toasty.error(getActivity(), "Không có hình nào được chọn", Toast.LENGTH_SHORT).show();
        }

    }

    private void changeAva(final Uri downloadUri) {
        mData = firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NhanVien a = ds.getValue(NhanVien.class);
                    if (a.email.equals(txtemail.getText())) {
                        a.setAva(downloadUri.toString());
                        ds.getRef().setValue(a);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
