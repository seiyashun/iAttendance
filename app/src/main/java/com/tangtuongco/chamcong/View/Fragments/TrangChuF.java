package com.tangtuongco.chamcong.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tangtuongco.chamcong.Adapter.AdapterViewPagerTrangChu;
import com.tangtuongco.chamcong.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrangChuF extends Fragment  implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{
    ViewPager viewPagerTrangChu;
    RadioButton rdcc,rdtb;
    RadioGroup radioGroupTrangChu;


    public TrangChuF() {
        // Required empty public constructor


    }

    private void anhxa(View v) {
        viewPagerTrangChu=v.findViewById(R.id.viewpager_trangchu);
        rdcc=v.findViewById(R.id.rdoCC);
        rdtb=v.findViewById(R.id.rdoTB);
        radioGroupTrangChu=v.findViewById(R.id.rdogroup);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        anhxa(v);
        AdapterViewPagerTrangChu adapterViewPagerTrangChu=new AdapterViewPagerTrangChu(getChildFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        radioGroupTrangChu.setOnCheckedChangeListener(this);

        return v;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rdcc.setChecked(true);
                break;
            case 1:
                rdtb.setChecked(true);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.rdoTB:
                viewPagerTrangChu.setCurrentItem(1);
                break;
            case R.id.rdoCC:
                viewPagerTrangChu.setCurrentItem(0);
                break;
        }

    }
}
