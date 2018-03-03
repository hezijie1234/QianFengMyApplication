package day23downappbyservice.day29webview;

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
        mWebView = (WebView) findViewById(R.id.text_wb);
        mWebView.loadUrl("");
    }

    public void loadLocalWeb(View view) {
        mWebView.loadUrl("file:///android_asset/test.html");
    }


    public void clickBaidu(View view) {
        mWebView.loadUrl("http://www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void onClickByCode(View view) {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head>");
        html.append("<title>");
        html.append("标题");
        html.append("</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("这是HTML代码创建的网页");
        html.append("</body>");
        html.append("</html>");
        mWebView.loadDataWithBaseURL(null,html.toString(),"text/html","utf-8",null);
    }
}
