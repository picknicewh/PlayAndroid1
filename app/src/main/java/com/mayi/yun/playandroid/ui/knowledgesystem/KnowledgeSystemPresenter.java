package com.mayi.yun.playandroid.ui.knowledgesystem;

import com.mayi.yun.playandroid.base.BasePresenter;
import com.mayi.yun.playandroid.bean.KnowledgeSystem;
import com.mayi.yun.playandroid.bean.Result;
import com.mayi.yun.playandroid.network.RetrofitManager;
import com.mayi.yun.playandroid.network.RxSchedulers;
import com.mayi.yun.playandroid.network.ThrowCustomer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class KnowledgeSystemPresenter extends BasePresenter<KnowledgeSystemContract.View> implements KnowledgeSystemContract.Presenter {

    @Inject
    public KnowledgeSystemPresenter() {

    }

    @Override
    public void getKnowledgeSystemList() {
        RetrofitManager.getApiService()
                .getKnowledgeSystem()
                .compose(RxSchedulers.<Result<List<KnowledgeSystem>>>applySchedulers())
                .compose(mView.<Result<List<KnowledgeSystem>>>bindToLife())
                .subscribe(new Consumer<Result<List<KnowledgeSystem>>>() {
                    @Override
                    public void accept(Result<List<KnowledgeSystem>> listResult) throws Exception {
                        List<KnowledgeSystem> knowledgeSystemList = listResult.getData();
                        mView.setKnowledgeSystemList(knowledgeSystemList);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        mView.showMessage(message);
                    }
                }));
    }
}
