package com.mayi.yun.playandroid;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.mayi.yun.playandroid.base.BaseActivity;
import com.mayi.yun.playandroid.base.BaseFragment;
import com.mayi.yun.playandroid.ui.home.HomeFragment;
import com.mayi.yun.playandroid.ui.hot.HotFragment;
import com.mayi.yun.playandroid.ui.hot.SearchActivity;
import com.mayi.yun.playandroid.ui.knowledgesystem.KnowledgeSystemFragment;
import com.mayi.yun.playandroid.ui.mine.MineFragment;
import com.mayi.yun.playandroid.utils.G;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    /**
     * 底部切换按钮
     */
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    /**
     * 第一次按推出的时间
     */
    private long mExitTime;
    /**
     * 页面列表
     */
    private List<BaseFragment> fragmentList;
    /**
     * 最后一次点击的位置
     */
    private int mLastFgIndex=0;

    @Override
    public void initInjector() {}

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mNavigation.setOnNavigationItemSelectedListener(this);
        initFragment();
        switchFragment(0);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(KnowledgeSystemFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        fragmentList.add(HotFragment.newInstance());
    }

    public void switchFragment(int position) {
        if (position>fragmentList.size()){
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BaseFragment targetFragment = fragmentList.get(position);
        BaseFragment lastFragment = fragmentList.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFragment);
        if (!targetFragment.isAdded()){
            ft.add(R.id.content,targetFragment);
        }
        ft.show(targetFragment);
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                setToolbarTitle("主页");
                switchFragment(0);
                break;
            case R.id.knowledge_system:
                setToolbarTitle("知识体系");
                switchFragment(1);
                break;
            case R.id.mine:
                setToolbarTitle("我的");
                switchFragment(2);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuHot:
                setToolbarTitle("热搜");
                  switchFragment(3);
                break;
            case R.id.menuSearch:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                G.showToast(this, getResources().getString(R.string.exit_system));
                mExitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
