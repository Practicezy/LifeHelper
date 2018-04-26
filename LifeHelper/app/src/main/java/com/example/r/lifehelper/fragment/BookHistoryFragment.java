package com.example.r.lifehelper.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.BookChapter;
import com.example.r.lifehelper.data.BookChapterAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookHistoryFragment extends Fragment {
    private SharedPreferences sPref;
    private TextView tvBookHistory;
    private Button btnBookHistory;
    private List<BookChapter> mBookChapterList;
    private BookChapter mBookChapter;
    private String url;
    private int position;
    private static final String HISTORY_ARGS = "com.example.r.lifehelper.fragment.book.history";

    public static Fragment newInstance(String title){
        BookHistoryFragment fragment = new BookHistoryFragment();
        Bundle args = new Bundle();
        args.putString(HISTORY_ARGS, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getArguments().getString(HISTORY_ARGS).replace("《","").replace("》","");
        sPref = getActivity().getSharedPreferences(title, Context.MODE_PRIVATE);
        url = sPref.getString("urlSpec","");
        position = sPref.getInt("position",0);
        try {
            mBookChapterList = new BookChapterAsyncTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /*因为viewpager会加载下一页position,所以获得的position须减一*/
        mBookChapter = mBookChapterList.get(position - 1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_history,container,false);
        tvBookHistory = view.findViewById(R.id.tv_book_history);
        tvBookHistory.setText(mBookChapter.getBookTitle());
        btnBookHistory = view.findViewById(R.id.btn_book_history_info);
        btnBookHistory.setText(mBookChapter.getChapterTitle());
        btnBookHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = BookContentFragment.newInstance(url, position -1);
                fm.beginTransaction()
                        .replace(R.id.book_fragment_container,fragment)
                        .addToBackStack(null)
                        .show(fragment)
                        .commit();
            }
        });
        return view;
    }
}
