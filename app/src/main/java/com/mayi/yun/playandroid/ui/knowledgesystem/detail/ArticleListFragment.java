package com.mayi.yun.playandroid.ui.knowledgesystem.detail;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseFragment;
import com.mayi.yun.playandroid.bean.Article;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ArticleListFragment extends BaseFragment<ArticleListPresenter> implements ArticleListContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    /**
     * 对应当前子下信息的id
     */
    public int cid;
    /**
     * 文章列表
     */
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    /**
     * 适配器
     */
    @Inject
    ArticleAdapter articleAdapter;
    /**
     * 刷新
     */
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static ArticleListFragment newInstance() {
        return new ArticleListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        cid = getArguments().getInt("cid");
        rvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArticle.setAdapter(articleAdapter);

        articleAdapter.setOnLoadMoreListener(this, rvArticle);
        articleAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.getKnowledgeSysArticle(cid);
    }


    @Override
    public void setKnowledgeSysArticle(Article article, int loadType) {
        setLoadDataResult(articleAdapter, swipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void showProgress() {
        super.showProgress();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
        intent.putExtra("link", articleAdapter.getItem(position).getLink());
        intent.putExtra("title", articleAdapter.getItem(position).getTitle());
        startActivity(intent);
    }
}
