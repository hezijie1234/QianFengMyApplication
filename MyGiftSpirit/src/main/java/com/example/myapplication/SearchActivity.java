package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.adapter.GiftAdapter;
import com.example.myapplication.entities.Gift;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2017/1/11.
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private EditText mSearchEdit;
    private GiftAdapter mAdapter;
    private List<Gift.ListBean> mGiftList;
    private LoaderJsonData mLoad;
    final Gson mGson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstsearch);
        initViews();
        initData();
    }

    private void initData() {
        mGiftList = new ArrayList<>();
        mLoad = new LoaderJsonData();

//        load.getGift(Constants.GIFT_URL+1, new LoaderJsonData.GetJsonData() {
//
//
//            @Override
//            public void getJsonData(String jsonData) {
//                Gift gift = gson.fromJson(jsonData, Gift.class);
//                mGiftList = gift.getList();
//                mAdapter = new GiftAdapter(SearchActivity.this,mGiftList);
//                mListView.setAdapter(mAdapter);
//            }
//        });
        String input = "key="+mSearchEdit.getText().toString();
        List<String> list = new ArrayList<>();
        list.add(input);
        mLoad.getGift(Constants.SEARCH_POST_URL, list, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                Gift gift = mGson.fromJson(jsonData, Gift.class);
                mGiftList = gift.getList();
                mAdapter = new GiftAdapter(SearchActivity.this,mGiftList);
                mListView.setAdapter(mAdapter);
            }
        });
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.search_bar);
        mListView = (ListView) findViewById(R.id.search_lv);
        mSearchEdit = (EditText) findViewById(R.id.search_eidt);
        //下面的代码让标题为空，不再显示工程名，但是要卸载setSupportActionBar前面
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }


    public void backOnClick(View view) {
        finish();
    }

    public void searchOnClick(View view) {
        String input ="key="+ mSearchEdit.getText().toString();
        List<String> list = new ArrayList<>();

        list.add(input);
        mLoad.getGift(Constants.SEARCH_POST_URL, list, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                Gift gift = mGson.fromJson(jsonData, Gift.class);
                Log.e("111", "getJsonData: "+gift.getList().toString());
                mGiftList.clear();
                mGiftList.addAll(gift.getList());
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
