package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.activity.NewsActivity;
import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.News;
import com.example.r.lifehelper.utils.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    /*初始化三种类型*/
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_SPECIAL = 2;

    private Context mContext;
    private List<News> mNewsList;


    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    /*根据位置设置三种类型*/
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position % 4 == 0) {
            return TYPE_SPECIAL;
        } else {
            return TYPE_NORMAL;
        }
    }

    /*根据类型创建不同的ViewHolder */
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER:
                View view0 = LayoutInflater.from(mContext).inflate(R.layout.
                        item0_list_news, parent, false);
                holder = new NewsHolder(view0);
                break;
            case TYPE_NORMAL:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.
                        item1_list_news, parent, false);
                holder = new NewsHolder(view1);
                break;
            case TYPE_SPECIAL:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.
                        item2_list_news, parent, false);
                holder = new NewsHolder(view2);
                break;
        }
        return holder;
    }

    /*绑定数据*/
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        final News news = mNewsList.get(position);
        /*对banner进行初始化和赋值*/
        if (position == 0) {
            holder.bindBanner(mNewsList);
        } else {
            /*对具体的新闻项进行赋值*/
            holder.bindHolder(news);
            holder.bindImage(news);

            /*设置新闻项的点击事件*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = NewsActivity.newIntent(mContext, news.getContent());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /*刷新整个列表*/
    public void updateAdapter(List<News> newsList) {
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    /*列表数目*/
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        private Banner mNewsBanner;
        private TextView tvNewsTitle, tvNewsDate, tvNewsSrc;
        private ImageView ivNewsImg;
        private News mNews;
        private List<News> mNewsList;

        private NewsHolder(View itemView) {
            super(itemView);
            tvNewsTitle = itemView.findViewById(R.id.item_title);
            tvNewsSrc = itemView.findViewById(R.id.item_book_author);
            tvNewsDate = itemView.findViewById(R.id.item_book_summary);
            ivNewsImg = itemView.findViewById(R.id.item_img);
            mNewsBanner = itemView.findViewById(R.id.news_item_banner);
        }

        private void bindHolder(News news) {
            mNews = news;
            tvNewsTitle.setText(mNews.getTitle());
            tvNewsSrc.setText(mNews.getSrc());
            tvNewsDate.setText(mNews.getDate());
        }

        private void bindImage(News news) {
            mNews = news;
            ivNewsImg.setTag(mNews.getImageUrl());
            ImageLoader imageLoader = new ImageLoader(mContext);
            imageLoader.loadBitmapByThread(ivNewsImg, mNews.getImageUrl(), 1000, 1000);
        }

        private void bindBanner(final List<News> newsList) {
            mNewsList = newsList;
            mNewsBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            mNewsBanner.setImageLoader(new MyBannerImageLoader());
            List<String> images = initImages(mNewsList);
            mNewsBanner.setImages(images);
            final List<String> titles = initTitles(mNewsList);
            mNewsBanner.setBannerTitles(titles);
            mNewsBanner.setDelayTime(5000);
            mNewsBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    for (News news : mNewsList
                            ) {
                        if (news.getTitle().equals(titles.get(position))) {
                            Intent intent = NewsActivity.newIntent(mContext, news.getContent());
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            mNewsBanner.start();
        }

        @NonNull
        private List<String> initImages(List<News> newsList) {
            List<String> images = new ArrayList<>();
            images.add(newsList.get(15).getImageUrl());
            images.add(newsList.get(20).getImageUrl());
            images.add(newsList.get(25).getImageUrl());
            return images;
        }

        @NonNull
        private List<String> initTitles(List<News> newsList) {
            List<String> titles = new ArrayList<>();
            titles.add(newsList.get(15).getTitle());
            titles.add(newsList.get(20).getTitle());
            titles.add(newsList.get(25).getTitle());
            return titles;
        }


        private class MyBannerImageLoader extends com.youth.banner.loader.ImageLoader {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setTag(path);
                new ImageLoader(mContext).loadBitmapByThread(imageView, (String) path, 2000, 2000);
            }

        }
    }
}
