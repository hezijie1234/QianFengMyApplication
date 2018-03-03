package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by hezijie on 2017/1/4.
 */
public class MyReceiver extends BroadcastReceiver {
    public static final String MY_BROADCAST = "hezijie";
    private int count;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(MY_BROADCAST.equals(intent.getAction())){
            count++;
            String str = intent.getStringExtra("000");
            Toast.makeText(context,"从广播获取的消息---"+str+count,Toast.LENGTH_LONG).show();
        }else if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Toast.makeText(context,"网络状态改变",Toast.LENGTH_LONG).show();
        }
    }
}
