package com.tangtuongco.chamcong.View;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.Fragments.BanBeF;
import com.tangtuongco.chamcong.View.Fragments.CaNhan;
import com.tangtuongco.chamcong.View.Fragments.TheoDoiF;
import com.tangtuongco.chamcong.View.Fragments.TrangChuF;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView navigation;
    FrameLayout frameLayout;
    TrangChuF trangChuF;
    TheoDoiF theoDoiF;
    CaNhan canhan;
    BanBeF banBeF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        xuly();


    }

    private void xuly() {
        trangChuF=new TrangChuF();
        theoDoiF=new TheoDoiF();
        canhan=new CaNhan();
        banBeF=new BanBeF();
        setFragment(trangChuF);
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
                }
                return false;
            }
        });




    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }


    private void anhxa() {
//
        navigation = findViewById(R.id.navigation);
        frameLayout=findViewById(R.id.frame_container);


    }


}
