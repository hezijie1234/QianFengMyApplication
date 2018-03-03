package day23downappbyservice.day29androidandjs;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mWebview = (WebView) findViewById(R.id.webview);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl("file:///android_asset/test.html");
        mWebview.addJavascriptInterface(new ClassOne(this),"myObj");
    }

    public void onClick(View view) {
        mWebview.loadUrl("javascript:test('这是android调用js的方法"+ System.currentTimeMillis()+"')");
    }
}
