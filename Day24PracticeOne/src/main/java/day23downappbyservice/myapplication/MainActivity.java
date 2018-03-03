package day23downappbyservice.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import day23downappbyservice.myapplication.utils.BitmapCache;
import day23downappbyservice.myapplication.utils.LoadBtimap;

public class MainActivity extends AppCompatActivity {
    private List<ImageView> mImageList;
    private ViewPager mImagePager;
    private List<String> mImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        BitmapCache.initCache(this);
        mImageUrl = new ArrayList<>();
        mImageList = new ArrayList<>();
        mImageUrl.add("http://www.1688wan.com/allimgs/img_iapp/201612/_1483101604058.jpg");
        mImageUrl.add("http://www.1688wan.com/allimgs/img_iapp/201612/_1482480933498.jpg");
        mImageUrl.add("http://www.1688wan.com/allimgs/img_iapp/201612/_1481885008504.jpg");
        for(int i= 0;i<mImageUrl.size();i++){
            final ImageView image = new ImageView(this);
            LoadBtimap.getBitmap(mImageUrl.get(i), new LoadBtimap.LoadBitmapListener() {
                @Override
                public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                    if(bitmap!=null){
                        image.setImageBitmap(bitmap);
                    }
                }
            });
            mImageList.add(image);
        }
    }

    private void initViews() {
        mImagePager = (ViewPager) findViewById(R.id.image_vp);
        mImagePager.setAdapter(new ImageViewPager());
    }

    class ImageViewPager extends PagerAdapter{

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageList.get(position));
            return mImageList.get(position);
        }

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
