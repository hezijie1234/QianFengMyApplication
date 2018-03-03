package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mList;
    private Button mIntentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData(){
        mList = new ArrayList<>();
        for(int i=0;i<30;i++){
            mList.add("item--"+i);
        }
    }

    private void initViews(){
        mIntentBtn = (Button)findViewById(R.id.intent);
        mListView = (ListView)findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1,
                android.R.id.text1,mList);
        mListView.setAdapter(adapter);
        mIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewActivity.class));
                overridePendingTransition(R.anim.translate_horizontal,R.anim.translate2_horizontal);
            }
        });
    }
}
