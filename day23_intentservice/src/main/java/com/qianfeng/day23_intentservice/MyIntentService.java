package com.qianfeng.day23_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by xray on 17/1/5.
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    //可以执行耗时操作，运行在子线程中
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("xxx", "onHandleIntent: "+Thread.currentThread().getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("xxx", "onCreate: "+Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("xxx", "onDestroy: "+Thread.currentThread().getName());
    }
}
