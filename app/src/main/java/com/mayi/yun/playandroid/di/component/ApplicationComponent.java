package com.mayi.yun.playandroid.di.component;

import android.content.Context;

import com.mayi.yun.playandroid.di.module.ApplicationModule;
import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerApp;

import dagger.Component;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@PerApp
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();// 对外提供ContextLife类型为"Application"的Context
}
