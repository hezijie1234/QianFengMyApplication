package com.example.myapplication;

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

    public void startServiceClick(View view) {
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("000","传送");
        startService(intent);
    }

    public void destServiceClick(View view) {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }

    public void bindServiceClick(View view) {
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }



    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void unBindServiceClick(View view) {
        unbindService(conn);
    }
}
