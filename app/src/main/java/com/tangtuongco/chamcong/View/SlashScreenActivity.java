package com.tangtuongco.chamcong.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tangtuongco.chamcong.R;

public class SlashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slashscreen);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iDangNhap = new Intent(SlashScreenActivity.this, DangNhap.class);
                startActivity(iDangNhap);
                finish();


            }
        }, 2000);
    }
}
