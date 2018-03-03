package com.qianfeng.day17_fragment_base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qianfeng.day17_fragment_base.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "111";
    //Fragment的管理器
    private FragmentManager mFragMgr = null;
    private FragmentTransaction mTrans;
    private MyFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        Log.i(TAG, "onCreate: Activty");
    }
    
    private void initFragment() {
        //获得管理器对象
        mFragMgr = getSupportFragmentManager();
        //创建Fragment
        mFragment = new MyFragment();
        //对Fragment进行管理，添加Fragment
//        mTrans = mFragMgr.beginTransaction();
//        mTrans.add(R.id.content_layout,mFragment);
//        //提交事务
//        mTrans.commit();
        mFragMgr.beginTransaction()
                .add(R.id.content_layout,mFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Activity");
    }
}
