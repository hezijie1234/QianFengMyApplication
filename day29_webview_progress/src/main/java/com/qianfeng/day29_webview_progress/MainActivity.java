package com.qianfeng.day29_webview_progress;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.web_view);

        mWebView.loadUrl("http://www.ifeng.com");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //初始化进度对话框
        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.show();
        //设置WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient(){

            //网页加载进度的更新
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    mDialog.dismiss();
                }else{
                    mDialog.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击返回键
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            //让网页返回
            if(mWebView.canGoBack()){
                mWebView.goBack();
                return true; //让程序不会关闭
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
