package day23downappbyservice.day25scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by hezijie on 2017/1/8.
 */
public class MyScorllView extends ListView {
    public MyScorllView(Context context) {
        super(context);
    }

    public MyScorllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
