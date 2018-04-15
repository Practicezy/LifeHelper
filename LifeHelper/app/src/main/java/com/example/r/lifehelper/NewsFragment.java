package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.lifehelper.bean.News;
import com.example.r.lifehelper.unitils.NewsLoader;

import java.util.List;


public class NewsFragment extends Fragment {
    private View mView;
    private RecyclerView rvNews;
    private List<News> mNewsList;
    private NewsAdapter mAdapter;
    private TabLayout mTabLayout;
    private static final String TAG = "NewsFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*双击标题返回顶部*/
        DoubleClickToolbar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list_news,container,false);
        /*新闻标签初始化*/
        setupTabLayout();

        /*列表初始化*/
        rvNews = mView.findViewById(R.id.rv_fragment_news);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter(getResources().getString(R.string.news_top));
        return mView;
    }

    /*根据给定的字符串来设置新闻页标签*/
    private void setupTabLayout() {
        mTabLayout = mView.findViewById(R.id.tab_layout);
        String[] tabs = getResources().getStringArray(R.array.tabs);
        int i = 0;
        for (String s:tabs
                 ) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s),i++);
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

    /*重新加载界面后刷新UI*/
    @Override
    public void onResume() {
        mView.invalidate();
        super.onResume();
    }

    /*设置双击标题事件*/
    private void DoubleClickToolbar() {
        View view = getActivity().findViewById(R.id.my_toolbar);
        view.setOnTouchListener(new onDubleClickListener(new onDubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                rvNews.scrollToPosition(0);
            }
        }));
    }

    /*根据标签名来刷新数据*/
    private void switchTab(TabLayout.Tab tab) {
        switch (tab.getPosition()){
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
        }
    }

    /*根据给定的字符串来刷新列表数据*/
    private void setupAdapter(String urlSpec) {
        mNewsList = new NewsLoader().loadNewsByAsyncTask(urlSpec);
        if(mAdapter == null){
            mAdapter = new NewsAdapter(getActivity(), mNewsList);
            rvNews.setAdapter(mAdapter);
        }else {
            mAdapter.updateAdapter(mNewsList);
        }
    }
}
