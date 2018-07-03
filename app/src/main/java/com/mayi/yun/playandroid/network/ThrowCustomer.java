package com.mayi.yun.playandroid.network;

import io.reactivex.functions.Consumer;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ThrowCustomer implements Consumer<Throwable> {
    public CallBack callBack;

    public ThrowCustomer(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void accept(Throwable e) throws Exception {
        ExceptionEngine.ResponseThrowable responseThrowable = ExceptionEngine.handleException(e);
        String message = responseThrowable.message;
        callBack.getErrorMessage(message);
    }

    public interface CallBack {
        void getErrorMessage(String message);
    }
}