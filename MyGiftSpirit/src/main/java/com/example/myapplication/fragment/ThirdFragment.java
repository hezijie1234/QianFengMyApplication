package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HotEntity;
import com.example.myapplication.adapter.HotGameAdapter;
import com.example.myapplication.adapter.HotGridAdapter;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2016/12/27.
 */
public class ThirdFragment extends Fragment{
    private PullToRefreshListView mListView;
    private GridView mGridView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        initViews(view);
        initData();
        return view;
    }

    private void initData() {
        LoaderJsonData load = new LoaderJsonData();
        final Gson gson = new Gson();
        load.getGift(Constants.HOT_URL, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                HotEntity hotEntity = gson.fromJson(jsonData, HotEntity.class);
                HotEntity.InfoBean info = hotEntity.getInfo();
                List<HotEntity.InfoBean.Push1Bean> push2 = info.getPush1();
                List<HotEntity.InfoBean.Push2Bean> push1 = info.getPush2();
                HotGameAdapter adapter = new HotGameAdapter(getContext(),push2);
                mListView.setAdapter(adapter);
                HotGridAdapter adapter1 = new HotGridAdapter(getContext(),push1);
                mGridView.setAdapter(adapter1);
            }
        });
    }

    private void initViews(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.hot_lv);
        mGridView = (GridView) view.findViewById(R.id.hot_gv);
    }
}
