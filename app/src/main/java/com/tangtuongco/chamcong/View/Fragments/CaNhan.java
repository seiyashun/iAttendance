package com.tangtuongco.chamcong.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.QuanLyPanel;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhan extends Fragment {

    CircleImageView imgAva;
    Button btn;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuanLyPanel.class);
                startActivity(i);
            }
        });


        return v;
    }

    private void anhxa(View v) {
        imgAva=v.findViewById(R.id.imgAvaCaNhan);
        btn=v.findViewById(R.id.btnQuanLyPanel);
    }

}
