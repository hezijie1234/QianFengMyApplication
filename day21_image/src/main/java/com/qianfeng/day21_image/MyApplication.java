package com.qianfeng.day21_image;

import android.app.Application;

import com.qianfeng.day21_image.utils.ImageUtils;

/**
 * 应用程序类
 * Created by xray on 17/1/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //当应用程序打开后调用初始化方法
        ImageUtils.initCache(getApplicationContext());
    }
}
