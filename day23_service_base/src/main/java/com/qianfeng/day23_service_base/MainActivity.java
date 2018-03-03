package com.qianfeng.day23_service_base;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartService(View view) {
        //第一次执行会启动Service，后面执行是给Service传值
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("time",System.currentTimeMillis());
        startService(intent);
    }

    public void onStopService(View view) {
        //停止Service
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }

    public void onBindService(View view) {
        Intent intent = new Intent(this,MyService.class);
        //绑定服务,Service.BIND_AUTO_CREATE代表如果服务没有启动，就创建服务
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    public void onUnbindService(View view) {
        //解除绑定
        unbindService(connection);
    }

    /**
     * 服务连接对象
     */
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
