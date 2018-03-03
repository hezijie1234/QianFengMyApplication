package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.R;

import static android.content.ContentValues.TAG;


/**
 * Created by xray on 16/12/27.
 */

public class MeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.me_frag,container,false);
        Log.i("xxx", "onCreateView: MeFragment");
        return vi;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("xxx", "onDestroyView: MeFragment");
    }

}
