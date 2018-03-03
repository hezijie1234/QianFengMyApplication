package com.qianfeng.day19_expandablelistview_base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.qianfeng.day19_expandablelistview_base.adapter.GroupAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView mExLv = null;
    //分组的集合
    private Map<String,List<String>> mData;
    private GroupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        mData = new LinkedHashMap<>();
        List<String> group1 = new ArrayList<>();
        group1.add("group1----->data1");
        group1.add("group1----->data2");
        group1.add("group1----->data3");
        mData.put("group1",group1);
        List<String> group2 = new ArrayList<>();
        group2.add("group2----->data1");
        group2.add("group2----->data2");
        group2.add("group2----->data3");
        mData.put("group2",group2);
        List<String> group3 = new ArrayList<>();
        group3.add("group3----->data1");
        group3.add("group3----->data2");
        group3.add("group3----->data3");
        mData.put("group3",group3);
    }

    private void initViews() {
        mExLv = (ExpandableListView) findViewById(R.id.ex_listview);
        mAdapter = new GroupAdapter(this,mData);
        mExLv.setAdapter(mAdapter);
        //默认展开
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            mExLv.expandGroup(i);
        }
        mExLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        mExLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String s = mData.keySet().toArray()[groupPosition].toString();
                String child = mData.get(s).get(childPosition);
                Toast.makeText(MainActivity.this, child, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
