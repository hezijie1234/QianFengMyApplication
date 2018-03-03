package com.qianfeng.day27_flowlayout.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局
 * Created by xray on 17/1/10.
 */

public class FlowLayout extends ViewGroup{

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //排列所有的子控件控件
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount(); //子控件的个数
        int width = r - l;                //流式布局的宽度
        int childLeft = 0;                  //子控件的左边
        int childTop = 0;                   //子控件的上边
        int maxHeight = 0;                  //一行中的最大高度
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i); //当前的子控件
            int childWidth = child.getMeasuredWidth(); //获得测量后的控件宽度
            int childHeight = child.getMeasuredHeight();//获得控件高度
            int childRight = childLeft + childWidth;    //控件右边
            int childBottom = childTop + childHeight;   //控件底部
            //判断控件是否超过了布局的宽度
            if(childRight > width){
                childLeft = 0;                  //控件从下一行的左边开始
                childTop = childTop + maxHeight; //高度为最大高度加上原高度
                childRight = childLeft + childWidth;
                childBottom = childTop + childHeight;
            }else{
                //计算最大高度
                maxHeight = Math.max(maxHeight,childHeight);
            }
            //排列子控件
            child.layout(childLeft,childTop,childRight,childBottom);
            //往后横下排列
            childLeft = childRight;
        }
    }

    //测量控件的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有的子控件
        this.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
