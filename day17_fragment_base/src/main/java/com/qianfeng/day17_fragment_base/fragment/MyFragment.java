package com.qianfeng.day17_fragment_base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.day17_fragment_base.R;

/**
 * 一个简单的Fragment
 * Created by xray on 16/12/27.
 */

public class MyFragment extends Fragment{

    public static final String TAG = "111";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: Fragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: Fragment");
    }

    //创建Fragment中的视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Fragment");
        View view = inflater.inflate(R.layout.myfragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: Fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Fragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Fragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: Fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: Fragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: Fragment");
    }
}
