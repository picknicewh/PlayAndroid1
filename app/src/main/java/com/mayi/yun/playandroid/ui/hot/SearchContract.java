package com.mayi.yun.playandroid.ui.hot;

import com.mayi.yun.playandroid.base.BaseContract;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.constant.LoadType;
import com.mayi.yun.playandroid.db.History;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface SearchContract {
    interface View extends BaseContract.BaseView {
        void setHistory(List<History> historyList);
       void setArticle(Article article, @LoadType.checker int loadType);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getHistory();

        void searchArticles(String keyWord);
         void addHistory(String name);
        void loadMore();

        void refresh();
    }
}
