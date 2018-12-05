package com.tangtuongco.chamcong.View.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuaGioCongDialog extends AppCompatDialogFragment {
    EditText edtNgay,edtGioVao,edtGioRa;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
