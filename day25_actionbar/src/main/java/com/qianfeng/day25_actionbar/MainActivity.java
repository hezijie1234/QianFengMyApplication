package com.qianfeng.day25_actionbar;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得ActionBar对象
        mActionBar = getSupportActionBar();
        //设置标题
        mActionBar.setTitle("Hello");
        mActionBar.setSubtitle("ActionBar");
        //显示图标
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setLogo(R.mipmap.ic_launcher);
        //动画效果隐藏和显示ActionBar
        mActionBar.setShowHideAnimationEnabled(true);
        //显示返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                mActionBar.hide();
                break;
            case R.id.item_search:
                initSearchView((SearchView)item.getActionView());
                break;
            case android.R.id.home: //点击导航按钮
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSearchView(final SearchView searchView){
        //给SearchView添加建议功能

        //创建建议适配器
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(adapter);

        //根据文字的改变，修改搜索内容
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交搜索内容
                Toast.makeText(MainActivity.this, "搜索："+query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字改变后，重新查询
                Cursor cursor = getContentResolver().query(
                        ContactsContract.Contacts.CONTENT_URI,
                        new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                        ContactsContract.Contacts.DISPLAY_NAME +" like '"+newText+"%'", null, null);
                adapter.swapCursor(cursor);
                return true;
            }
        });

        //添加建议监听
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                //获得建议内容，显示到搜索框中
                Cursor cursor1 = searchView.getSuggestionsAdapter().getCursor();
                if(cursor1.moveToPosition(position)){
                    String str = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    searchView.setQuery(str,true);
                }
                return true;
            }
        });
    }

    public void onClickShow(View view) {
        mActionBar.show();
    }
}
