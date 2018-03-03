package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2016/12/27.
 */
public class SecondFragment extends Fragment{
    private List<Fragment> mFragmentsList;
    private ViewPager mStartSysVp;
    private TabLayout mStartSysTab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        initFragments();
        initView(view);
        return view;
    }

    private void initFragments() {
        mFragmentsList = new ArrayList<>();
        mFragmentsList.add(new FragStartTest());
        mFragmentsList.add(new FragStartSys());
    }

    private void initView(View view) {

        mStartSysVp = (ViewPager) view.findViewById(R.id.start_systemt_vp);
        mStartSysTab =  (TabLayout) view.findViewById(R.id.start_systemt_tab);
        mStartSysVp.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        mStartSysTab.setupWithViewPager(mStartSysVp);
    }
    class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentsList==null ? 0 :mFragmentsList.size();
        }
    }
}
