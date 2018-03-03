package com.hezijie.day25.actionbar;

import android.database.Cursor;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("这是action的标题");
        mActionBar.setSubtitle("这是action的副标题");
        //下面3行能设置actionbar的图标
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setLogo(R.mipmap.ic_launcher);
        //设置返回图标
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                Toast.makeText(this,"编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(this,"添加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                SearchView searchView = (SearchView) item.getActionView();
                initSearchView(searchView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSearchView(SearchView searchView) {
        final Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        //必须要v4包中的适配器
        final SimpleCursorAdapter adapter =  new SimpleCursorAdapter(this,
                android.R.layout.simple_expandable_list_item_1,
                cursor,new String []{},new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在这里向服务器提交查询内容
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //监听查询输入内容的改变
                Cursor cursor1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI
                        , new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME}
                        , ContactsContract.Contacts.DISPLAY_NAME+"like '%"+newText+"%'", null, null);
                adapter.swapCursor(cursor1);
                return true;
            }
        });
    }

}
