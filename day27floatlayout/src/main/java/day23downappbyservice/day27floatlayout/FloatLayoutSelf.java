package day23downappbyservice.day27floatlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hezijie on 2017/1/10.
 */
public class FloatLayoutSelf extends ViewGroup {
    public FloatLayoutSelf(Context context) {
        this(context,null);
    }

    public FloatLayoutSelf(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        int childTop = 0;
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int childWidth = getChildAt(i).getMeasuredWidth();
            int childHeight = getChildAt(i).getMeasuredHeight();
            int childRight = childLeft+childWidth;
            int childBottom = childHeight+childTop;
            if(childRight>(r-l)){
                childLeft = 0;
                childTop = childTop+maxHeight;
                childRight = childLeft+childWidth;
                childBottom = childTop+childHeight;
            }else{
                maxHeight = Math.max(maxHeight,childHeight);
            }
            view.layout(childLeft,childTop,childRight,childBottom);
            childLeft = childRight;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.measureChildren(widthMeasureSpec,heightMeasureSpec);
    }
}
