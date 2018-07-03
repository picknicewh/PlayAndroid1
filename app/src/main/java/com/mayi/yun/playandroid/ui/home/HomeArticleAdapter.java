package com.mayi.yun.playandroid.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayi.yun.playandroid.R;
import com.mayi.yun.playandroid.bean.Article;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleAdapter.ViewHolder> {
    private List<Article.DatasBean> articleList;

    public HomeArticleAdapter(List<Article.DatasBean> articleList) {
        this.articleList = articleList;
    }

    @Override
    public HomeArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeArticleAdapter.ViewHolder holder, int position) {
        Article.DatasBean article = articleList.get(position);
        holder.tvTitle.setText(article.getAuthor());
        holder.tvContent.setText(article.getTitle());
        holder.tvTime.setText(article.getNiceDate());
        holder.tvFrom.setText(article.getChapterName());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvTitle;
        TextView tvContent;
        TextView tvFrom;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvFrom = itemView.findViewById(R.id.tv_from);
            tvContent = itemView.findViewById(R.id.tv_content);
            itemView.setTag(this);
        }
    }
}
