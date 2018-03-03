package com.qianfeng.day25_horizontalscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mLl = (LinearLayout) findViewById(R.id.ll);

        int width = getWindowManager().getDefaultDisplay().getWidth();

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(width / 2 - 10,200);
        lp.setMargins(2,0,2,0);
        //动态添加10张图片
        for (int i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            mLl.addView(imageView,lp);
        }
    }
}
