package com.tangtuongco.chamcong.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tangtuongco.chamcong.View.Fragments.ChamCongF;
import com.tangtuongco.chamcong.View.Fragments.ThongBaoF;

public class AdapterViewPagerTrangChu extends FragmentPagerAdapter {
    ChamCongF ccF;
    ThongBaoF tbF;

    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        ccF=new ChamCongF();
        tbF=new ThongBaoF();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return ccF;

            case 1:
                return  tbF;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
