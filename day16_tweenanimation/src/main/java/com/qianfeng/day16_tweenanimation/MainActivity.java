package com.qianfeng.day16_tweenanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.image_view);
    }

    public void onClickAlpha(View view) {
        //加载动画文件
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.alpha);
        //让控件执行动画
        mImageView.startAnimation(anim);
    }

    public void onClickScale(View view) {
        //加载动画文件
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale);
        //让控件执行动画
        mImageView.startAnimation(anim);
    }

    public void onClickRotate(View view) {
        //加载动画文件
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        //让控件执行动画
        mImageView.startAnimation(anim);
    }

    public void onClickTranslate(View view) {
        //加载动画文件
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.translate);
        //让控件执行动画
        mImageView.startAnimation(anim);
    }

    public void onClickMix(View view) {
        //加载动画文件
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.mix);
        //让控件执行动画
        mImageView.startAnimation(anim);
    }
}
