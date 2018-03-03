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
 * 实现懒加载的Fragment
 * Created by xray on 16/12/27.
 */

public class FindFragment extends Fragment {

    private boolean isVisible;//是否可见
    private boolean isPrepared;//视图是否加载完成

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.find_frag,container,false);
        Log.i("xxx", "onCreateView: FindFragment");
        //在视图初始化完成后 initViews
        isPrepared = true;
        loadData();
        return vi;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        Log.i("xxx", "onDestroyView: FindFragment");
    }

    //设置用户是否看到Fragment
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        loadData();
    }

    private void loadData(){
        //在用户不可见或视图没有初始化成功的状态下，不去进行数据加载
        if(!isVisible || !isPrepared){
            return;
        }
        Log.i("xxx", "loadData: FindFragment---------");
    }
}
