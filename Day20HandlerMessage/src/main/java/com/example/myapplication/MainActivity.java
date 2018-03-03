package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private int mPorgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mProgressBar.setProgress(msg.arg1);
                    if(msg.arg1==100){
                        mPorgress=0;
                    }
                    mPorgress+=10;
                    Message message = handler.obtainMessage(1, mPorgress, 0);
                    handler.sendMessageDelayed(message,1000);
                    break;
            }
        }
    };
    public void onClickBar(View view) {
        Message message = handler.obtainMessage(1, 0, 0);
        handler.sendMessage(message);
    }
}
