package com.androidxx.yangjw.day32_butter_knife_demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangjw on 2017/2/4.
 */
public class MyFragment extends Fragment {

    @BindView(R.id.fragment_list_view)
    ListView listView;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        ButterKnife.bind(this,view);
        MyListAdapter listAdapter = new MyListAdapter(context);
        listView.setAdapter(listAdapter);
        return view;
    }
}
