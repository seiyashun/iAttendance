package com.tangtuongco.chamcong.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tangtuongco.chamcong.R;

public class SlashScreenActivity extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slashscreen);

        anhxa();
        init();
    }

    private void init() {
        Glide.with(getApplicationContext())
                .load(R.drawable.coffeetek)
                .into(img);


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

    private void anhxa() {
        img=findViewById(R.id.slashscreen);
    }
}
