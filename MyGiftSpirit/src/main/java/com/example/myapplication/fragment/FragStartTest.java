package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.StartRightAdapter;
import com.example.myapplication.entities.StartSysRight;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by hezijie on 2016/12/29.
 */
public class FragStartTest extends Fragment{
    private PullToRefreshListView mListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_test, container, false);
        initViews(view);
        initData();
        return view;
    }

    private void initData() {
        LoaderJsonData load = new LoaderJsonData();
        final Gson gson = new Gson();
        load.getGift(Constants.START_SYS_RIGHT, new LoaderJsonData.GetJsonData() {
            private StartSysRight mStartRight;

            @Override
            public void getJsonData(String jsonData) {
                mStartRight = gson.fromJson(jsonData, StartSysRight.class);
                List<StartSysRight.InfoBean> info = mStartRight.getInfo();
                StartRightAdapter adapter = new StartRightAdapter(getContext(),info);
                mListView.setAdapter(adapter);
            }
        });
    }

    private void initViews(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.start_sys_right_lv);
    }
}
