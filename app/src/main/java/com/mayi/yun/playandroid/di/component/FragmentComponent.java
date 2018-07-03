package com.mayi.yun.playandroid.di.component;

import android.app.Activity;
import android.content.Context;

import com.mayi.yun.playandroid.di.module.FragmentModule;
import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerFragment;
import com.mayi.yun.playandroid.ui.home.HomeFragment;
import com.mayi.yun.playandroid.ui.hot.HotFragment;
import com.mayi.yun.playandroid.ui.knowledgesystem.KnowledgeSystemFragment;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleListFragment;
import com.mayi.yun.playandroid.ui.mine.MineFragment;

import dagger.Component;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@PerFragment
@Component(dependencies = {ApplicationComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HotFragment hotFragment);

    void inject(HomeFragment homeFragment);

    void inject(KnowledgeSystemFragment knowledgeSystemFragment);

    void inject(MineFragment mineFragment);

    void inject(ArticleListFragment articleListFragment);

}
