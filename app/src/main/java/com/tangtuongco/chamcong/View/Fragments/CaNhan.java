package com.tangtuongco.chamcong.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tangtuongco.chamcong.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhan extends Fragment {

    CircleImageView imgAva;

    public CaNhan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        anhxa(v);
        Glide.with(v)
                .load("https://i.ytimg.com/vi/vdPiMza1M6g/hqdefault.jpg")
                .into(imgAva);




        return v;
    }

    private void anhxa(View v) {
        imgAva=v.findViewById(R.id.imgAvaCaNhan);
    }

}
