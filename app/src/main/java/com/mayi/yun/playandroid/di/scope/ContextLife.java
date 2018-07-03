package com.mayi.yun.playandroid.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：注解context的使用
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
