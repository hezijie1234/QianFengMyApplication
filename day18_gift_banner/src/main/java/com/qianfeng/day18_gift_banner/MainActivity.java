package com.qianfeng.day18_gift_banner;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.qianfeng.day18_gift_banner.entities.Gift;
import com.qianfeng.day18_gift_banner.utils.Constants;
import com.qianfeng.day18_gift_banner.utils.ImageLoader;
import com.qianfeng.day18_gift_banner.utils.JSONLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JSONLoader mJSONLoader ;
    private Gift mGift;
    private ViewPager mBannerVp;
    //在ViewPager中子控件的集合
    private List<View> mViews;
    //存放圆点ImageView的集合
    private List<ImageView> mDots;
    //显示圆点的布局
    private LinearLayout mDotLayout;
    //原来选中圆点的索引
    private int mCurrentDot = 0;
    private Handler mHandler = null;
    //ViewPager当前页面的索引
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
    }

    private void initViews() {
        mBannerVp = (ViewPager) findViewById(R.id.banner_vp);
        mDotLayout = (LinearLayout) findViewById(R.id.dot_layout);
        //定义ViewPager切换的监听，实现圆点的改变
        mBannerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            /*[code]三个方法的执行顺序为：用手指滑动时，最先执行一遍onPageScrollStateChanged（1——press），
            然后不断执行onPageScrolled，放手指的时候，直接立即执行一次onPageScrollStateChanged（2--up），
            然后立即执行一次onPageSelected，然后再不断执行onPageScrollStateChanged，
            最后执行一次onPageScrollStateChanged（0--end）。
            *
            * */
            @Override
            public void onPageSelected(int position) {
                //将原来的圆点设置为未选状态
                mDots.get(mCurrentDot).setEnabled(true);
                //将现在的圆点设置为已选状态
                mDots.get(position).setEnabled(false);
                mCurrentDot = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mJSONLoader = new JSONLoader();
        final Gson gson = new Gson();
        mHandler = new Handler();
        //连接网络
        mJSONLoader.loadJSON(Constants.GIFT_URL + 1,
                new JSONLoader.OnJSONLoadListener() {
                    @Override
                    public void onJSONLoadComplete(String json) {
                        //JSON解析
                        mGift = gson.fromJson(json,Gift.class);
                        Log.i("xxx", "ad: "+mGift.getAd());
                        initPagerViews(mGift.getAd());
                        //设置ViewPager的适配器
                        mBannerVp.setAdapter(new BannerAdapter());
                        //初始化圆点指示器
                        initDots(mGift.getAd().size());
                        //启动自动轮播
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //修改页面索引
                                if(mCurrentPage == mGift.getAd().size()){
                                    mCurrentPage = 0;
                                }else{
                                    mCurrentPage++;
                                }
                                //修改当前的页面
                                mBannerVp.setCurrentItem(mCurrentPage);
                                //重复执行任务
                                mHandler.postDelayed(this,2000);
                            }
                        },2000);
                    }
                });

    }

    //初始化ViewPager中所有的子控件
    private void initPagerViews(List<Gift.AdBean> ads){
        mViews = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for(Gift.AdBean ad : ads){
            //加载布局
            View view = inflater.inflate(R.layout.image_item, null);
            Log.e("111","view"+view.toString());
            //获得ImageView
            final ImageView imageView = (ImageView) view.findViewById(R.id.image_iv);
            Log.e("111","imageview"+imageView.toString());
            //图片异步加载
            new ImageLoader().loadImage(Constants.BASE_URL + ad.getIconurl(),
                    new ImageLoader.OnImageLoadListener() {
                        @Override
                        public void onImageLoadComplete(Bitmap bitmap) {
                            if(bitmap != null){
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
            mViews.add(view);
        }
    }

    //初始化圆点ImageView集合
    private void initDots(int count){
        mDots = new ArrayList<>();
        //创建布局参数
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置左右间距
        lp.setMargins(5,0,5,0);
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(this);
            //设置选择器作为图片
            imageView.setImageResource(R.drawable.dot_selector);
            mDots.add(imageView);
            //动态添加到LinearLayout
            mDotLayout.addView(imageView,lp);
        }
        //默认选中第一个圆点
        mDots.get(0).setEnabled(false);
    }

    /**
     * 广告轮播图片适配器
     */
    class BannerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViews == null ? 0 : mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view  == object;
        }
    }
}
