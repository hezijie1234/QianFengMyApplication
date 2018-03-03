package com.qianfeng.day18_viewpager_base;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //保存图片资源id数组
    private int[] mImageIds = {R.mipmap.def_bg,R.mipmap.def_head,R.mipmap.s1,R.mipmap.lb_bg};
    private List<ImageView> mImageViews;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageViews();
        initViews();
    }

    //创建ViewPager中的子视图集合
    private void initImageViews() {
        mImageViews = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(mImageIds[i]);
            mImageViews.add(imageView);
        }
    }

    private void initViews() {
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        //设置适配器
        mViewPager.setAdapter(new MyPagerAdapter());
        //设置页面切换的监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //滑动中的事件
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //完成页面切换后的事件
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "第"+(position+1)+"页", Toast.LENGTH_SHORT).show();
            }

            //滑动状态改变的事件
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //指定当前的页面
        mViewPager.setCurrentItem(2);
    }

    /**
     * ViewPager视图适配器
     */
    class MyPagerAdapter extends PagerAdapter{

        //返回视图的数量
        @Override
        public int getCount() {
            return mImageViews.size();
        }

        //创建并添加视图到ViewPager上
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加到ViewPager上
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        //销毁视图
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //从ViewPager上删除视图
            container.removeView(mImageViews.get(position));
        }

        //是否由对象创建视图
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
