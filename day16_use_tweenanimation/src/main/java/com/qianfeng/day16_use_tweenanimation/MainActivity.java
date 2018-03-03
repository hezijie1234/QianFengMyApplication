package com.qianfeng.day16_use_tweenanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{"aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa","aaaaaaaa"});
        listView.setAdapter(adapter);
    }

    public void onClickChangeActivity(View view) {
        startActivity(new Intent(this,OtherActivity.class));
        //添加切换界面效果
        overridePendingTransition(R.anim.in,R.anim.out);
    }
}
