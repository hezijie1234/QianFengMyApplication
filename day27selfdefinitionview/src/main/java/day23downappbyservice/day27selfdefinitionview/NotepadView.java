package day23downappbyservice.day27selfdefinitionview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by hezijie on 2017/1/10.
 */
public class NotepadView extends EditText{

    private Paint mPaint;
    public NotepadView(Context context) {
        this(context,null);
    }

    public NotepadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NotepadView);
        int color = typedArray.getColor(R.styleable.NotepadView_lineColor, Color.BLUE);
        int integer = typedArray.getInteger(R.styleable.NotepadView_lineHeight, 3);
        typedArray.recycle();
        mPaint.setColor(color);
        mPaint.setStrokeWidth(integer);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStrokeWidth(3);
        for (int i = 0; i < getLineCount(); i++) {
            int y = paddingTop+(i+1)*getLineHeight();
            canvas.drawLine(getPaddingLeft(),y,getWidth()-getPaddingRight(),y,mPaint);
        }

    }
}
