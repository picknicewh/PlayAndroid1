package com.mayi.yun.playandroid.ui.knowledgesystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.bean.KnowledgeSystem;
import com.mayi.yun.playandroid.utils.OnItemClickListener;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class KnowledgeSystemAdapter extends RecyclerView.Adapter<KnowledgeSystemAdapter.ViewHolder> {
    private List<KnowledgeSystem> knowledgeSystemList;
    private OnItemClickListener onItemClickListener;

    public KnowledgeSystemAdapter(List<KnowledgeSystem> knowledgeSystemList) {
        this.knowledgeSystemList = knowledgeSystemList;
    }

    @Override
    public KnowledgeSystemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_knowledge_system, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KnowledgeSystemAdapter.ViewHolder holder, final int position) {
        KnowledgeSystem knowledgeSystem = knowledgeSystemList.get(position);
        holder.tvTitle.setText(knowledgeSystem.getName());
        holder.tvChildren.setText(getChildren(knowledgeSystem.getChildren()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, position);
                }
            }
        });
    }

    private String getChildren(List<KnowledgeSystem.Children> childrenList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < childrenList.size(); i++) {
            KnowledgeSystem.Children children = childrenList.get(i);
            stringBuffer.append(children.getName()).append("   ");
        }
        return stringBuffer.toString();
    }

    @Override
    public int getItemCount() {
        return knowledgeSystemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitle;
        TextView tvChildren;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_arrow_right);
            tvChildren = itemView.findViewById(R.id.tv_children);
            tvTitle = itemView.findViewById(R.id.tv_title);

            itemView.setTag(this);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
