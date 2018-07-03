package com.mayi.yun.playandroid.ui.hot;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseActivity;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.db.History;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleAdapter;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleContentActivity;
import com.mayi.yun.playandroid.utils.G;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SwipeRefreshLayout.OnRefreshListener, SearchContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    /**
     * 关键字搜索列表
     */
    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    /**
     * 刷新
     */
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * 头部搜索历史
     */
    private View mHeadView;
    /**
     * 历史记流式布局
     */
    private TagFlowLayout mTagFlowLayout;
    /**
     * 历史记录列表
     */
    private List<History> historyList;
    /**
     * 历史记录适配器
     */
    private HotChildAdapter<History> adapter;
    /**
     * 适配器
     */
    @Inject
    ArticleAdapter articleAdapter;
    /**
     * 搜索编辑框
     */
    private SearchView mSearchView;

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_search;
    }

    @Override
    public void initView() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.layout_search_head, null);
        mTagFlowLayout = mHeadView.findViewById(R.id.tfl_search);
        historyList = new ArrayList<>();
        adapter = new HotChildAdapter<>(historyList);
        mTagFlowLayout.setAdapter(adapter);

        rvHotSearch.setLayoutManager(new LinearLayoutManager(this));
        rvHotSearch.setAdapter(articleAdapter);
        articleAdapter.setOnLoadMoreListener(this, rvHotSearch);
        articleAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getHistory();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setMaxWidth(1920);
        mSearchView.setIconified(false);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchActivity.this.finish();
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.addHistory(query);
                mPresenter.searchArticles(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setHistory(List<History> historyList) {
        this.historyList.clear();
        this.historyList.addAll(historyList);
        if (adapter != null) {
            adapter.notifyDataChanged();
        }
    }

    @Override
    public void setArticle(Article article, int loadType) {
        G.log("load:" + article.getDatas().size());
        setLoadDataResult(articleAdapter, mSwipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, ArticleContentActivity.class);
        intent.putExtra("link", articleAdapter.getItem(position).getLink());
        intent.putExtra("title", articleAdapter.getItem(position).getTitle());
        startActivity(intent);
    }
}
