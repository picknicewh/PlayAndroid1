package com.mayi.yun.playandroid.di.module;

import android.app.Service;
import android.content.Context;

import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerService;

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
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service mService) {
        this.mService = mService;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context provideServiceContext() {
        return mService;
    }
}
