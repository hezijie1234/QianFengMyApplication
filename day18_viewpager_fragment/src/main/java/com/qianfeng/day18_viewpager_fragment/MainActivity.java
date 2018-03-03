package com.qianfeng.day18_viewpager_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qianfeng.day18_viewpager_fragment.fragment.ContactsFragment;
import com.qianfeng.day18_viewpager_fragment.fragment.FindFragment;
import com.qianfeng.day18_viewpager_fragment.fragment.MeFragment;
import com.qianfeng.day18_viewpager_fragment.fragment.WeixinFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //保存Fragment的集合
    private List<Fragment> mFragments;
    private ViewPager mContentVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViews();
    }

    private void initViews() {
        findViewById(R.id.weixin_btn).setOnClickListener(this);
        findViewById(R.id.me_btn).setOnClickListener(this);
        findViewById(R.id.find_btn).setOnClickListener(this);
        findViewById(R.id.contacts_btn).setOnClickListener(this);
        mContentVp = (ViewPager) findViewById(R.id.content_vp);
        //为ViewPager设置适配器
        mContentVp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new WeixinFragment());
        mFragments.add(new ContactsFragment());
        mFragments.add(new FindFragment());
        mFragments.add(new MeFragment());
    }

    /**
     * Fragment切换的适配器
     */
    class MyFragmentAdapter extends FragmentStatePagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weixin_btn:
                mContentVp.setCurrentItem(0);
                break;
            case R.id.contacts_btn:
                mContentVp.setCurrentItem(1);
                break;
            case R.id.find_btn:
                mContentVp.setCurrentItem(2);
                break;
            case R.id.me_btn:
                mContentVp.setCurrentItem(3);
                break;
        }
    }
}
