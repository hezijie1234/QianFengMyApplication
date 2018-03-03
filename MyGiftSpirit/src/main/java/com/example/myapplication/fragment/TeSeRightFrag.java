package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewGameAdapter;
import com.example.myapplication.entities.NewGame;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by hezijie on 2017/1/8.
 */
public class TeSeRightFrag extends Fragment {
    private PullToRefreshListView mListView;
    private NewGame mNewsBeam;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tese_right_frag, container, false);
        initViews(view);
        initData();
        return view;
    }

    private void initData() {
        LoaderJsonData loaderJsonData = new LoaderJsonData();
        final Gson gson = new Gson();
        loaderJsonData.getGift(Constants.TESE_RIGHT_URL, new LoaderJsonData.GetJsonData() {


            @Override
            public void getJsonData(String jsonData) {
                mNewsBeam = gson.fromJson(jsonData, NewGame.class);
                List<NewGame.ListBean> list = mNewsBeam.getList();
                NewGameAdapter adapter = new NewGameAdapter(getContext(),list);
                mListView.setAdapter(adapter);
            }
        });
    }

    private void initViews(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.tese_right_lv);
    }
}
