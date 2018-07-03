package com.mayi.yun.playandroid.ui.hot;

import com.mayi.yun.playandroid.base.BaseContract;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface HotContract {
    interface View extends BaseContract.BaseView {
        void setHotData(List<HotKey> hotKeyList,List<Friend> friendList );

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadHotData();

        void onRefresh();


    }
}
