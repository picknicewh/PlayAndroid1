package com.mayi.yun.playandroid.bean;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class Result<T> {
    private int errorCode;
    private Object errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
