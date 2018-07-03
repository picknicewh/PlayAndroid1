package com.mayi.yun.playandroid.ui.home;

import com.mayi.yun.playandroid.base.BasePresenter;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.bean.Banner;
import com.mayi.yun.playandroid.bean.Result;
import com.mayi.yun.playandroid.constant.LoadType;
import com.mayi.yun.playandroid.network.RetrofitManager;
import com.mayi.yun.playandroid.network.RxSchedulers;
import com.mayi.yun.playandroid.network.ThrowCustomer;
import com.mayi.yun.playandroid.utils.G;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private int mPage = 0;
    private boolean mIsRefresh;

    @Inject
    public HomePresenter() {
        mIsRefresh = true;
    }

    @Override
    public void getHomeBanner() {
        RetrofitManager.getApiService().
                getHomeBanners()
                .compose(RxSchedulers.<Result<List<Banner>>>applySchedulers())
                .compose(mView.<Result<List<Banner>>>bindToLife())
                .subscribe(new Consumer<Result<List<Banner>>>() {
                    @Override
                    public void accept(Result<List<Banner>> listResult) throws Exception {
                        List<Banner> bannerList = listResult.getData();
                        mView.setHomeBanner(bannerList);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        G.log("xxx" + message);
                        mView.showMessage(message);
                    }
                }));
    }

    @Override
    public void getHomeArticle() {
        RetrofitManager.getApiService().getHomeArticles(mPage)
                .compose(RxSchedulers.<Result<Article>>applySchedulers())
                .compose(mView.<Result<Article>>bindToLife())
                .subscribe(new Consumer<Result<Article>>() {
                    @Override
                    public void accept(Result<Article> articleResult) throws Exception {
                        Article article = articleResult.getData();
                        @LoadType.checker int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.setHomeArticle(article, loadType);
                    }
                }, new ThrowCustomer(new ThrowCustomer.CallBack() {
                    @Override
                    public void getErrorMessage(String message) {
                        @LoadType.checker int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.setHomeArticle(new Article(), loadType);
                    }
                }));
    }

    @Override
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        getHomeBanner();
        getHomeArticle();
    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        getHomeArticle();
    }
}
