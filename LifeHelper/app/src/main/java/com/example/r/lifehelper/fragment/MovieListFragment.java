package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.adapter.BookListAdapter;
import com.example.r.lifehelper.adapter.MovieListAdapter;
import com.example.r.lifehelper.bean.Movie;
import com.example.r.lifehelper.bean.MovieLab;
import com.example.r.lifehelper.data.MovieAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {
    private RecyclerView rvMovieList;
    private List<Movie> mMovieList = new ArrayList<>();
    private MovieListAdapter mAdapter;
    private boolean isInit = false;
    private boolean isHidden = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list,container,false);
        rvMovieList = view.findViewById(R.id.rv_life_movie);
        isInit = true;
        isCanLoadData();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            isHidden = false;
            isCanLoadData();
        }else {
            System.gc();
        }
    }

    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (!isHidden){
            mMovieList = MovieLab.getMovieLab().getMovieList();
            rvMovieList.setLayoutManager(new LinearLayoutManager(getActivity()));
            setupAdapter();
        }
    }

    /*更新详情列表数据*/
    private void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new MovieListAdapter(mMovieList, getActivity());
            rvMovieList.setAdapter(mAdapter);
        } else {
            mAdapter.updateAdapter(mMovieList);
        }
    }

}
