package com.example.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.com.hezijie.fragmentof.MyFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragManager=null;
    private FragmentTransaction mTransaction;
    private MyFragment mFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  initFtagment();
    }

    private void initFtagment() {
    //    mFrag = new MyFragment();
//        mFragManager = getSupportFragmentManager();
//        mTransaction = mFragManager.beginTransaction();
//        mTransaction.add(R.id.frame_content,mFrag);
//        mTransaction.commit();
   //     getSupportFragmentManager().beginTransaction().add(R.id.frame_content,mFrag).commit();
    }

}
