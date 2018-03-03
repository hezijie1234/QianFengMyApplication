package com.qianfeng.day24_aidl_client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qianfeng.day24_aidl_service.aidl.IPlayer;

public class MainActivity extends AppCompatActivity {

    private IPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定服务端的Service
        Intent intent = new Intent(".MusicService");
        //设置服务端的程序包名
        intent.setPackage("com.qianfeng.day24_aidl_service");
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得服务端的接口对象
            mPlayer = IPlayer.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayer = null;
        }
    };

    public void onClickPlay(View view) {
        try {
            mPlayer.play("/storage/emulated/0/qqmusic/song/刀郎 - 2002年的第一场雪 [mqms2].mp3");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickPause(View view) {
        try {
            mPlayer.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickResume(View view) {
        try {
            mPlayer.resume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickStop(View view) {
        try {
            mPlayer.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
