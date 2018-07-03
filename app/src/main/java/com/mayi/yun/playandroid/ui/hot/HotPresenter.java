package com.mayi.yun.playandroid.ui.hot;

import com.mayi.yun.playandroid.base.BasePresenter;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;
import com.mayi.yun.playandroid.bean.Result;
import com.mayi.yun.playandroid.constant.Constant;
import com.mayi.yun.playandroid.network.RetrofitManager;
import com.mayi.yun.playandroid.network.RxSchedulers;
import com.mayi.yun.playandroid.network.ThrowCustomer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 作者： wh
 * 时间：  2018/3/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HotPresenter extends BasePresenter<HotContract.View> implements HotContract.Presenter {

    @Inject
    public HotPresenter() {

    }


    @Override
    public void loadHotData() {
        Observable<Result<List<Friend>>> observableFriend = RetrofitManager.getApiService().getHotFriends();
        Observable<Result<List<HotKey>>> observableHotKey = RetrofitManager.getApiService().getHotKeys();
        Observable.zip(observableFriend, observableHotKey, new BiFunction<Result<List<Friend>>, Result<List<HotKey>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(Result<List<Friend>> listResult, Result<List<HotKey>> listResult2) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put(Constant.CONTENT_HOT_FRIEND_KEY, listResult.getData());
                objMap.put(Constant.CONTENT_HOT_KEY, listResult2.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers())
                .compose(mView.<Map<String, Object>>bindToLife())
                .subscribe(new Consumer<Map<String, Object>>() {
                    @Override
                    public void accept(Map<String, Object> map) throws Exception {
                        List<HotKey> hotKeys = (List<HotKey>) map.get(Constant.CONTENT_HOT_KEY);
                        List<Friend> friends = (List<Friend>) map.get(Constant.CONTENT_HOT_FRIEND_KEY);
                        mView.setHotData(hotKeys, friends);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        mView.showMessage(message);
                    }
                }));

    }

    @Override
    public void onRefresh() {
        loadHotData();
    }


}
