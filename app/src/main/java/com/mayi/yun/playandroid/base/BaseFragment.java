package com.mayi.yun.playandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayi.yun.playandroid.constant.Constant;
import com.mayi.yun.playandroid.constant.LoadType;
import com.mayi.yun.playandroid.di.component.DaggerFragmentComponent;
import com.mayi.yun.playandroid.di.component.FragmentComponent;
import com.mayi.yun.playandroid.di.module.FragmentModule;
import com.mayi.yun.playandroid.utils.G;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment implements BaseContract.BaseView {
    @Nullable
    @Inject
    protected T mPresenter;
    private View mRootView;
    private Unbinder unbinder;
    public FragmentComponent mFragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        initInjector();
        attachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView(View view);

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
        G.showToast(getActivity(), message);
    }

    @Override
    public void showNoNetWork() {
        G.showToast(getActivity(), "无网络连接！");
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }


    /**
     * 设置加载数据结果
     *
     * @param baseQuickAdapter
     * @param refreshLayout
     * @param list
     * @param loadType
     */
    protected void setLoadDataResult(BaseQuickAdapter baseQuickAdapter, SwipeRefreshLayout refreshLayout, List list, @LoadType.checker int loadType) {
        switch (loadType) {
            case LoadType.TYPE_REFRESH_SUCCESS:
                G.log("ssssss=="+list.size());
                G.log("ssssss=="+baseQuickAdapter);
                baseQuickAdapter.setNewData(list);
                refreshLayout.setRefreshing(false);
                break;
            case LoadType.TYPE_REFRESH_ERROR:
                refreshLayout.setRefreshing(false);
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if (list != null) baseQuickAdapter.addData(list);
                break;
            case LoadType.TYPE_LOAD_MORE_ERROR:
                baseQuickAdapter.loadMoreFail();
                break;
        }
        if (list == null || list.isEmpty() || list.size() < Constant.PAGE_SIZE) {
            baseQuickAdapter.loadMoreEnd(false);
        } else {
            baseQuickAdapter.loadMoreComplete();
        }
    }

    private void initComponent(){
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App)getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }
    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
    }

}
