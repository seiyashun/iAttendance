package com.tangtuongco.chamcong.Service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tangtuongco.chamcong.Model.GioCong;
import com.tangtuongco.chamcong.R;
import com.tangtuongco.chamcong.View.Fragments.ChamCongF;

public class StartService extends Service {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    Dialog dialog;
    ChildEventListener childEventListener;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        firebaseDatabase= FirebaseDatabase.getInstance();
        mData=firebaseDatabase.getReference().child("OTP");
        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                int otp= dataSnapshot.getValue(int.class);
                showAlertDialog(key,otp);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mData.addChildEventListener(childEventListener);

        Toast.makeText(this, "onStartcommnd", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        return super.onStartCommand(intent, flags, startId);

    }
    public void showAlertDialog(String key, int otp){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Mã OTP Của Nhân Viên " +key + " là " +otp + "\n" + "Bạn có muốn gửi mã OTP này đến nhân viên không ???");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alert.show();



    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Destroyyyy", Toast.LENGTH_SHORT).show();
        mData.removeEventListener(childEventListener);
        super.onDestroy();
    }
}
