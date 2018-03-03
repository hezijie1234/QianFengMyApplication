package com.qianfeng.day25_toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获得Toolbar
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        //代替ActionBar
        setSupportActionBar(mToolbar);
        //设置标题
        mToolbar.setTitle("Hello");
        mToolbar.setSubtitle("Toolbar");
        mToolbar.setLogo(R.mipmap.ic_launcher);
        //导航按钮
        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_compass);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Action按钮的监听
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_add:
                        Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
