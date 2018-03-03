package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.ForthFragment;
import com.example.myapplication.fragment.SecondFragment;
import com.example.myapplication.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

        private RadioGroup mGroup;
        private List<Fragment> mFragmentList;
        private ViewPager mViewPager;
        //    private Fragment mCurrentFrag;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initFragments();
            initView();
            listener();
        }

        private void listener() {
            mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.first_btn:
                            mViewPager.setCurrentItem(0);
                            break;
                        case R.id.second_btn:
                            mViewPager.setCurrentItem(1);
                            break;
                        case R.id.third_btn:
                            mViewPager.setCurrentItem(2);
                            break;
                        case R.id.forth_btn:
                            mViewPager.setCurrentItem(3);
                            break;
                    }
                }
            });
        }

        private void initView() {
            mGroup = (RadioGroup)findViewById(R.id.radio_group_rg);
            mViewPager = (ViewPager)findViewById(R.id.fragment_viewpager);
            mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        }

        private void initFragments() {
            mFragmentList = new ArrayList<>();
            FirstFragment firstFragment = new FirstFragment();
            SecondFragment secondFragment = new SecondFragment();
            ThirdFragment thirdFragment = new ThirdFragment();
            ForthFragment forthFragment = new ForthFragment();
            mFragmentList.add(firstFragment);
            mFragmentList.add(secondFragment);
            mFragmentList.add(thirdFragment);
            mFragmentList.add(forthFragment);
        }
        class MyFragmentAdapter extends FragmentPagerAdapter{

            public MyFragmentAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        }
}
