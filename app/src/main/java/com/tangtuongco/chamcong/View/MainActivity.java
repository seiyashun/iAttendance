package com.tangtuongco.chamcong.View;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.tangtuongco.chamcong.Model.ChucVu;
import com.tangtuongco.chamcong.Model.NhanVien;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.Service.StartService;
import com.tangtuongco.chamcong.View.Fragments.BanBeF;
import com.tangtuongco.chamcong.View.Fragments.CaNhan;
import com.tangtuongco.chamcong.View.Fragments.OrderF;
import com.tangtuongco.chamcong.View.Fragments.TheoDoiF;
import com.tangtuongco.chamcong.View.Fragments.TrangChuF;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener  {
    Toolbar toolbar;
    BottomNavigationView navigation;
    FrameLayout frameLayout;
    TrangChuF trangChuF;
    TheoDoiF theoDoiF;
    CaNhan canhan;
    OrderF orderF;
    ViewPager viewPager;
    BanBeF banBeF;
    Boolean aIsActive, bIsActive, cIsActive, dIsActive;
    Intent service;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    FirebaseAuth mAuth;
    NhanVien currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        //get Data
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        currentUser=new NhanVien();
        getcurrentuser();


        xuly();



    }

    private void getcurrentuser() {

        final String uid= mAuth.getCurrentUser().getEmail();

        mData=firebaseDatabase.getReference().child("NhanVien");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NhanVien a = ds.getValue(NhanVien.class);
                    if(a.getEmail().equals(uid) && a.getChucvu().equals("QL") )
                    {
                        service=new Intent(getApplicationContext(), StartService.class);
                        startService(service);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        showAlertDialog();


    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn đăng xuất không??");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isMyServiceRunning(StartService.class))
                {
                    stopService(service);
                }
                FirebaseAuth.getInstance().signOut();
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alert.show();



    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void xuly() {


        trangChuF = new TrangChuF();
        theoDoiF = new TheoDoiF();
        canhan = new CaNhan();
        orderF= new OrderF();
        banBeF = new BanBeF();
        setFragment(trangChuF);
        aIsActive = bIsActive = cIsActive = dIsActive = false;


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.navigation_trangchu:
                        setFragment(trangChuF);


                        return true;
                    case R.id.navigation_tinnhan:
                        setFragment(banBeF);


                        return true;
                    case R.id.navigation_theodoi:
                        setFragment(theoDoiF);


                        return true;
                    case R.id.navigation_canhan:
                        setFragment(canhan);


                        return true;
                    case R.id.navigation_monan:
                        setFragment(orderF);
                }

                return true;

            }
        });


    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }


    private void anhxa() {
//
        navigation = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame_container);
        viewPager = findViewById(R.id.viewpager);


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
