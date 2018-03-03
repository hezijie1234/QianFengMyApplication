package com.qianfeng.day10_listview_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.qianfeng.day10_listview_page.entity.Gift;
import com.qianfeng.day10_listview_page.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Gift> mGifts = null;
    public static final String URL_TEXT = "http://www.1688wan.com/majax.action?method=getGiftList&pageno=1";

    private ListView mGiftListView;
    private GiftAdapter mGiftAdapter;
    private JSONUtils mJsonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initData();
    }

    private void initData() {
        mJsonUtils = new JSONUtils();
        mJsonUtils.readText(URL_TEXT, new JSONUtils.OnTextTaskListener() {
            @Override
            public void onTextComplete(String text) {
                List<Gift> data = mJsonUtils.getGiftList(text);
                mGifts.addAll(data);
                mGiftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextFailed() {

            }
        });
    }

    private void initViews() {
        mGiftListView = (ListView)findViewById(R.id.list_view);
        mGifts = new ArrayList<>();
        mGiftAdapter = new GiftAdapter(this,mGifts);
        mGiftListView.setAdapter(mGiftAdapter);
    }
}
