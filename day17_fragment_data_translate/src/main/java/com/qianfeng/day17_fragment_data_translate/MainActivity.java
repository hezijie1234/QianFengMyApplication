package com.qianfeng.day17_fragment_data_translate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianfeng.day17_fragment_data_translate.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragMgr;
//    private MyFragment mFragment;
    private TextView mCountTv;
    private Button mCountBtn;

    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
    }

    private void initViews() {
        mCountTv = (TextView) findViewById(R.id.count_tv);
        mCountBtn = (Button) findViewById(R.id.count_btn);
        mCountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接通过Fragment的引用调用Fragment的方法
//                mFragment.count();
                MyFragment myFragment =
                        (MyFragment) mFragMgr.findFragmentByTag("MyFragment");
                myFragment.count();
            }
        });
    }

    private void initFragments() {
//        mFragment = new MyFragment();
        mFragMgr = getSupportFragmentManager();
        mFragMgr.beginTransaction()
                .add(R.id.content_layout,new MyFragment(),"MyFragment")
                .commit();
    }

    //Activity中，修改TextView的方法
    public void count(){
        mCount++;
        mCountTv.setText("Fragment给Activity点赞："+mCount);
    }
}
