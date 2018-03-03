package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    public static final String SHARED_FILE_NAME = "myshare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("first",true);
        if(result){
            sharedPreferences.edit().putBoolean("first", false).commit();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }, 5000);
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            },0 );
        }

    }
}
