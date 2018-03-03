package com.qianfeng.day17_frag_frag_call.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qianfeng.day17_frag_frag_call.R;

/**
 * 新闻详情的Fragment
 * Created by xray on 16/12/27.
 */

public class RightFragment extends Fragment implements OnSetTextCallback{

    private TextView mContentTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mContentTv = (TextView) view.findViewById(R.id.content_tv);
    }

    @Override
    public void setText(String text) {
        mContentTv.setText(text);
    }
}
