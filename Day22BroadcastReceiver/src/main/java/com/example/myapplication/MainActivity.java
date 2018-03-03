package com.example.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new MyReceiver();
        //过滤器来区别哪一种广播
        //IntentFilter filter = new IntentFilter(MyReceiver.MY_BROADCAST);
        //注册网络变化，让MyReceiver可以接受系统消息
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new MyReceiver(),filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注册与取消注册与对象无关
        unregisterReceiver(receiver);
    }

    public void broadcastOnClick(View view) {
        Intent intent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        intent.putExtra("000","你好");
        sendBroadcast(intent);
    }
}
