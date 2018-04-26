package com.example.r.lifehelper.fragment;

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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.adapter.BookCategoryAdapter;
import com.example.r.lifehelper.adapter.BookListAdapter;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookCategory;
import com.example.r.lifehelper.bean.BookCategoryLab;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.data.BookListAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookListFragment extends Fragment {
    private RecyclerView rvBookList;
    private List<Book> mBookList;
    private BookListAdapter mAdapter;
    private List<BookCategory> mBookCategories;
    private GridView gvBookCategory;
    private BookCategory mBookCategory;

    /*初始化数据*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookList = BookLab.getBookLab(getActivity()).getBooks();
        mBookCategories = BookCategoryLab.getBookCategoryLab().getBookCategories();
    }

    /*初始化UI元素*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life_book_list,container,false);
        /*初始化类别列表*/
        initCategory(view);
        /*设置类别列表的点击事件*/
        gvBookCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBookCategory = mBookCategories.get(i);
                String newUrl = mBookCategory.getUrl();
                try {
                    mBookList = new BookListAsyncTask().execute(newUrl).get();
                    setupAdapter();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        /*初始化详情列表*/
        initList(view);
        /*下拉加载更多*/
        rvBookList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) rvBookList.getLayoutManager();
                int totalItemCount = rvBookList.getAdapter().getItemCount();
                int lastVisibleCount = lm.findLastVisibleItemPosition();
                int visibleItemCount = rvBookList.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleCount == totalItemCount - 1
                        && visibleItemCount > 0){
                    String urlRecent = mBookCategory.getUrl();
                    int i = 2;
                    String urlPage = urlRecent + "index_"+ (i++) +".html";
                    try {
                        mBookList = new BookListAsyncTask().execute(urlPage).get();
                        mAdapter.insertItems(lastVisibleCount+1,mBookList);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    private void initCategory(View view) {
        gvBookCategory = view.findViewById(R.id.gv_life_book);
        BookCategoryAdapter adapter = new BookCategoryAdapter(mBookCategories, getActivity());
        gvBookCategory.setAdapter(adapter);
        mBookCategory = mBookCategories.get(9);
    }

    private void initList(View view) {
        rvBookList = view.findViewById(R.id.rv_life_book);
        rvBookList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookList.setItemAnimator(new DefaultItemAnimator());
        setupAdapter();
    }

    /*更新详情列表数据*/
    private void setupAdapter(){
        if (mAdapter == null){
            mAdapter = new BookListAdapter(mBookList, getActivity());
            rvBookList.setAdapter(mAdapter);
        }else{
            mAdapter.updateAdapter(mBookList);
        }
    }

}
