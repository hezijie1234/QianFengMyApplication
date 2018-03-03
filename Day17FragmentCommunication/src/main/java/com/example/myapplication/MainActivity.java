package com.example.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.fragment.FragmentA;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private FragmentA mFrag;
    private FragmentManager mFragMag;
    private int mCount;
    private Button mMainBtn;
    private TextView mMainText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
    }

    private void initView() {
        mMainText = (TextView)findViewById(R.id.main_text);
        mMainBtn = (Button) findViewById(R.id.main_btn);
        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentA frag = (FragmentA) mFragMag.findFragmentByTag("Fragment");
                frag.count();
            }
        });
    }

    private void initFragment() {
        mFrag = new FragmentA();
        mFragMag = getSupportFragmentManager();
        mFragMag.beginTransaction().add(R.id.frame_content,new FragmentA(),"Fragment").commit();
    }
    public void count(){
        mCount++;
        mMainText.setText("Mainactivity的count"+mCount);
    }
}
