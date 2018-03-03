package com.example.myapplication.com.hezijie.fragmentof;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * Created by hezijie on 2016/12/27.
 */
public class MyFragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        Log.e("111","onCreateView");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("111","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("111","onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("111","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("111","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("111","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("111","on");
    }
}
