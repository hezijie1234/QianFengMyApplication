package com.qianfeng.day16_frameanimation_weixin;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView mWeixinIv ;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        initViews();
    }

    private void initViews() {
        mWeixinIv = (ImageView) findViewById(R.id.weixin_iv);
        //初始化默认图片
        mWeixinIv.setImageResource(R.mipmap.adv);
        //给微信图片设置点击事件
        mWeixinIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给微信设置帧动画资源作为图片
                mWeixinIv.setImageResource(R.drawable.weixin);
                //获得帧动画图片对象
                AnimationDrawable anim = (AnimationDrawable) mWeixinIv.getDrawable();
                //不重复播放
                anim.setOneShot(true);
                //启动动画
                anim.start();
                //获得动画总的执行时间
                int time = 0;
                for(int i = 0;i < anim.getNumberOfFrames();i++){
                    //获得每一帧的时间
                    time += anim.getDuration(i);
                }
                //使用Handler帮助延迟一段时间后执行图片切换
                mHandler.postDelayed(
                        new Runnable() {
                    @Override
                    public void run() {
                        mWeixinIv.setImageResource(R.mipmap.open);
                    }
                },time);
            }
        });
    }
}
