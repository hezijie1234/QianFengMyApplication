package com.qianfeng.day22_broadcast_local;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_MY = "com.1000phone.broadcast";
    private LocalBroadcastManager lbm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建本地广播的管理者
        lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter(ACTION_MY);
        lbm.registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lbm.unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("111", "onReceive: "+Thread.currentThread().getName() );
            if(ACTION_MY.equals(intent.getAction())){
                Log.i("xxx", "Local----->"+intent.getStringExtra("msg"));
            }
        }
    };

    public void onSendLocalBroadcast(View view) {
        Intent intent = new Intent(ACTION_MY);
        intent.putExtra("msg","我是本地人");
        lbm.sendBroadcast(intent);
    }
}
