package com.mayi.yun.playandroid.ui.hot;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseFragment;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;
import com.mayi.yun.playandroid.constant.Constant;
import com.mayi.yun.playandroid.ui.knowledgesystem.detail.ArticleContentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/3/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HotFragment extends BaseFragment<HotPresenter> implements HotContract.View, SwipeRefreshLayout.OnRefreshListener, HotAdapter.OnTagClickListener {
    /**
     * 热搜列表
     */
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    /**
     * 下拉刷新
     */
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<HotKey> hotKeyList;

    private List<Friend> friendList;
    private HotAdapter hotAdapter;

    public static HotFragment newInstance() {
        return new HotFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        hotKeyList = new ArrayList<>();
        friendList = new ArrayList<>();
        hotAdapter = new HotAdapter(hotKeyList, friendList);
        rvHot.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHot.setAdapter(hotAdapter);
        hotAdapter.setOnTagClickListener(this);
        mPresenter.loadHotData();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }


    @Override
    public void setHotData(List<HotKey> hotKeyList, List<Friend> friendList) {
        mSwipeRefreshLayout.setRefreshing(false);
        this.hotKeyList.clear();
        this.friendList.clear();
        this.hotKeyList.addAll(hotKeyList);
        this.friendList.addAll(friendList);
        if (hotAdapter != null) {
            hotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTagClick(View view, int position, int from) {
        Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
        switch (from) {
            case Constant.TAG_HOT:
                intent.putExtra("link", hotKeyList.get(position).getLink());
                intent.putExtra("name",hotKeyList.get(position).getName());
                break;
            case Constant.TAG_FRIEDND:
                intent.putExtra("link", friendList.get(position).getLink());
                intent.putExtra("name",friendList.get(position).getName());
                break;
        }
        getActivity().startActivity(intent);
    }
}
