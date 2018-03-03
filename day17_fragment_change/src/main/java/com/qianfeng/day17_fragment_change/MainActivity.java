package com.qianfeng.day17_fragment_change;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qianfeng.day17_fragment_change.fragment.ContactsFragment;
import com.qianfeng.day17_fragment_change.fragment.FindFragment;
import com.qianfeng.day17_fragment_change.fragment.MeFragment;
import com.qianfeng.day17_fragment_change.fragment.WeixinFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment mWeixinFrag;
    private Fragment mFindFrag;
    private Fragment mContactsFrag;
    private Fragment mMeFrag;
    private FragmentManager mFragMgr;
    private Fragment mCurrentFrag; //当前显示的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
    }

    private void initViews() {
        findViewById(R.id.weixin_btn).setOnClickListener(this);
        findViewById(R.id.me_btn).setOnClickListener(this);
        findViewById(R.id.find_btn).setOnClickListener(this);
        findViewById(R.id.contacts_btn).setOnClickListener(this);
    }

    private void initFragments() {
        mContactsFrag = new ContactsFragment();
        mFindFrag = new FindFragment();
        mMeFrag = new MeFragment();
        mWeixinFrag = new WeixinFragment();
        mFragMgr = getSupportFragmentManager();
        //添加微信界面
        mFragMgr.beginTransaction()
                .add(R.id.content_layout,mWeixinFrag)
                .commit();
        //当前显示的是微信
        mCurrentFrag = mWeixinFrag;
    }

    //Fragment的替换
    private void changeFragment(Fragment newFrag){
        mFragMgr.beginTransaction()
                .replace(R.id.content_layout,newFrag)
                .commit();
    }

    //使用show和hide来优化Fragment的切换
    public void switchFragment(Fragment newFrag){
        //判断新的Fragment是否添加到Activity上了
        if(newFrag.isAdded()){
            //已经添加的情况下，隐藏原来的Fragment，显示新的Fragment
            mFragMgr.beginTransaction()
                    .hide(mCurrentFrag)
                    .show(newFrag)
                    .commit();
        }else{
            //没有添加的情况下，隐藏原来的Fragment，添加新的Fragment
            mFragMgr.beginTransaction()
                    .hide(mCurrentFrag)
                    .add(R.id.content_layout,newFrag)
                    .commit();
        }
        //设置新的Fragment为当前的Fragment
        mCurrentFrag = newFrag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weixin_btn:
                switchFragment(mWeixinFrag);
                break;
            case R.id.contacts_btn:
                switchFragment(mContactsFrag);
                break;
            case R.id.find_btn:
                switchFragment(mFindFrag);
                break;
            case R.id.me_btn:
                switchFragment(mMeFrag);
                break;
        }
    }
}
