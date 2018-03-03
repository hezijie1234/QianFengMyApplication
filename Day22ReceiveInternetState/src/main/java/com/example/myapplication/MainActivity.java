package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager mConnMana;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                if(getNetWorkState()){
                    Toast.makeText(MainActivity.this," "+getNetWorkType(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this," 没有可用的网络",Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnMana = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private boolean getNetWorkState(){
        NetworkInfo mobile = mConnMana.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = mConnMana.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mobile.isConnected()||wifi.isConnected();
    }
    private String getNetWorkType(){
        NetworkInfo net = mConnMana.getActiveNetworkInfo();
        if(net==null){
            return "没有可用的网络";
        }
        return net.getTypeName();
    }
}
