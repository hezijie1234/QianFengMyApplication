package com.qianfeng.day20_handler_thread_ex;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int MESSAGE_TEST = 1;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建子线程
        new Thread(new Runnable(){
            public void run(){
                //给当前线程创建Looper
                Looper.prepare();
                //在子线程中创建Handler，用于接受消息
                mHandler = new Handler(){
                    public void handleMessage(Message msg){
                        switch (msg.what){
                            case MESSAGE_TEST:
                                Log.i("xxx", "当前线程"+Thread.currentThread().getName()
                                        +"收到"+msg.obj+"发送的消息");
                                break;
                        }
                    }
                };
                //启动消息循环
                Looper.loop();
            }
        }).start();
    }

    public void onClickSendToThread(View view) {
        //主线程中给子线程中的Handler，发送消息
        mHandler.obtainMessage(MESSAGE_TEST,
                Thread.currentThread().getName())
                .sendToTarget();
    }

    public void onClickThreadSendToThread(View view) {
        //子线程给子线程发消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.obtainMessage(MESSAGE_TEST,
                        Thread.currentThread().getName())
                        .sendToTarget();
            }
        }).start();
    }
}
