package com.qianfeng.day19_slidepanelayout;

import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SlidingPaneLayout mSlidePane;
    //内容界面布局
    private View  mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mContentView = findViewById(R.id.activity_main);
        mSlidePane = (SlidingPaneLayout) findViewById(R.id.sliding_pane);
        //设置侧滑监听
        mSlidePane.setPanelSlideListener(
                new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mContentView.setScaleX(1 - slideOffset * 0.4f);
                mContentView.setScaleY(1 - slideOffset * 0.4f);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
    }
}
