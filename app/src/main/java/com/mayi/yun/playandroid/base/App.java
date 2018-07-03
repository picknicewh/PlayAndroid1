package com.mayi.yun.playandroid.base;

import android.app.Application;
import android.content.Context;

import com.mayi.yun.playandroid.di.component.ApplicationComponent;
import com.mayi.yun.playandroid.di.component.DaggerApplicationComponent;
import com.mayi.yun.playandroid.di.module.ApplicationModule;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class App extends Application {
    private static App mInstance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initComponent();
    }

    private void initComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder().
                applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static App getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
