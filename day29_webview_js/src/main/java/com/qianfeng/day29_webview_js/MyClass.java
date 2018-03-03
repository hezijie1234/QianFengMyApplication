package com.qianfeng.day29_webview_js;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by xray on 17/1/12.
 */

public class MyClass {

    private Context mContext;

    public MyClass(Context mContext){
        this.mContext = mContext;
    }

    //如果要再JS中调用该方法，需要添加注解：
    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
