package com.qianfeng.day27_flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态添加内容
        FlowLayout layout = (FlowLayout) findViewById(R.id.flow_layout);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Button tv = new Button(this);
            tv.setText(random.nextInt(Integer.MAX_VALUE)+"");
            layout.addView(tv);
        }
    }
}
