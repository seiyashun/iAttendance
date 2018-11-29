package com.tangtuongco.chamcong.View.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.GioCong;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Ulty.FormatHelper;
import com.tangtuongco.chamcong.Ulty.TinhThoiGian;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import ss.com.bannerslider.ImageLoadingService;

public class ChamCongF extends Fragment implements View.OnClickListener {
    Button checkin,checkout;
    TextView txtNgay,txtIn,txtOut,txtTinhToan;
    FirebaseAuth mAuth;
    DatabaseReference data;
    EditText edtOTP;
    FirebaseDatabase firebaseDatabase;
    String email;
    NhanVien currentNv;
    GioCong gioCong;
    ProgressDialog progressDialog;
    LinearLayout layoutOTP;
    Long OTPPP;
    Button btnOTP;
    ViewFlipper viewFlipper;
    ArrayList<String> mangquangcao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cham_cong,container,false);
        anhxa(view);

        capnhat();
        //Lay data
        //Lay email;
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        firebaseDatabase=FirebaseDatabase.getInstance();
        currentNv=new NhanVien();

        getData();
        //GetQC
        createQC();


        //Check-in

//        checkin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ClickCheckIn();
//            }
//        });
//        checkout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickCheckOut();
//            }
//        });
        checkin.setOnClickListener(this);
        checkout.setOnClickListener(this);





        return view;
    }

    private void createQC() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        mangquangcao=new ArrayList<>();
        mangquangcao.add("https://www.incimages.com/uploaded_files/image/970x450/getty_945685712_2000133420009280406_353733.jpg");
        mangquangcao.add("https://vietblend.vn/wp-content/uploads/2017/02/thumb2-19bdc567b492626b59386e7a7aeb0117-20161019150220-630x394.jpg");
        mangquangcao.add("https://cdn1.medicalnewstoday.com/content/images/articles/301/301759/coffee.jpg");
        mangquangcao.add("https://www.healthline.com/hlcmsresource/images/topic_centers/Food-Nutrition/Coffee1-banner.jpg");
        for(int i=0;i<mangquangcao.size();i++)
        {

            ImageView imageView=new ImageView(getActivity());
            Glide.with(getActivity())
                    .load(mangquangcao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
        progressDialog.dismiss();

    }

//    private void getQC() {
//        data=firebaseDatabase.getReference().child("QuangCao");
//        data.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                LoadQc(dataSnapshot);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }






    private void clickCheckOut() {
        gioCong=new GioCong();
        final Date ngaycheckin= Calendar.getInstance().getTime();
        final int thang=Calendar.getInstance().get(Calendar.MONTH);
        final int ngay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        long a=00;
        String currentNgay = FormatHelper.formatNgay(ngaycheckin);
        String currentGio=FormatHelper.formatGio(ngaycheckin);

        data=firebaseDatabase.getReference().child("GioCong").child(currentNv.getManv()).child(String.valueOf(thang)).child(String.valueOf(ngay));
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GioCong a = dataSnapshot.getValue(GioCong.class);
                if (a.getClickOut() == false) {
                    a.setGioRa(FormatHelper.formatGio(ngaycheckin));
                    txtOut.setText(FormatHelper.formatGio(ngaycheckin));
                    a.setClickOut(true);
                    txtIn.setText(a.getGioVao());
                    data.setValue(a);
                    Toasty.info(getActivity(), "Bạn đã check out vào " + a.getGioRa(), Toast.LENGTH_SHORT).show();
                    try {
                        String giora = TinhThoiGian.GioRaTruGioVao(a.getGioVao(),a.getGioRa());
                        txtTinhToan.setText(giora);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toasty.warning(getActivity(), "Bạn đã check out rồi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








    private void ClickCheckIn() {
//        data=firebaseDatabase.getReference().child("GioCong");

        data=firebaseDatabase.getReference().child("OTP").child(currentNv.getManv());
        data.setValue(random());
        layoutOTP.setVisibility(View.VISIBLE);
        getOTP();
        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kiemtra",OTPPP+"");
                //Kiem tra OTP nhap vao
                if(edtOTP.getText().toString().equals(OTPPP.toString()))
                {

                   CheckIn();
                   layoutOTP.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(getActivity(), "Mã xác thực không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });




//        if(gioCong.getClickIn()==false)
//        {
//            gioCong.setGioVao(currentGio);
//            gioCong.setGioRa("00");
//            gioCong.setNgay(currentNgay);
//            gioCong.setClickIn(true);
//            data=firebaseDatabase.getReference().child("GioCong");
//            data.child(String.valueOf(thang)).child(String.valueOf(ngay)).child(currentNv.getManv()).setValue(gioCong);
//            Toasty.info(getActivity(),"Bạn đã check in vào " + currentGio,Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toasty.warning(getActivity(),"Bạn đã check in rồi",Toast.LENGTH_SHORT).show();
//        }



    }

    private void CheckIn() {
        gioCong=new GioCong();
        Date ngaycheckin= Calendar.getInstance().getTime();
        int thang=Calendar.getInstance().get(Calendar.MONTH);
        int ngay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        long a=00;
        String currentNgay = FormatHelper.formatNgay(ngaycheckin);
        String currentGio=FormatHelper.formatGio(ngaycheckin);
        data=firebaseDatabase.getReference().child("GioCong");
        gioCong.setGioVao(currentGio);
        gioCong.setGioRa("00");
        gioCong.setNgay(currentNgay);
        data=firebaseDatabase.getReference().child("GioCong");
        Log.d("kiemtra",currentNv.getManv()+"");
        data.child(currentNv.getManv()).child(String.valueOf(thang)).child(String.valueOf(ngay)).setValue(gioCong);
        Toasty.info(getActivity(),"Bạn đã check in vào " + currentGio,Toast.LENGTH_SHORT).show();
        txtIn.setText(gioCong.getGioVao());


    }

    private void getOTP() {
        data=firebaseDatabase.getReference().child("OTP").child(currentNv.getManv());
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long OTP = (Long) dataSnapshot.getValue();
                saveOTP(OTP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private int random()
    {
        int min=0;
        int max=9999;
        int random=new Random().nextInt((max-min)+1);
        return random;
    }
    private void getData() {
        data=firebaseDatabase.getReference().child("NhanVien");

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    NhanVien a = dataSnapshot1.getValue(NhanVien.class);
                    if(a.getEmail().equals(email))
                    {
                       saveNV(a);
                        progressDialog.dismiss();
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
    private void saveOTP(Long otp) {
        OTPPP=otp;
    }

    private void capnhat() {
        Calendar c = Calendar.getInstance();
        Date ngay = c.getTime();
        txtNgay.setText(FormatHelper.formatNgay(ngay));
        txtIn.setText("00:00");
        txtOut.setText("00:00");
    }

    private void anhxa(View view) {
        checkin=view.findViewById(R.id.btnCheckInCC);
        checkout=view.findViewById(R.id.btnCheckOutCC);
        txtNgay=view.findViewById(R.id.txtNgayCC);
        txtIn=view.findViewById(R.id.txtCheckInCC);
        txtOut=view.findViewById(R.id.txtCheckOutCC);
        txtTinhToan=view.findViewById(R.id.txtSoGioLamCC);
        layoutOTP=view.findViewById(R.id.LinearOTP);
        edtOTP=view.findViewById(R.id.edtOTP);
        btnOTP=view.findViewById(R.id.btnOTP);
        viewFlipper=view.findViewById(R.id.viewfliper);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCheckInCC:
                ClickCheckIn();
                break;
            case R.id.btnCheckOutCC:
                clickCheckOut();
                break;
        }

    }
}
