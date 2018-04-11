package com.example.r.lifehelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_SPECIAL = 2;
    private Context mContext;
    private List<News> mNewsList;
    private static final String TAG = "NewsAdapter";


    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0){
//            return TYPE_HEADER;
//        }
        if (position % 4 == 0 && position > 0){
            return TYPE_SPECIAL;
        }
        else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsHolder holder = null;
        switch (viewType){
//            case TYPE_HEADER:
//                View view0 = LayoutInflater.from(mContext).inflate();
//                holder = new NewsHolder(view0);
//                break;
            case TYPE_NORMAL:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.
                        item1_fragment_list_news,parent,false);
                holder = new NewsHolder(view1);
                break;
            case TYPE_SPECIAL:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.
                        item2_fragment_list_news,parent,false);
                holder = new NewsHolder(view2);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvSrc.setText(news.getSrc());
        holder.tvDate.setText(news.getDate());
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadBitmapByThread(holder.ivImg,news.getImageUrl(),1000,1000);
        holder.ivImg.setTag(news.getImageUrl());
    }

    public void updateAdapter(List<News> newsList){
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvDate,tvSrc;
        private ImageView ivImg;
        public NewsHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvSrc = itemView.findViewById(R.id.item_src);
            tvDate = itemView.findViewById(R.id.item_date);
            ivImg = itemView.findViewById(R.id.item_img);
        }
    }
}
