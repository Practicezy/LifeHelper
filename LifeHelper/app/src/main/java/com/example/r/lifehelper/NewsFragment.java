package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class NewsFragment extends Fragment {
    private static NewsFragment sNewsFragment;
    private RecyclerView rvNews;
    private List<News> mNewsList;
    private NewsAdapter mAdapter;
    private TabLayout mTabLayout;

    public static NewsFragment newInstance(){
        if (sNewsFragment == null){
            sNewsFragment = new NewsFragment();
        }
        Bundle args = new Bundle();
        sNewsFragment.setArguments(args);
        return sNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news,container,false);

        mTabLayout = view.findViewById(R.id.tab_layout);
        String[] tabs = getResources().getStringArray(R.array.tabs);
        int i = 0;
        for (String s:tabs
                 ) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s),i++);
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rvNews = view.findViewById(R.id.rv_fragment_news);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter(getResources().getString(R.string.news_top));
        return view;
    }

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
