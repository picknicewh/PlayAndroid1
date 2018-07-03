package com.mayi.yun.playandroid.ui.home;

import com.mayi.yun.playandroid.base.BaseContract;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.bean.Banner;
import com.mayi.yun.playandroid.constant.LoadType;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface HomeContract {
    interface View extends BaseContract.BaseView {
        void setHomeBanner(List<Banner> bannerList);
        void setHomeArticle(Article article, @LoadType.checker int loadType);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getHomeBanner();

        void getHomeArticle();
        void refresh();

        void loadMore();
    }
}
