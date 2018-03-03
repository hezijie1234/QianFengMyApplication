package com.androidxx.yangjw.day31_project_mvp_demo.view.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.androidxx.yangjw.day31_project_mvp_demo.BaseAtivity;
import com.androidxx.yangjw.day31_project_mvp_demo.R;
import com.androidxx.yangjw.day31_project_mvp_demo.view.category.fragment.CategoryFragment;
import com.androidxx.yangjw.day31_project_mvp_demo.view.helper.fragment.HelperFragment;
import com.androidxx.yangjw.day31_project_mvp_demo.view.home.fragment.HomeFragment;
import com.androidxx.yangjw.day31_project_mvp_demo.view.setting.fragment.SettingFragment;

public class HomeActivity extends BaseAtivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mBottomMenuRG;
    private FragmentManager mFragmentManager;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private HelperFragment helperFragment;
    private SettingFragment settingFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        mBottomMenuRG = (RadioGroup) findViewById(R.id.main_bottom_menu_rg);

        mBottomMenuRG.setOnCheckedChangeListener(this);
        controlFragment(homeFragment);
//        mBottomMenuRG.check(R.id.main_bottom_menu_home_rb);

    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        homeFragment = HomeFragment.newInstance();
        categoryFragment = CategoryFragment.newInstance();
        helperFragment = HelperFragment.newInstance();
        settingFragment = SettingFragment.newInstance();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_bottom_menu_home_rb:
                controlFragment(homeFragment);
                break;
            case R.id.main_bottom_menu_category_rb:
                controlFragment(categoryFragment);
                break;
            case R.id.main_bottom_menu_helper_rb:
                controlFragment(helperFragment);
                break;
            case R.id.main_bottom_menu_setting_rb:
                controlFragment(settingFragment);
                break;
        }
    }

    private void controlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        if (!fragment.isAdded()) {

            fragmentTransaction.add(R.id.main_content_layout,fragment);

        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
        currentFragment = fragment;

    }
}
