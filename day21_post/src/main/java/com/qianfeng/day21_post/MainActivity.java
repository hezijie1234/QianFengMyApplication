package com.qianfeng.day21_post;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qianfeng.day21_post.utils.Constants;
import com.qianfeng.day21_post.utils.JSONLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONLoader loader = new JSONLoader();
        List<String> params = new ArrayList<>();
        params.add("key=星际");
        loader.loadJSONForPost(Constants.SEARCH_URL, params,
                new JSONLoader.OnJSONLoadListener() {
                    @Override
                    public void onJSONLoadComplete(String json) {
                        Log.i("xxx", "onJSONLoadComplete: "+json);
                    }
                });
    }
}
