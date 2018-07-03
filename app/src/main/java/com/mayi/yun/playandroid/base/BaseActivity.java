package com.mayi.yun.playandroid.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.constant.Constant;
import com.mayi.yun.playandroid.constant.LoadType;
import com.mayi.yun.playandroid.di.component.ActivityComponent;
import com.mayi.yun.playandroid.di.component.DaggerActivityComponent;
import com.mayi.yun.playandroid.di.module.ActivityModule;
import com.mayi.yun.playandroid.utils.G;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： wh
 * 时间：  2018/2/26
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
     @Nullable
     @Inject
     protected   T mPresenter;
    private Unbinder unbinder;
    public Toolbar mToolbar;
    //非静态，除了针对整个App的Component可以静态，其他一般都不能是静态的。
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        unbinder = ButterKnife.bind(this);
        attachView();
        initToolBar();
        initView();

    }

    public abstract void initInjector();

    public abstract int getLayoutId();

    public abstract void initView();

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        /**toolbar除掉阴影*/
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
        G.showToast(this, message);
    }

    @Override
    public void showNoNetWork() {
        G.showToast(this, "无网络连接！");
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }
    public void setLoadDataResult(BaseQuickAdapter baseQuickAdapter, SwipeRefreshLayout swipeRefreshLayout, List list, @LoadType.checker int loadType) {
        switch (loadType) {
            case LoadType.TYPE_REFRESH_SUCCESS:
                baseQuickAdapter.setNewData(list);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case LoadType.TYPE_REFRESH_ERROR:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if (list != null) {
                    baseQuickAdapter.addData(list);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
    }

    public void initComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
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
}
