package com.qianfeng.day19_pulltorefresh_use;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mList;
    private int mCurrentPage = 0;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mList.add("string----->"+i);
        }
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mList);
    }

    private void initViews() {
        mHandler = new Handler();
        mListView = (PullToRefreshListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        //设置刷新模式
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新监听器
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                mCurrentPage = 0;
                //网络加载和UI更新....
                Toast.makeText(MainActivity.this, "当前页面："+mCurrentPage, Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        mListView.onRefreshComplete();
                    }
                },1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                mCurrentPage++;
                //网络加载和UI更新....
                Toast.makeText(MainActivity.this, "当前页面:"+mCurrentPage, Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        mListView.onRefreshComplete();
                    }
                },1000);
            }
        });
        //获得PullToRefreshListView中包装的原始ListView
        ListView listView = mListView.getRefreshableView();
        listView.addHeaderView(null);
    }
}
