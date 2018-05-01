package com.example.r.lifehelper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.r.lifehelper.adapter.NewsAdapter;
import com.example.r.lifehelper.bean.News;
import com.example.r.lifehelper.utils.NewsLoader;
import com.example.r.lifehelper.utils.onDubleClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {
    private RecyclerView rvNews;
    private List<News> mNewsList;
    private NewsAdapter mAdapter;
    private TabLayout mTabLayout;
    public static final String EXTRA_NEWS = "com.example.r.lifehelper.news_extra";

    private List<Integer> tabs = new ArrayList<Integer>(Arrays.asList(R.string.news_top,
            R.string.news_shehui,R.string.news_guonei,R.string.news_guoji,R.string.news_yule,
            R.string.news_tiyu,R.string.news_junshi,R.string.news_keji,
            R.string.news_caijing,R.string.news_shishang));

    public static Intent newIntent(Context context,int position){
        Intent intent = new Intent(context, NewsListActivity.class);
        intent.putExtra(EXTRA_NEWS, position);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        /*双击标题返回顶部*/
        initToolbar();
        /*新闻标签初始化*/
        setupTabLayout();
        /*列表初始化*/
        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initList() {
        rvNews = findViewById(R.id.rv_list_news);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        int position = getIntent().getIntExtra(EXTRA_NEWS, 0);
        setupAdapter(getResources().getString(tabs.get(position)));
        mTabLayout.getTabAt(position).select();
    }

        /*根据给定的字符串来设置新闻页标签*/
        private void setupTabLayout() {
            mTabLayout = findViewById(R.id.tab_layout);
            String[] tabs = getResources().getStringArray(R.array.tabs);
            int i = 0;
            for (String s : tabs
                    ) {
                mTabLayout.addTab(mTabLayout.newTab().setText(s), i++);
            }
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switchTab(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    /*根据标签名来刷新数据*/
    private void switchTab(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                setupAdapter(getResources().getString(R.string.news_top));
                break;
            case 1:
                setupAdapter(getResources().getString(R.string.news_shehui));
                break;
            case 2:
                setupAdapter(getResources().getString(R.string.news_guonei));
                break;
            case 3:
                setupAdapter(getResources().getString(R.string.news_guoji));
                break;
            case 4:
                setupAdapter(getResources().getString(R.string.news_yule));
                break;
            case 5:
                setupAdapter(getResources().getString(R.string.news_tiyu));
                break;
            case 6:
                setupAdapter(getResources().getString(R.string.news_junshi));
                break;
            case 7:
                setupAdapter(getResources().getString(R.string.news_keji));
                break;
            case 8:
                setupAdapter(getResources().getString(R.string.news_caijing));
                break;
            case 9:
                setupAdapter(getResources().getString(R.string.news_shishang));
                break;
        }
    }

    /*根据给定的字符串来刷新列表数据*/
    private void setupAdapter(String urlSpec) {
        mNewsList = new NewsLoader().loadNewsByAsyncTask(urlSpec);
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(this, mNewsList);
            rvNews.setAdapter(mAdapter);
        } else {
            mAdapter.updateAdapter(mNewsList);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnTouchListener(new onDubleClickListener(new onDubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                rvNews.scrollToPosition(0);
            }
        }));
    }
}
