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
public class ForthFragment extends Fragment {
    private TabLayout mTab;
    private ViewPager mFragVp;
    private List<Fragment> mFragList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forth_fragment, container, false);
        initViews(view);
        initDatas(view);
        return view;
    }

    private void initDatas(View view) {
        mFragVp.setAdapter(new MyFraVp(getChildFragmentManager()));
        mTab.setupWithViewPager(mFragVp);
        mTab.getTabAt(0).setText("暴打星期三");
        mTab.getTabAt(1).setText("新游周刊");
    }

    private void initViews(View view) {
        mTab = (TabLayout) view.findViewById(R.id.tese_tab);
        mFragVp = (ViewPager) view.findViewById(R.id.tese_vp);
        mFragList = new ArrayList<>();
        mFragList.add(new TeSeLeft());
        mFragList.add(new TeSeRightFrag());
    }

    class MyFraVp extends FragmentPagerAdapter{

        public MyFraVp(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragList.get(position);
        }


        @Override
        public int getCount() {
            return mFragList==null? 0 :mFragList.size();
        }
    }
}
