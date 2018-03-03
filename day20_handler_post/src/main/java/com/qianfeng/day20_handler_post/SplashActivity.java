package com.qianfeng.day20_handler_post;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        SharedPreferences shared = getSharedPreferences("shared", MODE_PRIVATE);
        boolean first_start = shared.getBoolean("first_start", false);
        if(first_start) {
            shared.edit().putBoolean("first_start",true).commit();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },10000);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            });
        }
    }
}
