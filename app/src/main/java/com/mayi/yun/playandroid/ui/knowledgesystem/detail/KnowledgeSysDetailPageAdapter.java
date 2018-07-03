package com.mayi.yun.playandroid.ui.knowledgesystem.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class KnowledgeSysDetailPageAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<ArticleListFragment> articleListFragments;

    public KnowledgeSysDetailPageAdapter(FragmentManager fm, List<String > titles ,List<Integer> cids) {
        super(fm);
        this.titles = titles;
        articleListFragments = new ArrayList<>();
        if (titles == null ||cids==null) {
            return;
        }
        for (int i = 0 ; i<cids.size();i++) {
            ArticleListFragment articleListFragment = ArticleListFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("cid", cids.get(i));
            articleListFragment.setArguments(bundle);
            articleListFragments.add(articleListFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return articleListFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
