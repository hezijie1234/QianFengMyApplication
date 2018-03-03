package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * Created by hezijie on 2016/12/27.
 */
public class FragmentA extends Fragment{
    private int mCount;
    private Button mFragBtn;
    private TextView mFragText;
    private int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment,container,false);
        initView(view);
        return view;
    }
    public void count(){
        mCount++;
        mFragText.setText("Fragment 的count"+mCount);
    }

    private void initView(View view) {

        mFragBtn = (Button) view.findViewById(R.id.frag_btn);
        mFragText = (TextView)view.findViewById(R.id.fragment_text);
        mFragBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.count();
            }
        });
    }


}
