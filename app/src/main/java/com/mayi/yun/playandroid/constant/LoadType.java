package com.mayi.yun.playandroid.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoadType {
    public static final int TYPE_REFRESH_SUCCESS = 1;
    public static final int TYPE_REFRESH_ERROR = 2;
    public static final int TYPE_LOAD_MORE_SUCCESS = 3;
    public static final int TYPE_LOAD_MORE_ERROR = 4;

    @IntDef({TYPE_REFRESH_SUCCESS, TYPE_REFRESH_ERROR, TYPE_LOAD_MORE_SUCCESS, TYPE_LOAD_MORE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
