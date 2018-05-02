package com.example.r.lifehelper.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.adapter.BookCategoryAdapter;
import com.example.r.lifehelper.adapter.BookListAdapter;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookCategory;
import com.example.r.lifehelper.bean.BookCategoryLab;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.data.BookListAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookListFragment extends Fragment {
    private RecyclerView rvBookList;
    private List<Book> mBookList = new ArrayList<>();
    private BookListAdapter mAdapter;
    private List<BookCategory> mBookCategories;
    private GridView gvBookCategory;
    private BookCategoryAdapter categoryAdapter;
    private BookCategory mBookCategory;
    private View mView;

    /*初始化UI元素*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_life_book_list, container, false);
        /*初始化数据*/
        mBookCategories = BookCategoryLab.getBookCategoryLab().getBookCategories();
        /*初始化类别列表*/
        initCategory(mView);
        /*设置类别列表的点击事件*/
        gvBookCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryAdapter.getSelectPositon(i);
                categoryAdapter.notifyDataSetInvalidated();
                final View animatorView = getActivity().findViewById(R.id.animator_life_loading);
                animatorView.setVisibility(View.VISIBLE);
                ObjectAnimator oa = ObjectAnimator.ofFloat(animatorView, "rotation",0f, 360f);
                oa.setDuration(3000);
                oa.start();
                mBookCategory = mBookCategories.get(i);
                String newUrl = mBookCategory.getUrl();
                try {
                    mBookList = new BookListAsyncTask().execute(newUrl).get();
                    oa.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            animatorView.setVisibility(View.GONE);
                            setupAdapter();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        /*初始化详情列表*/
        initList(mView);
        /*下拉加载更多*/
        rvBookList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) rvBookList.getLayoutManager();
                int totalItemCount = rvBookList.getAdapter().getItemCount();
                final int lastVisibleCount = lm.findLastVisibleItemPosition();
                int visibleItemCount = rvBookList.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleCount == totalItemCount - 1
                        && visibleItemCount > 0) {
                    String urlRecent = mBookCategory.getUrl();
                    int i = 2;
                    String urlPage = urlRecent + "index_" + (i++) + ".html";
                    try {
                        final View animatorView = mView.findViewById(R.id.animator_book_list);
                        animatorView.setVisibility(View.VISIBLE);
                        mBookList = new BookListAsyncTask().execute(urlPage).get();
                        ObjectAnimator oa = ObjectAnimator.ofFloat(animatorView, "rotation", 0f, 360f);
                        oa.setDuration(3000);
                        oa.start();
                        oa.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                animatorView.setVisibility(View.GONE);
                                mAdapter.insertItems(lastVisibleCount + 1,mBookList);
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
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            System.gc();
        }
    }

    private void initCategory(View view) {
        gvBookCategory = view.findViewById(R.id.gv_life_book);
        categoryAdapter = new BookCategoryAdapter(mBookCategories, getActivity());
        gvBookCategory.setAdapter(categoryAdapter);
        categoryAdapter.getSelectPositon(Adapter.NO_SELECTION);
    }

    private void initList(View view) {
        rvBookList = view.findViewById(R.id.rv_life_book);
        rvBookList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookList.setItemAnimator(new DefaultItemAnimator());
        setupAdapter();
    }

    /*更新详情列表数据*/
    private void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new BookListAdapter(mBookList, getActivity());
            rvBookList.setAdapter(mAdapter);
        } else {
            mAdapter.updateAdapter(mBookList);
        }
    }

}
