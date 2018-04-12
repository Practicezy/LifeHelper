package com.example.r.lifehelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.bean.News;
import com.example.r.lifehelper.unitils.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
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
        if (position == 0){
            return TYPE_HEADER;
        }
        else if (position % 4 == 0){
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
            case TYPE_HEADER:
                View view0 = LayoutInflater.from(mContext).inflate(R.layout.
                        item0_fragment_list_news,parent,false);
                holder = new NewsHolder(view0);
                break;
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
        final News news = mNewsList.get(position);
        if (position == 0){
            holder.bindBanner(mNewsList);
        }else {
            holder.bindHolder(news);
            holder.bindImage(news);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = NewsActivity.newIntent(mContext, news.getContent());
                    mContext.startActivity(intent);
                }
            });
        }
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
        private Banner mBanner;
        private TextView tvTitle,tvDate,tvSrc;
        private ImageView ivImg;
        private News mNews;
        private List<News> mNewsList;

        public NewsHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvSrc = itemView.findViewById(R.id.item_src);
            tvDate = itemView.findViewById(R.id.item_date);
            ivImg = itemView.findViewById(R.id.item_img);
            mBanner = itemView.findViewById(R.id.item_banner);
        }

        private void bindHolder(News news){
            mNews = news;
            tvTitle.setText(mNews.getTitle());
            tvSrc.setText(mNews.getSrc());
            tvDate.setText(mNews.getDate());
        }

        private void bindImage(News news){
            mNews = news;
            ivImg.setTag(mNews.getImageUrl());
            ImageLoader imageLoader = new ImageLoader(mContext);
            imageLoader.loadBitmapByThread(ivImg,mNews.getImageUrl(),1000,1000);
        }

        private void bindBanner(List<News> newsList){
            mNewsList = newsList;
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            mBanner.setImageLoader(new MyBannerImageLoader());
            List<String> images = initImages(mNewsList);
            mBanner.setImages(images);
            final List<String> titles = initTitles(mNewsList);
            mBanner.setBannerTitles(titles);
            mBanner.setDelayTime(5000);
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    for (News news:mNewsList
                         ) {
                        if (news.getTitle().equals(titles.get(position))){
                            Intent intent = NewsActivity.newIntent(mContext, news.getContent());
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            mBanner.start();
        }

        @NonNull
        private List<String> initImages(List<News> newsList) {
            List<String> images = new ArrayList<>();
            images.add(newsList.get(10).getImageUrl());
            images.add(newsList.get(11).getImageUrl());
            images.add(newsList.get(12).getImageUrl());
            return images;
        }

        @NonNull
        private List<String> initTitles(List<News> newsList) {
            List<String> titles = new ArrayList<>();
            titles.add(newsList.get(10).getTitle());
            titles.add(newsList.get(11).getTitle());
            titles.add(newsList.get(12).getTitle());
            return titles;
        }



        private class MyBannerImageLoader extends com.youth.banner.loader.ImageLoader{

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setTag(path);
                new ImageLoader(mContext).loadBitmapByThread(imageView,(String)path,2000,2000);
            }

        }
    }
}
