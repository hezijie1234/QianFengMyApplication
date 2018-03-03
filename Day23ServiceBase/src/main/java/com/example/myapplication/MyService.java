package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hezijie on 2017/1/5.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("111", "onBind: " );
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("111", "onCreate " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("111", "onDestroy: " );
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("111", "onUnbind: " );
        //返回true表示可以重新绑定 ，但是要用onReBind来重新绑定

        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e("111", "onRebind: " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("111", "onStartCommand: " );
        String str = intent.getStringExtra("000");
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

}
