package com.qianfeng.day25_scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView，高度为最大，不能滚动
 * Created by xray on 17/1/7.
 */

public class MyListView extends ListView{
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context) {
        super(context);
    }

    //测量控件的高和宽
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得一个最大的高度
        int heightSpec =
                MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
