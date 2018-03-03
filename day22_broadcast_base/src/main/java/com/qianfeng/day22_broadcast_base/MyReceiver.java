package com.qianfeng.day22_broadcast_base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 自定义的广播接受者
 * Created by xray on 17/1/4.
 */

public class MyReceiver extends BroadcastReceiver{

    public static final String ACTION_MY = "com.1000phone.broadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断广播的类型
        String action = intent.getAction();
        if(ACTION_MY.equals(action)){
            String msg = intent.getStringExtra("msg");
            Toast.makeText(context, "接受到消息："+msg,Toast.LENGTH_SHORT).show();
        }else if(Intent.ACTION_BOOT_COMPLETED.equals(action)){
            Log.i("xxx", "onReceive: 系统启动完成");
            context.startActivity(new Intent(context,MainActivity.class));
        }else if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
            Log.i("xxx", "onReceive: 电量变化");
        }else if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            Log.i("xxx", "onReceive: 网络状态改变");
        }
    }
}
