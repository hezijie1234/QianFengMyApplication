package day23downappbyservice.day29androidandjs;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by hezijie on 2017/1/12.
 */
public class ClassOne {

    Context context;

    public ClassOne(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public void showToast(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
