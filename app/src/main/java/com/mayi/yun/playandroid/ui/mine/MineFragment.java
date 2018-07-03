package com.mayi.yun.playandroid.ui.mine;

import android.view.View;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.base.BaseFragment;

/**
 * 作者： wh
 * 时间：  2018/2/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    private static MineFragment mineFragment;
    public static MineFragment newInstance(){
        if (mineFragment==null){
            mineFragment  = new MineFragment();
        }
        return mineFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {
      mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {

    }
}
