package com.tangtuongco.chamcong.View;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.Fragments.BanBeF;
import com.tangtuongco.chamcong.View.Fragments.CaNhan;
import com.tangtuongco.chamcong.View.Fragments.TheoDoiF;
import com.tangtuongco.chamcong.View.Fragments.TrangChuF;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    Toolbar toolbar;
    BottomNavigationView navigation;
    FrameLayout frameLayout;
    TrangChuF trangChuF;
    TheoDoiF theoDoiF;
    CaNhan canhan;
    ViewPager viewPager;
    BanBeF banBeF;
    Boolean aIsActive, bIsActive, cIsActive, dIsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        xuly();


    }

    private void xuly() {
        trangChuF = new TrangChuF();
        theoDoiF = new TheoDoiF();
        canhan = new CaNhan();
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
