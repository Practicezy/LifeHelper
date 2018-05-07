package com.example.r.lifehelper.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
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
import com.example.r.lifehelper.utils.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieListFragment extends Fragment {
    private EmptyRecyclerView rvMovieList;
    private List<Movie> mMovieList = new ArrayList<>();
    private MovieListAdapter mAdapter;
    private boolean isInit = false;
    private boolean isHidden = true;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_movie_list,container,false);
        rvMovieList = mView.findViewById(R.id.rv_life_movie);
        isInit = true;
        isCanLoadData();
        return mView;
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
            View emptyView = mView.findViewById(R.id.empty_view);
            rvMovieList.setEmptyView(emptyView);
            setupAdapter();
            rvMovieList.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int totalItemCount = recyclerView.getAdapter().getItemCount();
                    final int lastVisibleCount = lm.findLastVisibleItemPosition();
                    int visibleItemCount = recyclerView.getChildCount();

                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleCount == totalItemCount -1
                            && visibleItemCount > 0){
                        String urlNext = mMovieList.get(mMovieList.size()-1).getNextPageUrl();
                        try {
                            final View animatorView = mView.findViewById(R.id.animator_movie_list);
                            animatorView.setVisibility(View.VISIBLE);
                            mMovieList = new MovieAsyncTask().execute(urlNext).get();
                            ObjectAnimator oa = ObjectAnimator.ofFloat(animatorView, "rotation", 0f, 360f);
                            oa.setDuration(3000);
                            oa.start();
                            oa.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    animatorView.setVisibility(View.GONE);
                                    mAdapter.insertItems(lastVisibleCount + 1,mMovieList);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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
