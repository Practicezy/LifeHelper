package com.example.r.lifehelper.fragment.BaseFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.adapter.NewsAdapter;
import com.example.r.lifehelper.adapter.NewsListAdapter;
import com.example.r.lifehelper.bean.News;
import com.example.r.lifehelper.utils.NewsLoader;
import com.example.r.lifehelper.utils.onDubleClickListener;

import java.util.List;


public class NewsFragment extends Fragment {
    private RecyclerView rvNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news, container, false);
        rvNews = view.findViewById(R.id.rv_fragment_news);
        rvNews.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        NewsListAdapter adapter = new NewsListAdapter(getActivity());
        rvNews.setAdapter(adapter);
        return view;
    }

}
