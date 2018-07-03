package com.mayi.yun.playandroid.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 作者： wh
 * 时间：  2018/2/26
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BaseContract {

    public interface BasePresenter<T extends BaseView> {
        void attachView(T view);

        void detachView();
    }

    public interface BaseView {
        void showProgress();

        void hideProgress();

        void showMessage(String message);

        void showNoNetWork();

        <T> LifecycleTransformer<T> bindToLife();//绑定生命周期
    }
}
