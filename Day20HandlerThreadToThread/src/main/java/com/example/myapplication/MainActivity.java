package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case 1:

                                Log.e("111",msg.obj.toString()+"发送消息给"+Thread.currentThread().getName());
                                break;
                            case 2:
                                Log.e("111",msg.obj.toString()+"发送消息给"+Thread.currentThread().getName());
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    public void onClickMain(View view) {
        Message message = handler.obtainMessage(1, Thread.currentThread().getName());
        handler.sendMessage(message);

    }

    public void onCLickThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage(2, Thread.currentThread().getName());
                handler.sendMessage(message);
            }
        }).start();
    }
}
