package com.qianfeng.day29_webview_base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
    }

    public void onClickFile(View view) {
        //加载本地网页文件
        mWebView.loadUrl("file:///android_asset/myweb.html");
    }

    public void onClickWeb(View view) {
        //打开网页
        mWebView.loadUrl("http://www.baidu.com");
        //设置WebViewClient对象
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //由WebView加载url
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void onClickHTML(View view) {
        //创建HTML代码
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head>");
        html.append("<title>");
        html.append("标题");
        html.append("</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<p>");
        html.append("这是HTML代码创建的网页");
        html.append("</p>");
        html.append("</body>");
        html.append("</html>");
        //加载HTML代码
//        mWebView.loadData(html.toString(),"text/html","utf-8");
        mWebView.loadDataWithBaseURL(null,html.toString(),"text/html","utf-8",null);
    }
}
