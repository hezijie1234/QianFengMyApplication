package day23downappbyservice.day25honrizontalscroll;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        initData();
    }

    private void initData() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams((width-10)/2, 200);
        ll.setMargins(2,0,2,0);
        for (int i = 0; i < 10; i++) {
            ImageView image = new ImageView(this);
            image.setImageResource(R.mipmap.ic_launcher);
            mLinear.addView(image,ll);
        }
    }
}
