package com.qianfeng.day27_flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 流式布局
 * Created by hengheng on 2016/6/23.
 * onMeasure测量宽高 -> onLayout排列子控件 -> onDraw绘制
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //排列子控件
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int cl = 0; //当前子控件的左边
        int ct = 0; //当前子控件的上边
        int width = r - l;//布局的宽
        int maxHeight = 0;//最大的行高
        //排列每个子控件
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int cw = child.getMeasuredWidth();//获得子控件的测量后的宽度
            int ch = child.getMeasuredHeight();//获得子控件的测量后的高度
            int cr = cl + cw; //子控件的右边
            int cb = ct + ch; //子控件的下边
            //判断是否换行
            if(cr > width){
                cl = 0;                 //子控件换到下行的左边
                ct = ct + maxHeight;    //上边坐标累加行高
                cr = cl + cw;           //右边累加宽度
                cb = ct + ch;           //下边为上边加高度
                maxHeight = ch;         //作为本行第一个控件，高度为此控件高度
            }else{
                maxHeight = Math.max(maxHeight,ch);//求得行中最大的高度
            }
            //排列子控件
            child.layout(cl,ct,cr,cb);
            //下个控件的左边，为当前控件的右边，实现水平排列
            cl = cr;
        }
    }

    //测量布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有的子控件
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }
}
