package com.mayi.yun.playandroid.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：通用的Rx线程转换类
 * 版本说明：参考:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0819/3327.html
 * 附加注释：
 * 主要接口：
 */
public class RxSchedulers {
    static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return (upstream).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }


}
