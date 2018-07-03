package com.mayi.yun.playandroid.ui.knowledgesystem.detail;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseActivity;
import com.mayi.yun.playandroid.ui.hot.SearchActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class KnowledgeSysDetailActivity extends BaseActivity {
    /**
     * 标题栏目
     */
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    /**
     * 页面
     */
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    /**
     * 数据列表
     */
    private ArrayList<String> titles;
    /**
     * 数据列表
     */
    private ArrayList<Integer> cids;
    /**
     * 标题
     */
    private String title;
    /**
     * 页面适配器
     */
    private KnowledgeSysDetailPageAdapter pageAdapter;

    @Override
    public void initInjector() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_knowledge_sys_detail;
    }

    @Override
    public void initView() {
        titles = getIntent().getStringArrayListExtra("titles");
        cids = getIntent().getIntegerArrayListExtra("cids");
        title = getIntent().getStringExtra("title");
        setToolbarTitle(title);
        pageAdapter = new KnowledgeSysDetailPageAdapter(getSupportFragmentManager(), titles, cids);
        mViewpager.setAdapter(pageAdapter);
        tablayout.setupWithViewPager(mViewpager);

    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuShare:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_url, getString(R.string.app_name), title, "ID"));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
                break;
            case R.id.menuSearch:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
