package com.qianfeng.day23_service_base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 自定义服务
 * Created by xray on 17/1/5.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("xxx", "onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("xxx", "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("xxx", "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("xxx", "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long time = intent.getLongExtra("time", 0);
        Log.i("xxx", "onStartCommand: "+time);
        return super.onStartCommand(intent, flags, startId);
    }
}
