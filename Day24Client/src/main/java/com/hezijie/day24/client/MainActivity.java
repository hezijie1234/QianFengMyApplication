package com.hezijie.day24.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import day23downappbyservice.aidl.IMusicService;


public class MainActivity extends AppCompatActivity {
    private IMusicService musicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("android.intent.action.mymusic");
        intent.setPackage("day23downappbyservice.myapplication");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = IMusicService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    public void onClcikPlay(View view) {
        try {
            if(musicService!=null){

                musicService.play("/storage/emulated/0/qqmusic/song/刀郎 - 2002年的第一场雪 [mqms2].mp3");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClcikPause(View view) {
        if(null!=musicService){
            try {
                musicService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClcikRestart(View view) {
        if(null!=musicService){
            try {
                musicService.resume();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClcikClose(View view) {
        if(null!=musicService){
            try {
                musicService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
