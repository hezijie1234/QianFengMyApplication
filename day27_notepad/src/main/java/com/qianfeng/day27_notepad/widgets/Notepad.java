package com.qianfeng.day27_notepad.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.qianfeng.day27_notepad.R;

/**
 * 带下划线效果的EditText
 * Created by xray on 17/1/10.
 */

public class Notepad extends EditText{

    private Paint mPaint;

    private static final String TAG = "xxx";

    public Notepad(Context context) {
        this(context,null);
    }

    public Notepad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        //创建画笔
        mPaint = new Paint();
        //从布局文件中读取自定义属性
        //obtainStyledAttributes获得属性集合，
        //第一个参数是AttributeSet属性集合，第二个参数是attrs中定义的属性集合名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Notepad);
        //再从TypedArray中获得属性值
        int color = typedArray.getColor(R.styleable.Notepad_lineColor, Color.BLUE);
        int line = typedArray.getInteger(R.styleable.Notepad_lineHeight, 3);
        //将TypedArray回收
        typedArray.recycle();
        //设置颜色
        mPaint.setColor(color);
        //设置线条粗细
        mPaint.setStrokeWidth(line);
    }

    //图形绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i(TAG, "onDraw: "+System.currentTimeMillis());
        int paddingLeft = getPaddingLeft();     //左填充
        int paddingRight = getPaddingRight();   //右填充
        int paddingTop = getPaddingTop();       //上填充
        int width = getWidth() - paddingRight;  //线条的宽度
        int lineHeight = getLineHeight();       //行高
        //给每一行文字下都画一条线
        int lineCount = getLineCount(); //行数
        for (int i = 0; i < lineCount; i++) {
            int line = paddingTop + (i + 1) * lineHeight;
            canvas.drawLine(paddingLeft,line,width,line,mPaint);
        }
    }
}
