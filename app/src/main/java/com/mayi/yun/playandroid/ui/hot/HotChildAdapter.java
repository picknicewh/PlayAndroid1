package com.mayi.yun.playandroid.ui.hot;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.bean.Friend;
import com.mayi.yun.playandroid.bean.HotKey;
import com.mayi.yun.playandroid.db.History;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HotChildAdapter<T> extends TagAdapter<T> {
    private List<T> list;
    public HotChildAdapter(List<T> list) {
        super(list);
        this.list = list;
    }
    @Override
    public View getView(FlowLayout parent, int position, T t) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_child_child, null);
        TextView tvTag = view.findViewById(R.id.tv_tag);
        if (t instanceof HotKey) {
            HotKey hotKey = (HotKey) list.get(position);
            tvTag.setText(hotKey.getName());
        } else if (list.get(position) instanceof Friend) {
            Friend friend = (Friend) list.get(position);
            tvTag.setText(friend.getName());
        }else if (list.get(position) instanceof History){
            History  history = (History) list.get(position);
            tvTag.setText(history.getName());
        }
        int parseColor = 0;
        try {
            String str = Integer.toHexString((int) (Math.random() * 16777215));
            parseColor = Color.parseColor("#".concat(str));
        }catch (Exception e){
            parseColor = Color.RED;
        }
        tvTag.setTextColor(parseColor);
        return view;
    }
}
