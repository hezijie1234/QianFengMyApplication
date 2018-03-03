package com.qianfeng.day19_tablayout_viewpager_fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.day19_tablayout_viewpager_fragment.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 微信子页面中还包含两个子页面
 * Created by xray on 16/12/27.
 */

public class WeixinFragment extends Fragment {

    private ViewPager mWeixinVp;
    private TabLayout mTabLayout;
    //子页面集合
    private List<Fragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.weixin_frag,container,false);
        Log.i("xxx", "onCreateView: WeixinFragment");
        initFragments();
        initViews(vi);
        return vi;
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new WeixinFragmentA());
        mFragments.add(new WeixinFragmentB());
    }

    private void initViews(View vi) {
        mWeixinVp = (ViewPager) vi.findViewById(R.id.weixin_vp);
        mTabLayout = (TabLayout) vi.findViewById(R.id.tab_layout);
        //初始化ViewPager,在Fragment中获得FragmentManager，要使用getChildFragmentManager
        mWeixinVp.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
        //初始化TabLayout
        //设置TabLayout和ViewPager的联动
        mTabLayout.setupWithViewPager(mWeixinVp);
        //给TabLayout的导航按钮设置文字
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setText("按钮----"+(i+1));
        }
        //手动给TabLayout添加按钮
//        TabLayout.Tab tab1 = mTabLayout.newTab();
//        tab1.setText("A");
//        mTabLayout.addTab(tab1);
//        TabLayout.Tab tab2 = mTabLayout.newTab();
//        tab2.setText("B");
//        mTabLayout.addTab(tab2);
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                mWeixinVp.setCurrentItem(position);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    //Fragment的ViewPager适配器
    class MyFragmentAdapter extends FragmentPagerAdapter{

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
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("xxx", "onDestroyView: WeixinFragment");
    }

}
