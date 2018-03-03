package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.StartSysActivity;
import com.example.myapplication.adapter.StartSysLeftAdapter;
import com.example.myapplication.entities.StartSysLeftE;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoaderJsonData;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hezijie on 2016/12/29.
 */
public class FragStartSys  extends Fragment{

    private ExpandableListView mListView;
    private Map<String ,List<StartSysLeftE.InfoBean>> mMap;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_sys, container, false);
        initView(view);
        initData();
        listener();
        return view;
    }

    private void listener() {
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String  key = (String) mMap.keySet().toArray()[groupPosition];
                StartSysLeftE.InfoBean infoBean = mMap.get(key).get(childPosition);
                String gid = infoBean.getGid();
                MainActivity ac = (MainActivity) getActivity();
                Intent intent = new Intent(ac, StartSysActivity.class);
                intent.putExtra("gid",gid);
                Log.e("111", "onChildClick: "+gid );
                startActivity(intent);
                return true;
            }
        });
    }

    private void initData() {
        mMap = new LinkedTreeMap<>();
        LoaderJsonData load = new LoaderJsonData();
        final Gson gson = new Gson();
        load.getGift(Constants.START_SYS_LEFT, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                StartSysLeftE startSysLeftE = gson.fromJson(jsonData, StartSysLeftE.class);
                List<StartSysLeftE.InfoBean> info = startSysLeftE.getInfo();
                for (int i = 0; i < info.size(); i++) {
                    StartSysLeftE.InfoBean infoBean = info.get(i);
                    String starttime = infoBean.getStarttime();
                    List<StartSysLeftE.InfoBean> list = new ArrayList<StartSysLeftE.InfoBean>();
                    list.add(infoBean);
                    mMap.put(starttime,list);
                }
                StartSysLeftAdapter adapter = new StartSysLeftAdapter(mMap,getContext());
                mListView.setAdapter(adapter);
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    mListView.expandGroup(i);
                }
            }
        });
    }

    private void initView(View view) {
        mListView = (ExpandableListView) view.findViewById(R.id.start_sys_left_ev);
    }
}
