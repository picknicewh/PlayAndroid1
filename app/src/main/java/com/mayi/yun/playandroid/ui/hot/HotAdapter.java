package com.mayi.yun.playandroid.ui.hot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;
import com.mayi.yun.playandroid.constant.Constant;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 热搜列表
     */
    private List<HotKey> hotKeyList;
    /**
     * 常用网站列表
     */
    private List<Friend> friendList;
    /**
     * 回调监听
     */
    private OnTagClickListener onTagClickListener;

    private enum ViewType {
        PARENT,
        CHILD,
    }

    public HotAdapter(List<HotKey> hotKeyList, List<Friend> friendList) {
        this.hotKeyList = hotKeyList;
        this.friendList = friendList;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = ViewType.PARENT.ordinal();
        if (position == 0 || position == 2) {
            viewType = ViewType.PARENT.ordinal();
        } else if (position == 1 || position == 3) {
            viewType = ViewType.CHILD.ordinal();
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ViewType.PARENT.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_parent, parent, false);
            viewHolder = new GroupViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_child, parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }
    private   int tag =   Constant.TAG_HOT;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0 || position == 2) {
            GroupViewHolder viewHolder = (GroupViewHolder) holder;
            if (position == 0) viewHolder.tvTitle.setText("大家都再搜");
            else viewHolder.tvTitle.setText("常用网站");
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (position == 1) {
                tag = Constant.TAG_HOT;
                HotChildAdapter<HotKey> hotChildAdapter = new HotChildAdapter<>(hotKeyList);
                viewHolder.flowLayout.setAdapter(hotChildAdapter);
            } else if (position == 3) {
                tag = Constant.TAG_FRIEDND;
                HotChildAdapter<Friend> hotChildAdapter = new HotChildAdapter<>(friendList);
                viewHolder.flowLayout.setAdapter(hotChildAdapter);
            }
            viewHolder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(view, position, tag);
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public GroupViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            itemView.setTag(this);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TagFlowLayout flowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            flowLayout = itemView.findViewById(R.id.tfl_hot);
            itemView.setTag(this);
        }
    }


    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        void onTagClick(View view, int position, int from);
    }
}
