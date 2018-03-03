package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = (ImageView)findViewById(R.id.image);
        handler = new Handler();
    }

    public void onClickFrame(View view) {
        mImage.setImageResource(R.drawable.fram_list);
        AnimationDrawable anim = (AnimationDrawable) mImage.getDrawable();
        anim.start();
        int time = 0;
        for(int i=0;i<anim.getNumberOfFrames();i++){
            time += anim.getDuration(i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImage.setImageResource(R.mipmap.a9);
            }
        },time*3);
    }
}
