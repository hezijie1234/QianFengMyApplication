package com.qianfeng.day20_handler_message;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public static final int MESSAGE_PROGRESS = 1;
    private ProgressBar mProgressBar;
    private int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);
    }

    /**
     * 使用匿名内部类的方式初始化Handler
     */
    private Handler mHandler = new Handler(){

        //消息的处理
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_PROGRESS:
                    mProgressBar.setProgress(msg.arg1);
                    mProgress += 10;
                    if(mProgress == 100){
                        mProgress = 0;
                    }
                    //创建消息
                    Message message = mHandler.obtainMessage(MESSAGE_PROGRESS, mProgress, 0);
                    //发送消息
                    mHandler.sendMessageDelayed(message,1000);
                    break;
            }
        }
    };

    public void onClickStart(View view) {

        //创建消息
        Message message = mHandler.obtainMessage(MESSAGE_PROGRESS, 0, 0);
        //发送消息
        mHandler.sendMessage(message);
    }
}
