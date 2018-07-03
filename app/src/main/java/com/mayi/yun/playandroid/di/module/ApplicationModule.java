package com.mayi.yun.playandroid.di.module;

import android.content.Context;

import com.mayi.yun.playandroid.base.App;
import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App mApplication) {
        this.mApplication = mApplication;
    }

    @PerApp
    @Provides
    @ContextLife("Application")
    public Context providerApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
