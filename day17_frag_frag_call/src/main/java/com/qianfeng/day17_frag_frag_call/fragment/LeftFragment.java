package com.qianfeng.day17_frag_frag_call.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qianfeng.day17_frag_frag_call.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表Fragment
 * Created by xray on 16/12/27.
 */

public class LeftFragment extends Fragment {

    private ListView mNewsLv = null;
    private List<String> mNews;
    private ArrayAdapter<String> mAdapter;
    //回调接口对象
    private OnSetTextCallback mCallback;
//    private RightFragment rightFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left, container, false);
        initData();
        initViews(view);
        return view;
    }

    private void initData() {
        mNews = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mNews.add("News --------- " + i);
        }
    }

    //设置回调接口的方法
    public void setOnSetTextCallback(OnSetTextCallback callback){
        this.mCallback = callback;
    }

//    public void setRightFragment(RightFragment rightFragment){
//        this.rightFragment = rightFragment;
//    }

    private void initViews(View view) {
        mNewsLv = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mNews);
        mNewsLv.setAdapter(mAdapter);
        //设计列表的点击事件
        mNewsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mNews.get(position);
                //接口回调
                if(mCallback != null){
                    mCallback.setText(s);
                }
//                rightFragment.setText(s);
            }
        });
    }
}
