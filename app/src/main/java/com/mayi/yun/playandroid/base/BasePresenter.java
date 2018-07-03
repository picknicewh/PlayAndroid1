package com.mayi.yun.playandroid.base;

/**
 * 作者： wh
 * 时间：  2018/2/26
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    protected T mView;

    @Override
    public void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
