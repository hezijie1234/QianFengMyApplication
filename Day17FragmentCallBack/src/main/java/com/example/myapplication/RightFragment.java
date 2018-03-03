package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.callback.SetTextCallBack;

/**
 * Created by hezijie on 2016/12/27.
 */
public class RightFragment extends Fragment implements SetTextCallBack{
    private TextView mContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right, container, false);
        mContent = (TextView) view.findViewById(R.id.content_tv);
        return view;
    }

    @Override
    public void setText(String content) {
        mContent.setText(content);
    }
}
