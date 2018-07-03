package com.mayi.yun.playandroid.ui.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseFragment;
import com.mayi.yun.playandroid.bean.Article;
import com.mayi.yun.playandroid.bean.Banner;
import com.mayi.yun.playandroid.network.GlideImageLoader;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleAdapter;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleContentActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, OnBannerListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private static HomeFragment homeFragment;
    /**
     * banner插件
     */
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * 首页数量列表
     */
    @BindView(R.id.rv_home)
    RecyclerView recyclerView;

    private com.youth.banner.Banner banner;
    /**
     * 头部标题view
     */
    private View mHeadView;
    /**
     * 适配器
     */
    @Inject
    ArticleAdapter articleAdapter;
    /**
     * 图片列表
     */
    private List<String> linkList;
    /**
     * 标题列表
     */
    private List<String> titleList;

    public static HomeFragment newInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        linkList = new ArrayList<>();
        titleList = new ArrayList<>();
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_head, null);
        banner = mHeadView.findViewById(R.id.banner);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.addHeaderView(mHeadView);
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnLoadMoreListener(this, recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.getHomeBanner();
        mPresenter.getHomeArticle();
        banner.setOnBannerListener(this);
    }

    @Override
    public void setHomeBanner(List<Banner> bannerList) {
        List<String> imageUrlList = new ArrayList<>();
         titleList.clear();
        for (int i = 0; i < bannerList.size(); i++) {
            Banner banner = bannerList.get(i);
            imageUrlList.add(banner.getImagePath());
            titleList.add(banner.getTitle());
            linkList.add(banner.getUrl());
        }
        banner.setImages(imageUrlList)
                .setBannerTitles(titleList)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();

    }

    @Override
    public void setHomeArticle(Article article, int loadType) {
        setLoadDataResult(articleAdapter, mSwipeRefreshLayout, article.getDatas(), loadType);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
        intent.putExtra("link", linkList.get(position));
        intent.putExtra("title",titleList.get(position) );
        startActivity(intent);
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
