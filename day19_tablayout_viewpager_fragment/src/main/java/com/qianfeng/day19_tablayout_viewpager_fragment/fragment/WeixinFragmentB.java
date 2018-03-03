package com.qianfeng.day19_tablayout_viewpager_fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.day19_tablayout_viewpager_fragment.R;


/**
 * Created by xray on 16/12/27.
 */

public class WeixinFragmentB extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.weixin_frag_b,container,false);
        Log.i("xxx", "onCreateView: WeixinFragment");

        return vi;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("xxx", "onDestroyView: WeixinFragment");
    }

}
