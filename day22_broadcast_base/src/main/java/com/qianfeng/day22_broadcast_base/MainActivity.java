package com.qianfeng.day22_broadcast_base;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyReceiver myReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //注册广播接受者
        //创建IntentFilter对象
//        IntentFilter filter = new IntentFilter(MyReceiver.ACTION_MY);
//        registerReceiver(new MyReceiver(),filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播接受者
//        unregisterReceiver(myReceiver);
    }

    public void onClickSendBroadcast(View view) {
        //发送广播
        Intent intent = new Intent(MyReceiver.ACTION_MY);
        intent.putExtra("msg","美女，出去走走呗~~");
        sendBroadcast(intent);
    }
}
