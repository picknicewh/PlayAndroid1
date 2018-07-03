package com.mayi.yun.playandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.mayi.yun.playandroid.di.scope.ContextLife;
import com.mayi.yun.playandroid.di.scope.PerActivity;

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
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
