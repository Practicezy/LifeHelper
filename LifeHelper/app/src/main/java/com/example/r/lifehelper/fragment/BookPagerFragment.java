package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.BookChapter;
import com.example.r.lifehelper.data.BookContentAsyncTask;

import java.util.concurrent.ExecutionException;

public class BookPagerFragment extends Fragment {
    private BookChapter mBookChapter;
    private TextView tvContent;
    private static final String CONTENT_ARGS = "com.example.r.lifehelper.fragment.content_args";

    public static Fragment newInstance(String urlSpec) {
        BookPagerFragment fragment = new BookPagerFragment();
        Bundle args = new Bundle();
        args.putString(CONTENT_ARGS, urlSpec);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*根据传来的具体章节链接获得书的内容数据*/
        String url = getArguments().getString(CONTENT_ARGS);
        try {
            mBookChapter = new BookContentAsyncTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager_book_content, container, false);
        /*显示书的内容详情*/
        tvContent = view.findViewById(R.id.tv_book_content);
        tvContent.setText(mBookChapter.getContent());
        return view;
    }
}
