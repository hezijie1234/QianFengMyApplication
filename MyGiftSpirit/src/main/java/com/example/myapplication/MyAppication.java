package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.utils.BitmapCache;

/**
 * Created by hezijie on 2017/1/3.
 */
public class MyAppication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        BitmapCache.initCache(getApplicationContext());
    }
}
