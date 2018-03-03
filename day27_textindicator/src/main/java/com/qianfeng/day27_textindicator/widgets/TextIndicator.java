package com.qianfeng.day27_textindicator.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 类似联系人的文字指示器
 * Created by xray on 17/1/10.
 */

public class TextIndicator extends LinearLayout{
    public TextIndicator(Context context) {
        this(context,null);
    }

    public TextIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        //添加26个字母文字
        for (int i = 0; i < 26; i++) {
            //创建了字母的文字
            char ch = (char) ('A' + i);
            TextView tv = new TextView(context);
            tv.setText(Character.toString(ch));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            //添加到布局中
            this.addView(tv);
        }
    }

    private int mCurrentIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //判断用户的动作
        switch (action){
            case MotionEvent.ACTION_DOWN: //手指按下
                break;
            case MotionEvent.ACTION_MOVE: //手指移动
                //获得手指的坐标
                float x = event.getX();
                float y = event.getY();
                int childCount = getChildCount(); //文字的数量
                for (int i = 0; i < childCount; i++) {
                    if(mCurrentIndex == i){ //不显示相同的文字
                        continue;
                    }
                    TextView child = (TextView) getChildAt(i);//获得当前的文字子控件
                    //判断手指是否在当前控件内部
                    if(x >= child.getLeft() && x <= child.getRight()){
                        if( y>= child.getTop() && y <= child.getBottom()){
                            Toast.makeText(getContext(), child.getText().toString(), Toast.LENGTH_SHORT).show();
                            Log.i("xxx", "onTouchEvent: "+child.getText().toString());
                            mCurrentIndex = i;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:   //手指放开
                break;
        }
        return true;//true代表事件由当前控件进行处理
    }
}
