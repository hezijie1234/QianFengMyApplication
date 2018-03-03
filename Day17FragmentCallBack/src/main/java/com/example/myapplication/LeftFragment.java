package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.callback.SetTextCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2016/12/27.
 */
public class LeftFragment extends Fragment{
    private ArrayAdapter<String> mTitleAdapter;
    private ListView mTitleLv;
    private List<String> mDataList;
    private SetTextCallBack setTextCallBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left, container, false);
        initData();
        initViews(view);
        return view;
    }
    public void setOnRightFragment(SetTextCallBack setTextCallBack){
        this.setTextCallBack = setTextCallBack;
    }
    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDataList.add("新闻"+i);
        }
    }


    private void initViews(View view) {
        mTitleLv = (ListView)view.findViewById(R.id.title_lv);
        mTitleAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,android.R.id.text1,mDataList);
        mTitleLv.setAdapter(mTitleAdapter);
        mTitleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(setTextCallBack!=null){
                    setTextCallBack.setText(mDataList.get(position));
                }
            }
        });
    }


}
