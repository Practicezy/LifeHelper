package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.r.lifehelper.adapter.BookCategoryAdapter;
import com.example.r.lifehelper.adapter.BookListAdapter;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookCategory;
import com.example.r.lifehelper.bean.BookCategoryLab;
import com.example.r.lifehelper.bean.BookLab;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {
    private RecyclerView rvBookList;
    private List<Book> mBookList;
    private BookListAdapter mAdapter;
    private List<BookCategory> mBookCategories;
    private GridView gvBookCategory;

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
        initCategory(view);
        initList(view);
        return view;
    }

    private void initCategory(View view) {
        gvBookCategory = view.findViewById(R.id.gv_life_book);
        BookCategoryAdapter adapter = new BookCategoryAdapter(mBookCategories, getActivity());
        gvBookCategory.setAdapter(adapter);
    }

    private void initList(View view) {
        rvBookList = view.findViewById(R.id.rv_life_book);
        rvBookList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
    }


    private void setupAdapter(){
        if (mAdapter == null){
            mAdapter = new BookListAdapter(mBookList, getActivity());
            rvBookList.setAdapter(mAdapter);
        }else{
            mAdapter.updateAdapter(mBookList);
        }
    }

}
