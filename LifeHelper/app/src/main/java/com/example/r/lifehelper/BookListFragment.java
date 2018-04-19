package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.lifehelper.adapter.BookListAdapter;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.utils.BookAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookListFragment extends Fragment {
    private RecyclerView rvBookList;
    private List<Book> mBookList;
    private BookListAdapter mAdapter;

    /*初始化数据*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookList = BookLab.getBookLab(getActivity()).getBooks();
        setHasOptionsMenu(true);
    }

    /*初始化UI元素*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life_book_list,container,false);
        rvBookList = view.findViewById(R.id.rv_life_book);
        rvBookList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
        return view;
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
