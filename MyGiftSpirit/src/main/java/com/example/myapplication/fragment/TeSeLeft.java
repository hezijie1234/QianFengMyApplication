package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.FightWednAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entities.FightWednesDay;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by hezijie on 2017/1/8.
 */
public class TeSeLeft extends Fragment {
    private PullToRefreshListView mPullListView;
    private FightWednesDay fight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tese_left_frag, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        final Gson gson = new Gson();
        LoaderJsonData loaderJsonData = new LoaderJsonData();
        loaderJsonData.getGift(Constants.TESE_LEFT_URL, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                fight = gson.fromJson(jsonData,FightWednesDay.class);
                List<FightWednesDay.ListBean> list = fight.getList();
                mPullListView.setAdapter(new FightWednAdapter(getContext(),list));
            }
        });
    }

    private void initView(View view) {
        mPullListView = (PullToRefreshListView) view.findViewById(R.id.wednes_lv);

    }
}
