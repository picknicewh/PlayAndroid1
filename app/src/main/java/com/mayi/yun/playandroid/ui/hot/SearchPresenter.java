package com.mayi.yun.playandroid.ui.hot;

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
 * 时间：  2018/3/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
    private int mPage;
    private boolean isRefresh;
    private String keyWord;

    @Inject
    public SearchPresenter() {
        isRefresh = true;
    }

    @Override
    public void getHistory() {

    }

    @Override
    public void searchArticles(String keyWord) {
        this.keyWord = keyWord;
        RetrofitManager.getApiService()
                .getSearchArticles(mPage, keyWord)
                .compose(RxSchedulers.<Result<Article>>applySchedulers())
                .compose(mView.<Result<Article>>bindToLife())
                .subscribe(new Consumer<Result<Article>>() {
                    @Override
                    public void accept(Result<Article> articleResult) throws Exception {
                        @LoadType.checker int loadType = isRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        Article article = articleResult.getData();
                        mView.setArticle(article, loadType);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        @LoadType.checker int loadType = isRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.setArticle(new Article(), loadType);
                        mView.showMessage(message);
                    }
                }));
    }

    @Override
    public void addHistory(String name) {

    }

    @Override
    public void loadMore() {
        mPage++;
        isRefresh = false;
        searchArticles(keyWord);
    }

    @Override
    public void refresh() {
        mPage = 0;
        isRefresh = true;
        searchArticles(keyWord);
    }
}
