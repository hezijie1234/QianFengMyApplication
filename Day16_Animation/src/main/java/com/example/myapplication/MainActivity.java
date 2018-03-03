package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private ImageView mWeiXinIm;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView(){
        handler = new Handler();
        mWeiXinIm = (ImageView)findViewById(R.id.image);
        mWeiXinIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeiXinIm.setImageResource(R.drawable.weixintest);
                AnimationDrawable anim = (AnimationDrawable)mWeiXinIm.getDrawable();
                anim.start();
                int time =0;
                for(int i=0;i<anim.getNumberOfFrames();i++){
                    time+=anim.getDuration(i);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWeiXinIm.setImageResource(R.mipmap.open);
                    }
                },time*20);
            }
        });

    }
}
