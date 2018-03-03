package com.qianfeng.day17_frag_frag_call;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianfeng.day17_frag_frag_call.fragment.LeftFragment;
import com.qianfeng.day17_frag_frag_call.fragment.RightFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragMgr = null;
    private LeftFragment mLeftFrag;
    private RightFragment mRightFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
    }

    private void initFragments() {
        mLeftFrag = new LeftFragment();
        mRightFrag = new RightFragment();
        //关联Fragment，设置回调接口
        mLeftFrag.setOnSetTextCallback(mRightFrag);
//        mLeftFrag.setRightFragment(mRightFrag);
        mFragMgr = getSupportFragmentManager();
        mFragMgr.beginTransaction()
                .add(R.id.left_layout,mLeftFrag)
                .add(R.id.right_layout,mRightFrag)
                .commit();
    }
}
