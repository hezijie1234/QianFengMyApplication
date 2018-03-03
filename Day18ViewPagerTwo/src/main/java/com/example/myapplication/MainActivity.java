package com.example.myapplication;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<View> mViewPagerList;
    private List<Integer> mViewPagerId;
    private ImageView mImage;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImagePagers();
    }

    private void initImagePagers() {
        mViewPager = (ViewPager)findViewById(R.id.view_pager_test);
        mViewPagerId = new ArrayList<>();
        mViewPagerList = new ArrayList<>();
        mViewPagerId.add(R.mipmap.m_iconhot);
        mViewPagerId.add(R.mipmap.m_libao);
        mViewPagerId.add(R.mipmap.m_wode);
        mViewPagerId.add(R.mipmap.m_tese);
        mViewPagerId.add(R.mipmap.m_youxi);
        for(int i=0;i<mViewPagerId.size();i++){
            //一张图片使用一个imageview，
            mImage = new ImageView(MainActivity.this);
            mImage.setImageResource(mViewPagerId.get(i));
            mViewPagerList.add(mImage);
        }
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViewPagerList==null?0:mViewPagerList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //要去掉实现父类的方法，会报错
            container.addView(mViewPagerList.get(position));
            return mViewPagerList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewPagerList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
