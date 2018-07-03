package com.mayi.yun.playandroid.di.component;

import android.app.Activity;
import android.content.Context;

import com.mayi.yun.playandroid.di.module.ActivityModule;
import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerActivity;
import com.mayi.yun.playandroid.ui.hot.SearchActivity;

import dagger.Component;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class} ,modules = {ActivityModule.class})
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(SearchActivity searchActivity);
}
