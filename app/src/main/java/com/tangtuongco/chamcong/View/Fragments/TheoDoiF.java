package com.tangtuongco.chamcong.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangtuongco.chamcong.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheoDoiF extends Fragment {


    public TheoDoiF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theo_doi, container, false);
    }

}
