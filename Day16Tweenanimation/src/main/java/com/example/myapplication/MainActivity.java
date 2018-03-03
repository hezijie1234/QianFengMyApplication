package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button mAlphaBtn;
    private Button mRotateBtn;
    private Button mScaleBtn;
    private Button mMoveBtn;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        listener();
    }
    private void initViews(){
        mAlphaBtn = (Button) findViewById(R.id.alpha);
        mRotateBtn = (Button)findViewById(R.id.rotate);
        mScaleBtn =(Button)findViewById(R.id.scale);
        mMoveBtn = (Button)findViewById(R.id.move);
        mImage = (ImageView)findViewById(R.id.image);
    }
    private void listener(){
        mAlphaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Animation anim =  AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha);
                mImage.startAnimation(anim);
            }
        });
        mRotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim =  AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotateof);
                mImage.startAnimation(anim);
            }
        });
        mScaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim =  AnimationUtils.loadAnimation(MainActivity.this,R.anim.scaleofme);
                mImage.startAnimation(anim);
            }
        });
        mMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate);
                mImage.startAnimation(anim);
            }
        });
    }

    public void onClickAlpha(View view) {
       Animation anim =  new AlphaAnimation(1,0);
        anim.setDuration(3000);
        mImage.startAnimation(anim);
    }

    public void onClickTranslate(View view) {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_PARENT,0.8f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_PARENT,0.8f);
        anim.setDuration(3000);
        mImage.startAnimation(anim);
    }

    public void onClickRotate(View view) {
        Animation anim = new RotateAnimation(0,1080,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
        anim.setDuration(3000);
        mImage.startAnimation(anim);
    }

    public void onClickScale(View view) {
        Animation anim = new ScaleAnimation(1,2,1,2,0.5f,0.5f);
        anim.setDuration(3000);
        mImage.startAnimation(anim);
    }
}
