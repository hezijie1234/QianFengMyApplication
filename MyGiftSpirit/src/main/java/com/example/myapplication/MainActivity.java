package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.fragment.FirstFragment;
import com.example.myapplication.fragment.ForthFragment;
import com.example.myapplication.fragment.SecondFragment;
import com.example.myapplication.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;
    private ForthFragment mForthFragment;
    private Fragment mCurrentFrag;
    private FragmentManager mFragmentManager;

    private RadioGroup mGroup;
    private List<Fragment> mFragmentList;
    private ViewPager mViewPager;
    private SlidingPaneLayout mSlidingPane;
    private View mContentView;
    private View mMainContent;

    private ImageView mBackImage;

    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    public Fragment getmFirstFragment() {
        return mFirstFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        listener();
    }
    public void setFragment(Fragment newFragment){
        if(newFragment.isAdded()){
            mFragmentManager.beginTransaction().hide(mCurrentFrag).show(newFragment).commit();
        }else{
            mFragmentManager.beginTransaction().hide(mCurrentFrag).add(R.id.frame_fl,newFragment).commit();
            Log.e("111", "setFragment: 执行了" );
        }
        mCurrentFrag = newFragment;
    }
    private void listener() {
//        for(int i=0;i<mGroup.getChildCount();i++){
//            if(mGroup.getChildAt(i) instanceof RadioButton){
//                final RadioButton btn = (RadioButton) mGroup.getChildAt(i);
//                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                        switch (btn.getId()){
//                            case R.id.first_btn:
//                                setFragment(mFirstFragment);
//                                Log.e("111", "onCheckedChanged: 1被点击了" );
//                                break;
//                            case R.id.second_btn:
//                                setFragment(mSecondFragment);
//                                Log.e("111", "onCheckedChanged: 2被点击了" );
//                                break;
//                            case R.id.third_btn:
//                                setFragment(mThirdFragment);
//                                Log.e("111", "onCheckedChanged: 3被点击了" );
//                                break;
//                            case R.id.forth_btn:
//                                setFragment(mForthFragment);
//                                Log.e("111", "onCheckedChanged: 4被点击了" );
//                                break;
//                        }
//                    }
//                });
//            }
//        }
        //使用这种方法设置监听效果更好
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.first_btn:
                        setFragment(mFirstFragment);
                        break;
                    case R.id.second_btn:
                        setFragment(mSecondFragment);
                        break;
                    case R.id.third_btn:
                        setFragment(mThirdFragment);
                        break;
                    case R.id.forth_btn:
                        setFragment(mForthFragment);
                        break;
                }
            }
        });
        mMainContent.setAlpha(1);
        mSlidingPane.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mBackImage.setImageResource(R.mipmap.menu_background);
                mMainContent.setScaleX(1-slideOffset*0.02f);
                mMainContent.setScaleY(1-slideOffset*0.5f);
                mMainContent.setAlpha(1.0f);
            }

            @Override
            public void onPanelOpened(View panel) {
                mMainContent.setAlpha(1.0f);
            }

            @Override
            public void onPanelClosed(View panel) {
                mBackImage.setImageBitmap(null);
            }
        });
    }

    private void initView() {
        mGroup = (RadioGroup)findViewById(R.id.radio_group_rg);
       // mViewPager = (ViewPager)findViewById(R.id.fragment_viewpager);
       // mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        mSlidingPane = (SlidingPaneLayout) findViewById(R.id.sliding_pane);
        // = findViewById(R.id.content_test);
        mMainContent = findViewById(R.id.main_activity);
        mBackImage = (ImageView) findViewById(R.id.main_activity_bcimage);
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentList = new ArrayList<>();
        mFirstFragment = new FirstFragment();
        mSecondFragment= new SecondFragment();
        mThirdFragment = new ThirdFragment();
        mForthFragment = new ForthFragment();
//        mFragmentList.add(mFirstFragment);
//        mFragmentList.add(mSecondFragment);
//        mFragmentList.add(mThirdFragment);
//        mFragmentList.add(mForthFragment);
        mFragmentManager.beginTransaction().add(R.id.frame_fl,mFirstFragment).commit();
        mCurrentFrag = mFirstFragment;
    }
//    class MyFragmentAdapter extends FragmentPagerAdapter {
//
//        public MyFragmentAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//    }

}
