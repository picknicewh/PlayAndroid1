package com.mayi.yun.playandroid.ui.knowledgesystem.detail;

import com.mayi.yun.playandroid.base.BasePresenter;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.bean.Result;
import com.mayi.yun.playandroid.constant.LoadType;
import com.mayi.yun.playandroid.network.RetrofitManager;
import com.mayi.yun.playandroid.network.RxSchedulers;
import com.mayi.yun.playandroid.network.ThrowCustomer;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * 作者： wh
 * 时间：  2018/3/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ArticleListPresenter extends BasePresenter<ArticleListContract.View> implements ArticleListContract.Presenter {
    private int mPage;
    private int cid;
    private boolean isRefresh;

    @Inject
    public ArticleListPresenter() {
        this.isRefresh = true;
    }


    @Override
    public void getKnowledgeSysArticle(int cid) {
        this.cid = cid;
        RetrofitManager.getApiService()
                .getKnowledgeSystemArticles(mPage, cid)
                .compose(RxSchedulers.<Result<Article>>applySchedulers())
                .compose(mView.<Result<Article>>bindToLife())
                .subscribe(new Consumer<Result<Article>>() {
                    @Override
                    public void accept(Result<Article> articleResult) throws Exception {
                        Article article = articleResult.getData();
                        @LoadType.checker int loadType = isRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.setKnowledgeSysArticle(article, loadType);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        @LoadType.checker int loadType = isRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.setKnowledgeSysArticle(new Article(), loadType);
                    }
                }));
    }

    @Override
    public void refresh() {
        mPage = 0;
        isRefresh = true;
        getKnowledgeSysArticle(cid);
    }

    @Override
    public void loadMore() {
        mPage++;
        isRefresh = false;
        getKnowledgeSysArticle(cid);
    }
}
