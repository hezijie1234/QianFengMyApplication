package com.qianfeng.day17_fragment_data_translate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qianfeng.day17_fragment_data_translate.MainActivity;
import com.qianfeng.day17_fragment_data_translate.R;

/**
 * Created by xray on 16/12/27.
 */

public class MyFragment extends Fragment {

    private TextView mCountTv;
    private Button mCountBtn;
    private int mCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,container,false);
        initViews(view);
        return view;
    }

    //初始化视图
    private void initViews(View view) {
        mCountBtn = (Button)view.findViewById(R.id.count_btn);
        mCountTv = (TextView)view.findViewById(R.id.count_tv);
        mCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得Fragment所在的Activity对象
                MainActivity activity = (MainActivity) getActivity();
                //调用Activity中的方法
                activity.count();
            }
        });
    }

    public void count(){
        mCount++;
        mCountTv.setText("Actvity给Fragment点赞："+mCount);
    }

}
