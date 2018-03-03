package com.qianfeng.day22_broadcast_network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager mConnManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得网络连接管理器
        mConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //注册获得网络状态的广播接收器
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                if(getNetworkState()){
                    Log.i("xxx", "当前网络类型: "+getNetworkType());
                }else{
                    Log.i("xxx", "没有网络");
                }
            }
        }
    };

    /**
     * 获得网络连接状态
     * @return
     */
    private boolean getNetworkState(){
        NetworkInfo mobile = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mobile.isConnected() || wifi.isConnected();
    }

    /**
     * 获得网络类型名称
     * @return
     */
    private String getNetworkType(){
        NetworkInfo network = mConnManager.getActiveNetworkInfo();
        if(network == null){
            return "没有可用网络";
        }
        return network.getTypeName();
    }
}
