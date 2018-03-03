package com.example.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private LeftFragment leftFragment;
    private RightFragment rightFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
    }

    private void initFragments() {
        leftFragment = new LeftFragment();
        rightFragment = new RightFragment();
        leftFragment.setOnRightFragment(rightFragment);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_left_fl,leftFragment)
                .add(R.id.frame_right_fl,rightFragment).commit();
    }
}
