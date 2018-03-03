package day23downappbyservice.day27textindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hezijie on 2017/1/10.
 */
public class ContactTextIndicat extends LinearLayout {


    public ContactTextIndicat(Context context) {
        this(context,null);
    }

    public ContactTextIndicat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        for (int i = 0; i < 26; i++) {
            char c = (char)('A'+i);
            TextView textView = new TextView(context);
            textView.setText(Character.toString(c));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            this.addView(textView);
        }
    }
private int mCurrentChild = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if(mCurrentChild==i){
                        continue;
                    }
                    TextView child = (TextView) getChildAt(i);
                    if(x>child.getLeft()&&x<child.getRight()&&y>child.getTop()&&y<child.getBottom()){
                        mCurrentChild = i;
                        Toast.makeText(getContext(),child.getText().toString(),Toast.LENGTH_SHORT).show();
                        Log.e("111", "onTouchEvent: "+child.getText().toString() );
                    }
                }
                break;
        }
        return true;
    }
}
