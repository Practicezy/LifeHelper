package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.NewsListActivity;
import com.example.r.lifehelper.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListHolder> {
    private List<Integer> bgColors = new ArrayList<Integer>(Arrays.asList(R.color.colorLightAccent,
            R.color.colorLightGreen,R.color.colorLightBlue,R.color.colorLightYellow,R.color.colorLightBlack,
    R.color.colorLightDarkBlue,R.color.colorLightRed,R.color.colorLightChartreuse,
            R.color.colorStarBlue,R.color.colorLightOrange));

    private List<Integer> titles = new ArrayList<Integer>(Arrays.asList(R.string.news_top,
            R.string.news_shehui,R.string.news_guonei,R.string.news_guoji,R.string.news_yule,
            R.string.news_tiyu,R.string.news_junshi,R.string.news_keji,
            R.string.news_caijing,R.string.news_shishang));

    private List<String> titles_zh = new ArrayList<>(Arrays.asList("\n" + "头条",
            "\n" + "社会","\n" + "国内","\n" + "国际","\n" + "娱乐","\n" + "体育","\n" + "军事",
            "\n" + "科技","\n" + "财经","\n" + "时尚"));

    private List<Integer> mHeights;

    private Context mContext;

    public NewsListAdapter(Context context) {
        mContext = context;
        getRandomHeights();
    }

    private void getRandomHeights(){
        mHeights = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            mHeights.add((int) (500 + Math.random()*100));
        }
    }

    @NonNull
    @Override
    public NewsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_news,parent,false);
        NewsListHolder holder = new NewsListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.ivNews.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.ivNews.setLayoutParams(layoutParams);

        int bgColor = bgColors.get(position);
        int title = titles.get(position);
        String title_zh = titles_zh.get(position);
        holder.ivNews.setBackgroundResource(bgColor);
        holder.tvNews.setText(title);
        holder.tvNews.setTextColor((position % 2==0)? mContext.getResources().getColor(R.color.text):mContext.getResources().getColor(R.color.white));
        holder.tvNews.append(title_zh);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = NewsListActivity.newIntent(mContext, position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class NewsListHolder extends RecyclerView.ViewHolder{
        ImageView ivNews;
        TextView tvNews;
        public NewsListHolder(View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.iv_news_item_img);
            tvNews = itemView.findViewById(R.id.tv_news_item_title);
        }
    }
}
