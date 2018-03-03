package com.qianfeng.day29_webview_js;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.web_view);
        //启动JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //加载本地网页
        mWebView.loadUrl("file:///android_asset/myweb.html");
        //把MyClass对象，注入到JS中
        mWebView.addJavascriptInterface(new MyClass(this),"myObj");
    }

    public void onClickAndroidCallJS(View view) {
        //调用网页中的JS
        mWebView.loadUrl("javascript:sayHi('"+("test"+System.currentTimeMillis())+"')");
    }

}
