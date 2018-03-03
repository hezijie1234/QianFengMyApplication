package day23downappbyservice.day25toolbar;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitle("这是标题");
        mToolBar.setSubtitle("这是副标题");
        mToolBar.setLogo(R.mipmap.ic_launcher);
        //设置导航按钮，在左边显示，
        mToolBar.setNavigationIcon(android.R.drawable.ic_menu_compass);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置在toolbar上的按钮的监听
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //更具id找到所属的按钮
                switch (item.getItemId()){
                    case R.id.search :
                        SearchView view = (SearchView) item.getActionView();
                        initSearchView(view);
                        break;
                }
                return true;
            }
        });
    }

    private void initSearchView(final SearchView searchView) {
        final Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        //必须要v4包中的适配器
        final SimpleCursorAdapter adapter =  new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String []{ContactsContract.Contacts.DISPLAY_NAME},
        new int[]{android.R.id.text1},
        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
                        , ContactsContract.Contacts.DISPLAY_NAME+" like '%"+newText+"%'", null, null);
                adapter.swapCursor(cursor1);
                return true;
            }
        });
        //将系统查询的值显示到searchView中
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor1 = searchView.getSuggestionsAdapter().getCursor();
                while (cursor1.moveToPosition(position)){
                    String string = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    searchView.setQuery(string,true);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
