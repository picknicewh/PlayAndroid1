package com.mayi.yun.playandroid.ui.knowledgesystem;

import com.mayi.yun.playandroid.base.BaseContract;
import com.mayi.yun.playandroid.bean.KnowledgeSystem;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface KnowledgeSystemContract {
    interface View extends BaseContract.BaseView {
        void setKnowledgeSystemList(List<KnowledgeSystem> knowledgeSystemList);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getKnowledgeSystemList();
    }
}
