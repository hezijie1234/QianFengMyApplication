package com.qianfeng.day25_scrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScrollView mScrollView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mListView = (ListView) findViewById(R.id.list_view);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item----"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                list);
        mListView.setAdapter(adapter);
    }

    public void onClickToTop(View view) {
        //控制滑动
//        mScrollView.scrollTo(0,0);
        mScrollView.smoothScrollTo(0,0);
    }
}
